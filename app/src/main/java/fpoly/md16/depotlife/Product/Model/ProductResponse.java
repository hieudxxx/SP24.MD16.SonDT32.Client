package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

//    @SerializedName("current_page")
//    private int current_page;
//    @SerializedName("first_page_url")
//    private String first_page_url;
//
//    @SerializedName("from")
//    private int from;
//
//    @SerializedName("last_page")
//    private int last_page;
//
//    @SerializedName("last_page_url")
//    private String last_page_url;
//
//    @SerializedName("links")
//    private Link[] link;
public class ProductResponse {

    @SerializedName("data")
    private Product[] data;
    @SerializedName("next_page_url")
    private String next_page_url;

    public ProductResponse(Product[] data, String next_page_url, String path, int per_page) {
        this.data = data;
        this.next_page_url = next_page_url;
        this.path = path;
        this.per_page = per_page;
    }

    @SerializedName("path")
    private String path;
    @SerializedName("per_page")
    private int per_page;

    public Product[] getData() {
        return data;
    }

    public void setData(Product[] data) {
        this.data = data;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

//    public static class Data {
//        @SerializedName("id")
//        private int id;
//        @SerializedName("supplier_id")
//        private int supplier_id;
//        @SerializedName("categories_id")
//        private int category_id;
//        @SerializedName("product_name")
//        private String product_name;
//        @SerializedName("pin_image")
//        private String img;
//        @SerializedName("barcode")
//        private String barcode;
//        @SerializedName("unit")
//        private String unit;
//        @SerializedName("import_price")
//        private double import_price;
//        @SerializedName("sell_price")
//        private double export_price;
//        @SerializedName("total_quantity")
//        private int inventory;
//        @SerializedName("status")
//        private int status;
//        @SerializedName("categories")
//        private String category_name;
//        @SerializedName("supplier")
//        private String supplier_name;
//
//        public Data(int id, int supplier_id, int category_id, String product_name, String img, String barcode, String unit, double import_price, double export_price, int inventory, int status, String category_name, String supplier_name) {
//            this.id = id;
//            this.supplier_id = supplier_id;
//            this.category_id = category_id;
//            this.product_name = product_name;
//            this.img = img;
//            this.barcode = barcode;
//            this.unit = unit;
//            this.import_price = import_price;
//            this.export_price = export_price;
//            this.inventory = inventory;
//            this.status = status;
//            this.category_name = category_name;
//            this.supplier_name = supplier_name;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getSupplier_id() {
//            return supplier_id;
//        }
//
//        public void setSupplier_id(int supplier_id) {
//            this.supplier_id = supplier_id;
//        }
//
//        public int getCategory_id() {
//            return category_id;
//        }
//
//        public void setCategory_id(int category_id) {
//            this.category_id = category_id;
//        }
//
//        public String getProduct_name() {
//            return product_name;
//        }
//
//        public void setProduct_name(String product_name) {
//            this.product_name = product_name;
//        }
//
//        public String getImg() {
//            return img;
//        }
//
//        public void setImg(String img) {
//            this.img = img;
//        }
//
//        public String getBarcode() {
//            return barcode;
//        }
//
//        public void setBarcode(String barcode) {
//            this.barcode = barcode;
//        }
//
//        public String getUnit() {
//            return unit;
//        }
//
//        public void setUnit(String unit) {
//            this.unit = unit;
//        }
//
//        public double getImport_price() {
//            return import_price;
//        }
//
//        public void setImport_price(double import_price) {
//            this.import_price = import_price;
//        }
//
//        public double getExport_price() {
//            return export_price;
//        }
//
//        public void setExport_price(double export_price) {
//            this.export_price = export_price;
//        }
//
//        public int getInventory() {
//            return inventory;
//        }
//
//        public void setInventory(int inventory) {
//            this.inventory = inventory;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public String getCategory_name() {
//            return category_name;
//        }
//
//        public void setCategory_name(String category_name) {
//            this.category_name = category_name;
//        }
//
//        public String getSupplier_name() {
//            return supplier_name;
//        }
//
//        public void setSupplier_name(String supplier_name) {
//            this.supplier_name = supplier_name;
//        }
//
//        @Override
//        public String toString() {
//            return "Data{" +
//                    "id=" + id +
//                    ", supplier_id=" + supplier_id +
//                    ", category_id=" + category_id +
//                    ", product_name='" + product_name + '\'' +
//                    ", img='" + img + '\'' +
//                    ", barcode='" + barcode + '\'' +
//                    ", unit='" + unit + '\'' +
//                    ", import_price=" + import_price +
//                    ", export_price=" + export_price +
//                    ", inventory=" + inventory +
//                    ", status=" + status +
//                    ", category_name='" + category_name + '\'' +
//                    ", supplier_name='" + supplier_name + '\'' +
//                    '}';
//        }
//    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "data=" + Arrays.toString(data) +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", per_page=" + per_page +
                '}';
    }
}
