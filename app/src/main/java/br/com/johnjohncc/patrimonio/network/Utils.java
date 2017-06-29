package br.com.johnjohncc.patrimonio.network;

import java.io.IOException;
import java.lang.annotation.Annotation;

import br.com.johnjohncc.patrimonio.entities.ApiError;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by WGL003 on 29/06/2017.
 */

public class Utils {

    public static ApiError convertErrors(ResponseBody response) {
        Converter<ResponseBody, ApiError> converter = RetrofitBuilder.getRetrofit().responseBodyConverter(ApiError.class, new Annotation[0]);
        ApiError apiError = null;
        try {
            apiError = converter.convert(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiError;
    }
}
