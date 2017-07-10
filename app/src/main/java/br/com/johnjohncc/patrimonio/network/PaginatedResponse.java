package br.com.johnjohncc.patrimonio.network;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by WGL003 on 10/07/2017.
 */

public class PaginatedResponse<T> {

    @Json(name = "current_page")
    private int currentPage;
    private List<T> data;
    private int from;
    @Json(name = "last_page")
    private int lastPage;
    @Json(name = "next_page_url")
    private String nextPageUrl;
    private String path;
    @Json(name = "per_page")
    private int perPage;
    @Json(name = "prev_page_url")
    private String prevPageUrl;
    private int to;
    private int total;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public String getPrevPageUrl() {
        return prevPageUrl;
    }

    public void setPrevPageUrl(String prevPageUrl) {
        this.prevPageUrl = prevPageUrl;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
