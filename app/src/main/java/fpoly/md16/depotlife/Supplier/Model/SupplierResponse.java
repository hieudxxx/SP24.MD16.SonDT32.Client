package fpoly.md16.depotlife.Supplier.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class SupplierResponse {
    @SerializedName("data")
    private Supplier[] data;
    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("per_page")
    private int per_page;

    public SupplierResponse(Supplier[] data, String next_page_url, String path, int per_page) {
        this.data = data;
        this.next_page_url = next_page_url;
        this.path = path;
        this.per_page = per_page;
    }

    public Supplier[] getData() {
        return data;
    }

    public void setData(Supplier[] data) {
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

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    @Override
    public String toString() {
        return "SupplierResponse{" +
                "data=" + Arrays.toString(data) +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", per_page=" + per_page +
                '}';
    }
}
