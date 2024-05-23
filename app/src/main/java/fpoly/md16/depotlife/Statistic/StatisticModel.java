package fpoly.md16.depotlife.Statistic;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class StatisticModel implements Serializable {
    public class Invoice {
        @SerializedName("invoice_id")
        private int invoiceId;

        @SerializedName("created_at")
        private String createdAt;

        @SerializedName("total_amount")
        private int totalAmount;

        @SerializedName("details")
        private List<Detail> details;

        // Getters and Setters
        // ...

        public int getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(int invoiceId) {
            this.invoiceId = invoiceId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
        }

        public List<Detail> getDetails() {
            return details;
        }

        public void setDetails(List<Detail> details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return "Invoice{" +
                    "invoiceId=" + invoiceId +
                    ", createdAt='" + createdAt + '\'' +
                    ", totalAmount=" + totalAmount +
                    ", details=" + details +
                    '}';
        }
    }

    public class Detail {
        @SerializedName("product_id")
        private int productId;

        @SerializedName("quantity")
        private int quantity;

        @SerializedName("import_price")
        private int importPrice;

        @SerializedName("total_price")
        private int totalPrice;

        // Getters và Setters
        // ...

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

        public int getImportPrice() {
            return importPrice;
        }

        public void setImportPrice(int importPrice) {
            this.importPrice = importPrice;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        @Override
        public String toString() {
            return "Detail{" +
                    "productId=" + productId +
                    ", quantity=" + quantity +
                    ", importPrice=" + importPrice +
                    ", totalPrice=" + totalPrice +
                    '}';
        }
    }

    public class DailyStat {
        @SerializedName("time")
        private String time;

        @SerializedName("total_quantity")
        private int totalQuantity;

        @SerializedName("total_value")
        private int totalValue;

        @SerializedName("invoices")
        private List<Invoice> invoices;

        // Getters và Setters
        // ...

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public int getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(int totalValue) {
            this.totalValue = totalValue;
        }

        public List<Invoice> getInvoices() {
            return invoices;
        }

        public void setInvoices(List<Invoice> invoices) {
            this.invoices = invoices;
        }

        @Override
        public String toString() {
            return "DailyStat{" +
                    "time='" + time + '\'' +
                    ", totalQuantity=" + totalQuantity +
                    ", totalValue=" + totalValue +
                    ", invoices=" + invoices +
                    '}';
        }
    }

    public class MonthlyStat {
        @SerializedName("time")
        private String time;

        @SerializedName("total_quantity")
        private int totalQuantity;

        @SerializedName("total_value")
        private int totalValue;
        @SerializedName("invoice_count")
        private int invoice_count;

        // Getters và Setters
        // ...

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public int getTotalValue() {
            return totalValue;
        }

        public void setTotalValue(int totalValue) {
            this.totalValue = totalValue;
        }

        public int getInvoice_count() {
            return invoice_count;
        }

        public void setInvoice_count(int invoice_count) {
            this.invoice_count = invoice_count;
        }

        @Override
        public String toString() {
            return "MonthlyStat{" +
                    "time='" + time + '\'' +
                    ", totalQuantity=" + totalQuantity +
                    ", totalValue=" + totalValue +
                    ", invoice_count=" + invoice_count +
                    '}';
        }
    }

    public class StatsResponse {
        @SerializedName("monthly_stats")
        private Map<String, MonthlyStat> monthlyStats;

        @SerializedName("daily_stats")
        private DailyStatsWrapper dailyStats;

        // Getters và Setters
        // ...

        public Map<String, MonthlyStat> getMonthlyStats() {
            return monthlyStats;
        }

        public void setMonthlyStats(Map<String, MonthlyStat> monthlyStats) {
            this.monthlyStats = monthlyStats;
        }

        public DailyStatsWrapper getDailyStats() {
            return dailyStats;
        }

        public void setDailyStats(DailyStatsWrapper dailyStats) {
            this.dailyStats = dailyStats;
        }

        @Override
        public String toString() {
            return "StatsResponse{" +
                    "monthlyStats=" + monthlyStats +
                    ", dailyStats=" + dailyStats +
                    '}';
        }
    }

    public class DailyStatsWrapper {
        @SerializedName("data")
        private List<DailyStat> data;

        // Getters và Setters
        // ...

        public List<DailyStat> getData() {
            return data;
        }

        public void setData(List<DailyStat> data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "DailyStatsWrapper{" +
                    "data=" + data +
                    '}';
        }
    }
}
