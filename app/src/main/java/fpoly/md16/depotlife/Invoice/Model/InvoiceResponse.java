package fpoly.md16.depotlife.Invoice.Model;

import com.google.gson.annotations.SerializedName;

import fpoly.md16.depotlife.Product.Model.Product;

public class InvoiceResponse {

    @SerializedName("data")
    private Invoice[] data;
    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("last_page")
    private int last_page;
    @SerializedName("total")
    private int total;


    public InvoiceResponse(Invoice[] data, String next_page_url, String path, int last_page, int total) {
        this.data = data;
        this.next_page_url = next_page_url;
        this.path = path;
        this.last_page = last_page;
        this.total = total;
    }

    public Invoice[] getData() {
        return data;
    }

    public void setData(Invoice[] data) {
        this.data = data;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
