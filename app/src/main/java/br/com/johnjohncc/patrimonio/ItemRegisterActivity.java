package br.com.johnjohncc.patrimonio;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.util.List;
import java.util.Map;

import br.com.johnjohncc.patrimonio.entities.Item;
import br.com.johnjohncc.patrimonio.network.AccessToken;
import br.com.johnjohncc.patrimonio.network.ApiError;
import br.com.johnjohncc.patrimonio.network.ApiService;
import br.com.johnjohncc.patrimonio.network.DefaultResponse;
import br.com.johnjohncc.patrimonio.network.RetrofitBuilder;
import br.com.johnjohncc.patrimonio.network.TokenManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRegisterActivity extends AppCompatActivity {

    private static final String TAG = "ItemRegisterActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.editTitle)
    TextInputLayout tilTitle;

    @BindView(R.id.editDescription)
    TextInputLayout tilDescription;

    @BindView(R.id.editInvoice)
    TextInputLayout tilInvoice;

    @BindView(R.id.editSerial)
    TextInputLayout tilSerial;

    @BindView(R.id.editPrice)
    TextInputLayout tilPrice;

    @BindView(R.id.editQuantity)
    TextInputLayout tilQuantity;

    @BindView(R.id.editAcquisitionDate)
    TextInputLayout tilAcquisitionDate;

    @BindView(R.id.editNotice)
    TextInputLayout tilNotice;

    @BindView(R.id.editFather)
    TextInputLayout tilFather;

    ApiService service;
    Call<List<DefaultResponse<Item>>> call;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_register);

        ButterKnife.bind(this);
        tokenManager = TokenManager.getInstance(this.getSharedPreferences("prefs", Context.MODE_PRIVATE));
        service = RetrofitBuilder.createServiceWithAuth(ApiService.class, tokenManager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.register_options, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(getIntent() != null) {
            menu.removeItem(R.id.item_edit);
            menu.removeItem(R.id.item_delete);
        } else {
            menu.removeItem(R.id.item_save);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_save:
                register();
                return true;
            case R.id.item_edit:
                return true;
            case R.id.item_delete:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //TODO corrigir ordenação dos itens para aparecer por ordem de cadastro na lista ou possibilidade melhor
    private void register() {

        tilTitle.setError(null);
        tilDescription.setError(null);
        tilInvoice.setError(null);
        tilSerial.setError(null);
        tilPrice.setError(null);
        tilQuantity.setError(null);
        tilAcquisitionDate.setError(null);
        tilNotice.setError(null);
        tilFather.setError(null);

        Item item = new Item();
        item.setTitle(tilTitle.getEditText().getText().toString());
        item.setDescription(tilDescription.getEditText().getText().toString());
        item.setInvoice(tilInvoice.getEditText().getText().toString());
        item.setSerialNumber(tilSerial.getEditText().getText().toString());
        item.setPrice(tilPrice.getEditText().getText().toString().trim());
        item.setAcquisitionDate(tilAcquisitionDate.getEditText().getText().toString());
        item.setNotice(tilNotice.getEditText().getText().toString());
        if (!tilQuantity.getEditText().getText().toString().isEmpty()) {
            item.setQuantity(Integer.parseInt(tilQuantity.getEditText().getText().toString().trim()));
        }
        if (!tilFather.getEditText().getText().toString().isEmpty()) {
            item.setFatherItemId(Integer.parseInt(tilFather.getEditText().getText().toString().trim()));
        }

        call = service.itemRegister(item.getTitle(),item.getDescription(),item.getInvoice(),item.getAcquisitionDate(),item.getQuantity(),item.getNumber(), item.getSerialNumber(),item.getPrice(),item.getNotice(),item.getFatherItemId());
        call.enqueue(new Callback<List<DefaultResponse<Item>>>() {
            @Override
            public void onResponse(Call<List<DefaultResponse<Item>>> call, Response<List<DefaultResponse<Item>>> response) {
                Log.w(TAG, "onResponse: " + response);
                if (response.isSuccessful()) {
                    Log.w(TAG, "onResponse: " + response.body());
                    Toast.makeText(ItemRegisterActivity.this, "Item criado", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 422){
                    handleErrors(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<DefaultResponse<Item>>> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void handleErrors(ResponseBody response) {
        ApiError apiError = Utils.convertErrors(response);

        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("title")) {
                tilTitle.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("invoice")) {
                tilInvoice.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("price")) {
                tilPrice.setError(error.getValue().get(0));
            }
            if (error.getKey().equals("quantity")) {
                tilQuantity.setError(error.getValue().get(0));
            }
        }
    }
}
