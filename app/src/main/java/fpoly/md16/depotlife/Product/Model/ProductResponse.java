package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class ProductResponse {
    @SerializedName("data")
    private Product[] data;
    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("last_page")
    private int last_page;
    @SerializedName("total")
    private int total;

    public ProductResponse(Product[] data, String next_page_url, String path, int last_page, int total) {
        this.data = data;
        this.next_page_url = next_page_url;
        this.path = path;
        this.last_page = last_page;
        this.total = total;
    }

    public Product[] getData() {
        return data;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public String getPath() {
        return path;
    }

    public int getLast_page() {
        return last_page;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "data=" + Arrays.toString(data) +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", last_page=" + last_page +
                ", total=" + total +
                '}';
    }
}
