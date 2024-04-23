package fpoly.md16.depotlife.Category.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

public class Category implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private int status;
    @SerializedName("created_at")
    private String created_at;

    public static Comparator<Category> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());


    public Category() {
    }

    public Category(int id, String name, int status, String created_at) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
