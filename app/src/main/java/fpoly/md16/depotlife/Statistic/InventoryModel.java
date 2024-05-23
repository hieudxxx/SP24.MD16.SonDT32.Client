package fpoly.md16.depotlife.Statistic;

import java.util.List;
import java.util.Map;

public class InventoryModel {

    public static class InventoryResponse {
        private Map<String, List<Product>> products;
        private int total_inventory;

        public Map<String, List<Product>> getProducts() {
            return products;
        }

        public void setProducts(Map<String, List<Product>> products) {
            this.products = products;
        }

        public int getTotalInventory() {
            return total_inventory;
        }

        public void setTotalInventory(int total_inventory) {
            this.total_inventory = total_inventory;
        }
    }

    public static class Product {
        private int product_id;
        private String product_name;
        private String category;
        private Location location;
        private String message;
        private List<Expiry> expiries;
        private int total_quantity;

        public int getProductId() {
            return product_id;
        }

        public void setProductId(int product_id) {
            this.product_id = product_id;
        }

        public String getProductName() {
            return product_name;
        }

        public void setProductName(String product_name) {
            this.product_name = product_name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Expiry> getExpiries() {
            return expiries;
        }

        public void setExpiries(List<Expiry> expiries) {
            this.expiries = expiries;
        }

        public int getTotalQuantity() {
            return total_quantity;
        }

        public void setTotalQuantity(int total_quantity) {
            this.total_quantity = total_quantity;
        }
    }

    public static class Location {
        private int zone;
        private String shelf;
        private int level;
        private String code;

        public int getZone() {
            return zone;
        }

        public void setZone(int zone) {
            this.zone = zone;
        }

        public String getShelf() {
            return shelf;
        }

        public void setShelf(String shelf) {
            this.shelf = shelf;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public static class Expiry {
        private String expiry_date;
        private int total_quantity;

        public String getExpiryDate() {
            return expiry_date;
        }

        public void setExpiryDate(String expiry_date) {
            this.expiry_date = expiry_date;
        }

        public int getTotalQuantity() {
            return total_quantity;
        }

        public void setTotalQuantity(int total_quantity) {
            this.total_quantity = total_quantity;
        }
    }
}