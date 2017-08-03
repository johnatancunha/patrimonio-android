package br.com.johnjohncc.patrimonio.network;

import java.util.List;

import br.com.johnjohncc.patrimonio.entities.Item;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    Call<PaginatedResponse<Item>> items(@Query("page") Integer page);

    @POST("items")
//    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<List<DefaultResponse<Item>>> itemRegister(@Field("title") String title,
                                                  @Field("description") String description,
                                                  @Field("invoice") String invoice,
                                                  @Field("acquisition_date") String acquisitionDate,
                                                  @Field("quantity") Integer quantity,
                                                  @Field("number") Integer number,
                                                  @Field("serial_number") String serialNumber,
                                                  @Field("price") String price,
                                                  @Field("notice") String notice,
                                                  @Field("father_item_id") Integer fatherItemId);

}
