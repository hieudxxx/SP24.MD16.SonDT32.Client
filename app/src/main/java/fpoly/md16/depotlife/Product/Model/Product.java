package fpoly.md16.depotlife.Product.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import fpoly.md16.depotlife.Category.Model.Category;
import fpoly.md16.depotlife.Supplier.Model.Supplier;

public class Product implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("supplier_id")
    private int supplier_id;
    @SerializedName("categories_id")
    private int category_id;
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("barcode")
    private String barcode;
    @SerializedName("unit")
    private String unit;
    @SerializedName("import_price")
    private int import_price;
    @SerializedName("sell_price")
    private int export_price;
    @SerializedName("total_quantity")
    private int inventory;
    @SerializedName("status")
    private int status;
    @SerializedName("supplier")
    private Supplier supplier;
    @SerializedName("category")
    private Category category;
    @SerializedName("product_image")
    private Image[] img;
    @SerializedName("location")
    private Location location;

    public static Comparator<Product> sortByAsc = (t1, t2) -> (int) (t1.getExport_price() - t2.getExport_price());

    public static Comparator<Product> sortByNameAZ = (t1, t2) -> t1.getProduct_name().compareTo(t2.getProduct_name());

    public Product() {
    }

    public Product(int id, int supplier_id, int category_id, String product_name, String barcode, String unit, int import_price, int export_price, int inventory, int status, Supplier supplier, Category category, Image[] img, Location location) {
        this.id = id;
        this.supplier_id = supplier_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.barcode = barcode;
        this.unit = unit;
        this.import_price = import_price;
        this.export_price = export_price;
        this.inventory = inventory;
        this.status = status;
        this.supplier = supplier;
        this.category = category;
        this.img = img;
        this.location = location;
    }


    public Product(int supplier_id, int category_id, String product_name, String unit, int import_price, int export_price) {
        this.supplier_id = supplier_id;
        this.category_id = category_id;
        this.product_name = product_name;
        this.unit = unit;
        this.import_price = import_price;
        this.export_price = export_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getImport_price() {
        return import_price;
    }

    public void setImport_price(int import_price) {
        this.import_price = import_price;
    }

    public int getExport_price() {
        return export_price;
    }

    public void setExport_price(int export_price) {
        this.export_price = export_price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Image[] getImg() {
        return img;
    }

    public void setImg(Image[] img) {
        this.img = img;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", supplier_id=" + supplier_id +
                ", category_id=" + category_id +
                ", product_name='" + product_name + '\'' +
                ", barcode='" + barcode + '\'' +
                ", unit='" + unit + '\'' +
                ", import_price=" + import_price +
                ", export_price=" + export_price +
                ", inventory=" + inventory +
                ", status=" + status +
                ", supplier=" + supplier +
                ", category=" + category +
                ", img=" + Arrays.toString(img) +
                ", location=" + location +
                '}';
    }
}
