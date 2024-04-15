package fpoly.md16.depotlife.Product.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class ProductResponse {
    @SerializedName("current_page")
    private int current_page;
    @SerializedName("data")
    private Data[] data;
    @SerializedName("first_page_url")
    private String first_page_url;

    @SerializedName("from")
    private int from;

    @SerializedName("last_page")
    private int last_page;

    @SerializedName("last_page_url")
    private String last_page_url;

    @SerializedName("links")
    private Link[] link;

    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("per_page")
    private int per_page;
    @SerializedName("prev_page_url")
    private String prev_page_url;
    @SerializedName("to")
    private int to;
    @SerializedName("total")
    private int total;

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public Link[] getLink() {
        return link;
    }

    public void setLink(Link[] link) {
        this.link = link;
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

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class Data {
        @SerializedName("id")
        private int id;
        @SerializedName("supplier_id")
        private int supplier_id;
        @SerializedName("categories_id")
        private int category_id;
        @SerializedName("product_name")
        private String product_name;
        @SerializedName("pin_image")
        private String pin_image;
        @SerializedName("barcode")
        private String barcode;
        @SerializedName("unit")
        private String unit;
        @SerializedName("import_price")
        private double import_price;
        @SerializedName("sell_price")
        private double sell_price;
        @SerializedName("total_quantity")
        private int total_quantity;
        @SerializedName("status")
        private int status;
        @SerializedName("categories")
        private String categories;
        @SerializedName("supplier")
        private String supplier;

        public Data(int id, int supplier_id, int category_id, String product_name, String pin_image, String barcode, String unit, double import_price, double sell_price, int total_quantity, int status, String categories, String supplier) {
            this.id = id;
            this.supplier_id = supplier_id;
            this.category_id = category_id;
            this.product_name = product_name;
            this.pin_image = pin_image;
            this.barcode = barcode;
            this.unit = unit;
            this.import_price = import_price;
            this.sell_price = sell_price;
            this.total_quantity = total_quantity;
            this.status = status;
            this.categories = categories;
            this.supplier = supplier;
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

        public String getPin_image() {
            return pin_image;
        }

        public void setPin_image(String pin_image) {
            this.pin_image = pin_image;
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

        public double getImport_price() {
            return import_price;
        }

        public void setImport_price(double import_price) {
            this.import_price = import_price;
        }

        public double getSell_price() {
            return sell_price;
        }

        public void setSell_price(double sell_price) {
            this.sell_price = sell_price;
        }

        public int getTotal_quantity() {
            return total_quantity;
        }

        public void setTotal_quantity(int total_quantity) {
            this.total_quantity = total_quantity;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCategories() {
            return categories;
        }

        public void setCategories(String categories) {
            this.categories = categories;
        }

        public String getSupplier() {
            return supplier;
        }

        public void setSupplier(String supplier) {
            this.supplier = supplier;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", supplier_id=" + supplier_id +
                    ", category_id=" + category_id +
                    ", product_name='" + product_name + '\'' +
                    ", pin_image='" + pin_image + '\'' +
                    ", barcode='" + barcode + '\'' +
                    ", unit='" + unit + '\'' +
                    ", import_price=" + import_price +
                    ", sell_price=" + sell_price +
                    ", total_quantity=" + total_quantity +
                    ", status=" + status +
                    ", categories='" + categories + '\'' +
                    ", supplier='" + supplier + '\'' +
                    '}';
        }
    }

    public static class Link {
        @SerializedName("url")
        private String url;
        @SerializedName("label")
        private String label;
        @SerializedName("active")
        private boolean active;

        public Link(String url, String label, boolean active) {
            this.url = url;
            this.label = label;
            this.active = active;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "current_page=" + current_page +
                ", data=" + Arrays.toString(data) +
                ", first_page_url='" + first_page_url + '\'' +
                ", from=" + from +
                ", last_page=" + last_page +
                ", last_page_url='" + last_page_url + '\'' +
                ", link=" + Arrays.toString(link) +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", per_page=" + per_page +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", to=" + to +
                ", total=" + total +
                '}';
    }
}
