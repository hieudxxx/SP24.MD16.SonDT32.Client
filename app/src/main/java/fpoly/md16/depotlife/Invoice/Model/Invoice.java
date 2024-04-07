package fpoly.md16.depotlife.Invoice.Model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Invoice implements Serializable {
    private String id;
    private String user_id;
    private String date_created;
    private double total;
    private int type;
    private int status;

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

    public Invoice(String id, String user_id, String date_created, double total, int type, int status) {
        this.id = id;
        this.user_id = user_id;
        this.date_created = date_created;
        this.total = total;
        this.type = type;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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
}
