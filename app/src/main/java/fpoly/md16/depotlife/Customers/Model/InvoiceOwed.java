package fpoly.md16.depotlife.Customers.Model;

import com.google.gson.annotations.SerializedName;

public class InvoiceOwed {
    @SerializedName("customer_id")
    private int customerId;

    @SerializedName("total_owed")
    private String totalOwed;

    // Constructor
    public InvoiceOwed(int customerId, String totalOwed) {
        this.customerId = customerId;
        this.totalOwed = totalOwed;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTotalOwed() {
        return totalOwed;
    }

    public void setTotalOwed(String totalOwed) {
        this.totalOwed = totalOwed;
    }

}
