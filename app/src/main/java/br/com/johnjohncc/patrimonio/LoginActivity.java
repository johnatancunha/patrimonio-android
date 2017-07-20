package br.com.johnjohncc.patrimonio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

import java.util.List;
import java.util.Map;

import br.com.johnjohncc.patrimonio.network.AccessToken;
import br.com.johnjohncc.patrimonio.network.ApiError;
import br.com.johnjohncc.patrimonio.network.ApiService;
import br.com.johnjohncc.patrimonio.network.RetrofitBuilder;
import br.com.johnjohncc.patrimonio.network.TokenManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.editEmail)
    TextInputLayout tilEmail;

    @BindView(R.id.editPassword)
    TextInputLayout tilPassword;

    ApiService service;
    TokenManager tokenManager;
    AwesomeValidation validator;
    Call<AccessToken> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        service = RetrofitBuilder.createService(ApiService.class);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
//        validator = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);

        if(tokenManager.getToken().getAccessToken() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    @OnClick(R.id.btn_login)
    public void login() {
        String email = tilEmail.getEditText().getText().toString();
        String password = tilPassword.getEditText().getText().toString();

        tilEmail.setError(null);
        tilPassword.setError(null);

        call = service.login(email,password);

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Log.w(TAG, "onResponse: " + response );

                if (response.isSuccessful()) {
                    tokenManager.saveToken(response.body());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    if (response.code() == 422) {
                        handleErrors(response.errorBody());
                    }
                    if (response.code() == 401) {
                        ApiError apiError = Utils.convertErrors(response.errorBody());
                        Toast.makeText(LoginActivity.this, apiError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.w(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.go_to_register)
    public void goToRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private void handleErrors(ResponseBody response) {
        ApiError apiError = Utils.convertErrors(response);

        for (Map.Entry<String,List<String>> error : apiError.getErrors().entrySet()) {
            if (error.getKey().equals("email")) {
                tilEmail.setError(error.getValue().get(0));
            }if (error.getKey().equals("password")) {
                tilPassword.setError(error.getValue().get(0));
            }
        }
    }

    public void setupRules() {
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
