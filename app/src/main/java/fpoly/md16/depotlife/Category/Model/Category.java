package fpoly.md16.depotlife.Category.Model;

import java.io.Serializable;
import java.util.Comparator;

public class Category implements Serializable {
    private String id;
    private String name;
    private boolean status;

    public static Comparator<Category> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());


    public Category() {
    }

    public Category(String id, String name, boolean status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
