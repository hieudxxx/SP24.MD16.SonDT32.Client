package fpoly.md16.depotlife.Category.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class CategoryResponse {
//    @SerializedName("current_page")
//    private int current_page; //trang hien tai
    @SerializedName("data")
    private Data[] data;
//    @SerializedName("first_page_url")
//    private String first_page_url; // url of trang dau tien
//    @SerializedName("from")
//    private int from;
//    @SerializedName("last_page")
//    private int last_page; //index of trang cuoi cung
//    @SerializedName("last_page_url")
//    private String last_page_url; //
//    @SerializedName("links")
//    private Links[] links;

    @SerializedName("next_page_url")
    private String next_page_url;
//    @SerializedName("path")
//    private String path;
//    @SerializedName("per_page")
//    private int per_page;
    @SerializedName("prev_page_url")
    private String prev_page_url;
//    @SerializedName("to")
//    private int to;
//    @SerializedName("total")
//    private int total;


    public CategoryResponse(Data[] data, String next_page_url, String prev_page_url) {
        this.data = data;
        this.next_page_url = next_page_url;
        this.prev_page_url = prev_page_url;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public static class Data {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("status")
        private int status;
        @SerializedName("created_at")
        private String created_at;

        public Data(int id, String name, int status, String created_at) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.created_at = created_at;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", status=" + status +
                    ", created_at='" + created_at + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "data=" + Arrays.toString(data) +
                ", next_page_url='" + next_page_url + '\'' +
                ", prev_page_url='" + prev_page_url + '\'' +
                '}';
    }
}
