package br.com.johnjohncc.patrimonio.entities;

import java.util.List;

/**
 * Created by WGL003 on 30/06/2017.
 */

public class ItemResponse {

    private List<Item> data;

    public List<Item> getData() {
        return data;
    }

    public void setData(List<Item> data) {
        this.data = data;
    }
}
