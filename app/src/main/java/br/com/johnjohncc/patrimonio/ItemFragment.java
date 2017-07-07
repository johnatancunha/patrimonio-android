package br.com.johnjohncc.patrimonio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.johnjohncc.patrimonio.adapters.ItemAdapter;
import br.com.johnjohncc.patrimonio.network.DefaultResponse;
import br.com.johnjohncc.patrimonio.entities.Item;
import br.com.johnjohncc.patrimonio.network.ApiService;
import br.com.johnjohncc.patrimonio.network.RetrofitBuilder;
import br.com.johnjohncc.patrimonio.network.TokenManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemFragment extends Fragment {

    @BindView(R.id.rl_fragment_item)
    RecyclerView recyclerView;

    private ItemAdapter itemAdapter;

    private ApiService service;
    private TokenManager tokenManager;
    private Call<DefaultResponse<Item>> call;

    private static final String TAG = "ItemFragment";


    public ItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_item, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);
//        recyclerView.setHasFixedSize(true);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                ItemAdapter itemAdapter = (ItemAdapter) recyclerView.getAdapter();
//
//                if(items.size() == layoutManager.findLastCompletelyVisibleItemPosition() + 1) {
//                    List<Item> listAux = getItems();
//                    for (int i=0 ; i < listAux.size(); i++){
//                        itemAdapter.addListItem(listAux.get(i), items.size());
//                    }
//                }
//
//                Log.w(TAG, "onScrolled: ");
//
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setReverseLayout(true); //se quiser que o layout comece de baixo para cima
        itemAdapter = new ItemAdapter(new ArrayList<Item>());
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        getItems();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (call != null) {
            call.cancel();
            call = null;
        }
    }

    private void getItems(){
        tokenManager = TokenManager.getInstance(getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        if (tokenManager.getToken() == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }

        call = service.items();
        call.enqueue(new Callback<DefaultResponse<Item>>() {
            @Override
            public void onResponse(Call<DefaultResponse<Item>> call, Response<DefaultResponse<Item>> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    itemAdapter.addList(response.body().getData());

                } else {
                    tokenManager.deleteToken();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse<Item>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
