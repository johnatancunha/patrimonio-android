package br.com.johnjohncc.patrimonio.network;

import br.com.johnjohncc.patrimonio.entities.AccessToken;
import br.com.johnjohncc.patrimonio.entities.ItemResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by WGL003 on 29/06/2017.
 */

public interface ApiService {


    @POST("register")
    @FormUrlEncoded
    Call<AccessToken> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @POST("login")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("email") String email, @Field("password") String password);

    @POST("refresh")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @GET("items")
    Call<ItemResponse> items();

}
