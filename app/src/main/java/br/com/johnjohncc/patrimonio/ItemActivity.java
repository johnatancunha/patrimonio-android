package br.com.johnjohncc.patrimonio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import br.com.johnjohncc.patrimonio.entities.ItemResponse;
import br.com.johnjohncc.patrimonio.network.ApiService;
import br.com.johnjohncc.patrimonio.network.RetrofitBuilder;
import br.com.johnjohncc.patrimonio.network.TokenManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemActivity extends AppCompatActivity {

    private static final String TAG = "ItemActivity";

    ApiService service;
    TokenManager tokenManager;
    Call<ItemResponse> call;

    @BindView(R.id.items_list)
    RecyclerView itemListView;

    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        ButterKnife.bind(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        if (tokenManager.getToken() == null) {
            startActivity(new Intent(ItemActivity.this, LoginActivity.class));
            finish();
        }

        setupList();

    }

//    @OnClick(R.id.btn_items)
//    public void items() {
//        call = service.items();
//        call.enqueue(new Callback<ItemResponse>() {
//            @Override
//            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
////                Log.w(TAG, "onResponse: " + response);
//
//                if (response.isSuccessful()) {
//
//                } else {
//                    tokenManager.deleteToken();
//                    startActivity(new Intent(ItemActivity.this, LoginActivity.class));
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ItemResponse> call, Throwable t) {
//                Log.w(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
    }

    private void setupList() {

        call = service.items();
        call.enqueue(new Callback<ItemResponse>() {
            @Override
            public void onResponse(Call<ItemResponse> call, Response<ItemResponse> response) {
                Log.w(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ItemActivity.this);
                    itemListView.setLayoutManager(layoutManager);
                    itemAdapter = new ItemAdapter(ItemActivity.this, response.body().getData());
                    itemListView.setAdapter(itemAdapter);
                    itemListView.addItemDecoration(
                            new DividerItemDecoration(ItemActivity.this, DividerItemDecoration.VERTICAL));

                } else {
                    tokenManager.deleteToken();
                    startActivity(new Intent(ItemActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ItemResponse> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

}