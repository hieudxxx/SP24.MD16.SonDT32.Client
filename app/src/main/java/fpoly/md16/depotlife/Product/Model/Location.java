package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("zone")
    private int zone;
    @SerializedName("shelf")
    private String shelf;
    @SerializedName("level")
    private int level;
    @SerializedName("code")
    private String code;
    @SerializedName("product_id")
    private int product_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", zone=" + zone +
                ", shelf='" + shelf + '\'' +
                ", level=" + level +
                ", code='" + code + '\'' +
                ", product_id=" + product_id +
                '}';
    }
}
