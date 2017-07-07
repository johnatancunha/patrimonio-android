package br.com.johnjohncc.patrimonio.network;

import java.util.List;

/**
 * Created by WGL003 on 07/07/2017.
 */

public class DefaultResponse<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
