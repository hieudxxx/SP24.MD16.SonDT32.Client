package fpoly.md16.depotlife.Supplier.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Supplier implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("supplier_name")
    private String name;
    @SerializedName("supplier_phone")
    private String phone;
    private String email;
    @SerializedName("tax")
    private String tax_code;
    @SerializedName("address")
    private String address;
    @SerializedName("total_import_price")
    private double total;
    @SerializedName("status")
    private int status;

    public Supplier(int id, String name, String taxCode, String address, double total, int status) {
    }

    public static ArrayList<Supplier> filterByStatus(ArrayList<Supplier> list, boolean status) {
        ArrayList<Supplier> filteredList = new ArrayList<>();

//        for (Supplier supplier : list) {
//            if (supplier.isStatus() == status) {
//                filteredList.add(supplier);
//            }
//        }
        return filteredList;
    }

    public static Comparator<Supplier> sortByAsc = (t1, t2) -> (int) (t1.getTotal() - t2.getTotal());

    public static Comparator<Supplier> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());


    public Supplier() {
    }

    public Supplier(int id, String name, String phone, String tax_code, String address, double total, int status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.tax_code = tax_code;
        this.address = address;
        this.total = total;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static Comparator<Supplier> getSortByAsc() {
        return sortByAsc;
    }

    public static void setSortByAsc(Comparator<Supplier> sortByAsc) {
        Supplier.sortByAsc = sortByAsc;
    }

    public static Comparator<Supplier> getSortByNameAZ() {
        return sortByNameAZ;
    }

    public static void setSortByNameAZ(Comparator<Supplier> sortByNameAZ) {
        Supplier.sortByNameAZ = sortByNameAZ;
    }
}
