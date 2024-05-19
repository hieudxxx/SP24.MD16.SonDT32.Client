package fpoly.md16.depotlife.Customers.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class CustomerResponse {
    @SerializedName("data")
    private Customer[] data;
    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("last_page")
    private int last_page;
    @SerializedName("total")
    private int total;

    public CustomerResponse(Customer[] data, String next_page_url, String path, int last_page, int total) {
        this.data = data;
        this.next_page_url = next_page_url;
        this.path = path;
        this.last_page = last_page;
        this.total = total;
    }

    public Customer[] getData() {
        return data;
    }

    public void setData(Customer[] data) {
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

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "data=" + Arrays.toString(data) +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", last_page=" + last_page +
                ", total=" + total +
                '}';
    }
}
