package fpoly.md16.depotlife.Product.Model;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Serializable {
    private String id;
    private String supplier_id;
    private String category_id;
    private String name;
    private String barcode;
    private String unit;
    private String img;
    private double import_price;
    private double export_price;
    private int inventory;
    private boolean status;

    public static Comparator<Product> sortByAsc = (t1, t2) -> (int) (t1.getExport_price() - t2.getExport_price());

    public static Comparator<Product> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());

    public Product() {
    }

    public Product(String id, String supplier_id, String category_id, String name, String barcode, String unit, String img, double import_price, double export_price, int inventory, boolean status) {
        this.id = id;
        this.supplier_id = supplier_id;
        this.category_id = category_id;
        this.name = name;
        this.barcode = barcode;
        this.unit = unit;
        this.img = img;
        this.import_price = import_price;
        this.export_price = export_price;
        this.inventory = inventory;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getImport_price() {
        return import_price;
    }

    public void setImport_price(double import_price) {
        this.import_price = import_price;
    }

    public double getExport_price() {
        return export_price;
    }

    public void setExport_price(double export_price) {
        this.export_price = export_price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
