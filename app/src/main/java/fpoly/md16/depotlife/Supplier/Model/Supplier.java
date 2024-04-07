package fpoly.md16.depotlife.Supplier.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Supplier implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String tax_code;
    private String address;
    private double total;
    private boolean status;

    public static ArrayList<Supplier> filterByStatus(ArrayList<Supplier> list, boolean status) {
        ArrayList<Supplier> filteredList = new ArrayList<>();

        for (Supplier supplier : list) {
            if (supplier.isStatus() == status) {
                filteredList.add(supplier);
            }
        }
        return filteredList;
    }

    public static Comparator<Supplier> sortByAsc = (t1, t2) -> (int) (t1.getTotal() - t2.getTotal());

    public static Comparator<Supplier> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getId());


    public Supplier() {
    }

    public Supplier(String id, String name, String phone, String email, String tax_code, String address, double total, boolean status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tax_code = tax_code;
        this.address = address;
        this.total = total;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
