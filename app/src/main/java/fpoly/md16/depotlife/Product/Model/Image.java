package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("product_id")
    private int product_id;
    @SerializedName("path")
    private String path;
    @SerializedName("name")
    private String name;
    @SerializedName("is_pined")
    private int is_pined;

    public Image() {
    }

    public Image(int product_id, String path, int is_pined) {
        this.product_id = product_id;
        this.path = path;
        this.is_pined = is_pined;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIs_pined() {
        return is_pined;
    }

    public void setIs_pined(int is_pined) {
        this.is_pined = is_pined;
    }
}
