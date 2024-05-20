package fpoly.md16.depotlife.Invoice.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import fpoly.md16.depotlife.Login.Model.UserResponse;
import fpoly.md16.depotlife.Product.Model.Product;

public class Invoice implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("total_amount")
    private long totalAmount;
    @SerializedName("invoice_type")
    private int invoiceType;
    @SerializedName("customer_id")
    private int customer_id;
    @SerializedName("supplier_id")
    private int supplier_id;
    @SerializedName("pay_status")
    private int statusPayment;
    @SerializedName("discount")
    private int discount;
    @SerializedName("due_date")
    private String dueDate;
    @SerializedName("note")
    private String note;
    @SerializedName("term")
    private String term;
    @SerializedName("signature_name")
    private String signature_name;
    @SerializedName("signature")
    private String signature_img;
    @SerializedName("created_at")
    private String date_created;
    @SerializedName("products")
    private ProductInvoice[] productInvoice;

    @SerializedName("user")
    private UserResponse.User user;

    public Invoice() {
    }

    public Invoice(int user_id, int invoiceType, int customer_id, int supplier_id, int statusPayment, int discount, String dueDate, String note, String term, String signature_name, String signature_img, String date_created, ProductInvoice[] productInvoice) {
        this.user_id = user_id;
        this.invoiceType = invoiceType;
        this.customer_id = customer_id;
        this.supplier_id = supplier_id;
        this.statusPayment = statusPayment;
        this.discount = discount;
        this.dueDate = dueDate;
        this.note = note;
        this.term = term;
        this.signature_name = signature_name;
        this.signature_img = signature_img;
        this.date_created = date_created;
        this.productInvoice = productInvoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(int statusPayment) {
        this.statusPayment = statusPayment;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getSignature_name() {
        return signature_name;
    }

    public void setSignature_name(String signature_name) {
        this.signature_name = signature_name;
    }

    public String getSignature_img() {
        return signature_img;
    }

    public void setSignature_img(String signature_img) {
        this.signature_img = signature_img;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public ProductInvoice[] getProductInvoice() {
        return productInvoice;
    }

    public void setProductInvoice(ProductInvoice[] productInvoice) {
        this.productInvoice = productInvoice;
    }

    public UserResponse.User getUser() {
        return user;
    }

    public void setUser(UserResponse.User user) {
        this.user = user;
    }

    public static class ProductInvoice {
        @SerializedName("id")
        private int id;
        @SerializedName("quantity")
        private int quantity;
        @SerializedName("invoice_id")
        private int invoice_id;;

        @SerializedName("product_id")
        private int product_id;

        @SerializedName("product")
        private Product product;

        public ProductInvoice() {
        }

        public ProductInvoice(int id, int quantity, int invoice_id, int product_id, Product product) {
            this.id = id;
            this.quantity = quantity;
            this.invoice_id = invoice_id;
            this.product_id = product_id;
            this.product = product;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getInvoice_id() {
            return invoice_id;
        }

        public void setInvoice_id(int invoice_id) {
            this.invoice_id = invoice_id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }
    }

}
