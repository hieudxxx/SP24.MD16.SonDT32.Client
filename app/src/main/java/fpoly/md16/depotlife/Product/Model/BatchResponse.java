package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BatchResponse implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("product_id")
    private int product_id;
    @SerializedName("expiry_date")
    private String expiry_date;
    @SerializedName("quantity_exp")
    private String quantity_exp;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;

    public BatchResponse(int id, int product_id, String expiry_date, String quantity_exp, String created_at, String updated_at) {
        this.id = id;
        this.product_id = product_id;
        this.expiry_date = expiry_date;
        this.quantity_exp = quantity_exp;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getQuantity_exp() {
        return quantity_exp;
    }

    public void setQuantity_exp(String quantity_exp) {
        this.quantity_exp = quantity_exp;
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

    @Override
    public String toString() {
        return "BatchResponse{" +
                "id=" + id +
                ", product_id=" + product_id +
                ", expiry_date='" + expiry_date + '\'' +
                ", quantity_exp='" + quantity_exp + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
