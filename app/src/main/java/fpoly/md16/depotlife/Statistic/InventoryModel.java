package fpoly.md16.depotlife.Statistic;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InventoryModel {

    public class Product {
        @SerializedName("product_id")
        private int productId;

        @SerializedName("product_name")
        private String productName;

        @SerializedName("category")
        private String category;

        @SerializedName("location")
        private String location;

        @SerializedName("expiries")
        private List<Expiry> expiries;

        @SerializedName("total_quantity")
        private int totalQuantity;

        // Getters and Setters
        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<Expiry> getExpiries() {
            return expiries;
        }

        public void setExpiries(List<Expiry> expiries) {
            this.expiries = expiries;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
        }
    }

    public class Expiry {
        @SerializedName("expiry_date")
        private String expiryDate;

        @SerializedName("total_quantity")
        private int totalQuantity;

        // Getters and Setters
        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(int totalQuantity) {
            this.totalQuantity = totalQuantity;
        }
    }

    public class InventoryResponse {
        @SerializedName("products")
        private List<Product> products;

        @SerializedName("total_inventory")
        private int totalInventory;

        // Getters and Setters
        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public int getTotalInventory() {
            return totalInventory;
        }

        public void setTotalInventory(int totalInventory) {
            this.totalInventory = totalInventory;
        }
    }
}