package fpoly.md16.depotlife.Invoice.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

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

    public static class ProductInvoice {
        @SerializedName("productId")
        private int productId;
        @SerializedName("quantity")
        private int quantity;
        @SerializedName("expiry")
        private String expiry;

        public ProductInvoice() {
        }

        public ProductInvoice(int productId, int quantity, String expiry) {
            this.productId = productId;
            this.quantity = quantity;
            this.expiry = expiry;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getExpiry() {
            return expiry;
        }

        public void setExpiry(String expiry) {
            this.expiry = expiry;
        }
    }

}
