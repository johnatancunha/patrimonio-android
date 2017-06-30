package br.com.johnjohncc.patrimonio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.List;
import java.util.Map;

import br.com.johnjohncc.patrimonio.entities.AccessToken;
import br.com.johnjohncc.patrimonio.entities.ApiError;
import br.com.johnjohncc.patrimonio.network.ApiService;
import br.com.johnjohncc.patrimonio.network.RetrofitBuilder;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    @BindView(R.id.editName)
    TextInputLayout tilName;

    @BindView(R.id.editEmail)
    TextInputLayout tilEmail;

    @BindView(R.id.editPassword)
    TextInputLayout tilPassword;

    ApiService service;
    Call<AccessToken> call;
    AwesomeValidation validator;
    TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);
//        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
//        setupRules();

        if(tokenManager.getToken().getAccessToken() != null) {
            startActivity(new Intent(RegisterActivity.this, ItemActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_register)
    public void register() {

        String name = tilName.getEditText().getText().toString();
        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        tilName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);

//        validator.clear();

//        if (validator.validate()) {
            call = service.register(name, email, password);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    Log.w(TAG, "onResponse: " + response);
                    if (response.isSuccessful()) {
                        Log.w(TAG, "onResponse: " + response.body());
                        tokenManager.saveToken(response.body());
                        startActivity(new Intent(RegisterActivity.this, ItemActivity.class));
                        finish();
                    } else {
                        handleErrors(response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Log.w(TAG, "onFailure: " + t.getMessage());
                }
            });
//        }
    }
    @OnClick(R.id.go_to_login)
    public void goToLogin() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    private void handleErrors(ResponseBody response) {
        ApiError apiError = Utils.convertErrors(response);

        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("name")) {
                tilName.setError(error.getValue().get(0));
            }if (error.getKey().equals("email")) {
                tilEmail.setError(error.getValue().get(0));
            }if (error.getKey().equals("password")) {
                tilPassword.setError(error.getValue().get(0));
            }
        }
    }

    public void setupRules() {
        validator.addValidation(this, R.id.til_name, RegexTemplate.NOT_EMPTY, R.string.err_name);
        validator.addValidation(this, R.id.til_email, Patterns.EMAIL_ADDRESS, R.string.err_email);
        validator.addValidation(this, R.id.til_password, "[a-zA-Z0-9]{6,}", R.string.err_password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
            call = null;
        }
    }
}
