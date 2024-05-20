package fpoly.md16.depotlife.Customers.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import fpoly.md16.depotlife.Customers.Model.InvoiceOwed;
import fpoly.md16.depotlife.Customers.Model.InvoicePaid;
import fpoly.md16.depotlife.Product.Model.Image;
import fpoly.md16.depotlife.Product.Model.Product;

public class Customer implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("customer_name")
    private String customerName;

    @SerializedName("customer_phone")
    private String customerPhone;

    @SerializedName("customer_email")
    private String customerEmail;

    @SerializedName("address")
    private String address;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("status")
    private int status;

    @SerializedName("invoice_quantity")
    private int invoiceQuantity;

    @SerializedName("total_invoices")
    private int totalInvoices;

    @SerializedName("invoices_paid")
    private List<InvoicePaid> invoicesPaid;

    @SerializedName("invoices_owed")
    private List<InvoiceOwed> invoicesOwed;



    public static Comparator<Customer> sortByNameAZ = (t1, t2) -> t1.getCustomerName().compareTo(t2.getCustomerName());

    // Constructor, Getters and Setters


    public Customer(int id, String createdAt, String updatedAt, String customerName, String customerPhone, String customerEmail, String address, String avatar, int status, int invoiceQuantity, int totalInvoices, List<InvoicePaid> invoicesPaid, List<InvoiceOwed> invoicesOwed) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.address = address;
        this.avatar = avatar;
        this.status = status;
        this.invoiceQuantity = invoiceQuantity;
        this.totalInvoices = totalInvoices;
        this.invoicesPaid = invoicesPaid;
        this.invoicesOwed = invoicesOwed;
    }

    public Customer() {
    }

    public Customer(String customerName, String customerPhone, String customerEmail, String address) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getInvoiceQuantity() {
        return invoiceQuantity;
    }

    public void setInvoiceQuantity(int invoiceQuantity) {
        this.invoiceQuantity = invoiceQuantity;
    }

    public int getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(int totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public List<InvoicePaid> getInvoicesPaid() {
        return invoicesPaid;
    }

    public void setInvoicesPaid(List<InvoicePaid> invoicesPaid) {
        this.invoicesPaid = invoicesPaid;
    }

    public List<InvoiceOwed> getInvoicesOwed() {
        return invoicesOwed;
    }

    public void setInvoicesOwed(List<InvoiceOwed> invoicesOwed) {
        this.invoicesOwed = invoicesOwed;
    }
}
