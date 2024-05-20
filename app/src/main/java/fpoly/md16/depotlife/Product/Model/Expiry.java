package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

public class Expiry {
    @SerializedName("id")
    private int id;

    @SerializedName("product_id")
    private int product_id;

    @SerializedName("quantity_exp")
    private int quantity_exp;

    @SerializedName("expiry_date")
    private String expiry_date;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    public Expiry() {
    }

    public Expiry(int product_id, int quantity_exp, String expiry_date) {
        this.product_id = product_id;
        this.quantity_exp = quantity_exp;
        this.expiry_date = expiry_date;
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

    public int getQuantity_exp() {
        return quantity_exp;
    }

    public void setQuantity_exp(int quantity_exp) {
        this.quantity_exp = quantity_exp;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
