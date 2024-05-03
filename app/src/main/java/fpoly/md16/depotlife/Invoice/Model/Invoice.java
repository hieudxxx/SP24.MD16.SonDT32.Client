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
    @SerializedName("discount")
    private int discount;
    @SerializedName("products")
    private Product[] products;
    @SerializedName("note")
    private String note;
    @SerializedName("term")
    private String term;
    @SerializedName("signature_name")
    private String signature_name;
//    @SerializedName("signature")
//    private String signature_img;

    @SerializedName("invoice_type")
    private int type;
    @SerializedName("pay_status")
    private int status;
    @SerializedName("total_amount")
    private int total;

    @SerializedName("signature_name")
    private String date_created;

    public static ArrayList<Invoice> filterByStatus(ArrayList<Invoice> invoices, int status) {
        ArrayList<Invoice> filteredInvoices = new ArrayList<>();

        for (Invoice invoice : invoices) {
            if (invoice.getStatus() == status) {
                filteredInvoices.add(invoice);
            }
        }
        return filteredInvoices;
    }

    public static ArrayList<Invoice> filterByType(ArrayList<Invoice> invoices, int invoiceType) {
        ArrayList<Invoice> filteredInvoices = new ArrayList<>();

        for (Invoice invoice : invoices) {
            if (invoice.getType() == invoiceType) {
                filteredInvoices.add(invoice);
            }
        }
        return filteredInvoices;
    }

    public static Comparator<Invoice> sortByAsc = (t1, t2) -> (int) (t1.getTotal() - t2.getTotal());

    public static Comparator<Invoice> sortByNewestDate = new Comparator<Invoice>() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

        @Override
        public int compare(Invoice invoice1, Invoice invoice2) {
            try {
                Date date1 = dateFormat.parse(invoice1.getDate_created());
                Date date2 = dateFormat.parse(invoice2.getDate_created());
                return date2.compareTo(date1); // Sắp xếp giảm dần theo ngày
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }
    };

    public Invoice() {
    }

    public Invoice(int id, int user_id, int discount, Product[] products, String note, String term, String signature_name, int type, int status, int total, String date_created) {
        this.id = id;
        this.user_id = user_id;
        this.discount = discount;
        this.products = products;
        this.note = note;
        this.term = term;
        this.signature_name = signature_name;
        this.type = type;
        this.status = status;
        this.total = total;
        this.date_created = date_created;
    }

    public Invoice(int user_id, int discount, Product[] products, String signature_name, int type, int status, int total) {
        this.user_id = user_id;
        this.discount = discount;
        this.products = products;
        this.signature_name = signature_name;
        this.type = type;
        this.status = status;
        this.total = total;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public static Comparator<Invoice> getSortByAsc() {
        return sortByAsc;
    }

    public static void setSortByAsc(Comparator<Invoice> sortByAsc) {
        Invoice.sortByAsc = sortByAsc;
    }

    public static Comparator<Invoice> getSortByNewestDate() {
        return sortByNewestDate;
    }

    public static void setSortByNewestDate(Comparator<Invoice> sortByNewestDate) {
        Invoice.sortByNewestDate = sortByNewestDate;
    }
}
