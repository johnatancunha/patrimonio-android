package br.com.johnjohncc.patrimonio.entities;

import com.squareup.moshi.Json;

/**
 * Created by WGL003 on 29/06/2017.
 */

public class AccessToken {

    @Json(name = "token_type")
    String tokenType;

    @Json(name = "expires_in")
    int expiresIn;

    @Json(name = "access_token")
    String accessToken;

    @Json(name = "refresh_token")
    String refreshToken;

}
