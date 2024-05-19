package fpoly.md16.depotlife.Supplier.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import fpoly.md16.depotlife.Invoice.Model.Invoice;

public class Supplier implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("supplier_name")
    private String name;
    @SerializedName("supplier_phone")
    private String phone;
    @SerializedName("tax")
    private String tax_code;
    @SerializedName("address")
    private String address;
    @SerializedName("total_amount")
    private long total;
    @SerializedName("status")
    private int status;
    @SerializedName("invoices")
    private List<Invoice> invoices;

    public static ArrayList<Supplier> filterByStatus(ArrayList<Supplier> list, boolean status) {
        ArrayList<Supplier> filteredList = new ArrayList<>();

//        for (Supplier supplier : list) {
//            if (supplier.isStatus() == status) {
//                filteredList.add(supplier);
//            }
//        }
        return filteredList;
    }

    public static Comparator<Supplier> sortByAsc = (t1, t2) -> (int) (t1.getId() - t2.getId());

    public static Comparator<Supplier> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());


    public Supplier() {
    }

    public Supplier(int id, String name, String phone, String tax_code, String address, int status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.tax_code = tax_code;
        this.address = address;
        this.status = status;
    }

    public Supplier(String name, String phone, String tax_code, String address, int status) {
        this.name = name;
        this.phone = phone;
        this.tax_code = tax_code;
        this.address = address;
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
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

    public static class Invoice {
        @SerializedName("id")
        private int id;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("total_amount")
        private long totalAmount;
        @SerializedName("invoice_type")
        private int invoiceType;
        @SerializedName("customer_id")
        private Integer customerId; // Nullable
        @SerializedName("supplier_id")
        private int supplierId;
        @SerializedName("pay_status")
        private int payStatus;
        @SerializedName("discount")
        private Double discount; // Nullable
        @SerializedName("due_date")
        private String dueDate; // Nullable
        @SerializedName("note")
        private String note; // Nullable
        @SerializedName("term")
        private String term; // Nullable
        @SerializedName("signature_name")
        private String signatureName;
        @SerializedName("signature")
        private String signature;
        @SerializedName("created_at")
        private String createdAt;

        public Invoice() {
        }

        public Invoice(int id, int userId, long totalAmount, int invoiceType, Integer customerId, int supplierId, int payStatus, Double discount, String dueDate, String note, String term, String signatureName, String signature) {
            this.id = id;
            this.userId = userId;
            this.totalAmount = totalAmount;
            this.invoiceType = invoiceType;
            this.customerId = customerId;
            this.supplierId = supplierId;
            this.payStatus = payStatus;
            this.discount = discount;
            this.dueDate = dueDate;
            this.note = note;
            this.term = term;
            this.signatureName = signatureName;
            this.signature = signature;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }

        public int getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(int supplierId) {
            this.supplierId = supplierId;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
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

        public String getSignatureName() {
            return signatureName;
        }

        public void setSignatureName(String signatureName) {
            this.signatureName = signatureName;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", tax_code='" + tax_code + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
