package fpoly.md16.depotlife.Category.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class CategoryResponse {
    @SerializedName("current_page")
    private int current_page;
    @SerializedName("data")
    private Data[] data;
    @SerializedName("first_page_url")
    private String first_page_url;
    @SerializedName("from")
    private int from;
    @SerializedName("last_page")
    private int last_page;
    @SerializedName("last_page_url")
    private String last_page_url;
    @SerializedName("links")
    private Links[] links;

    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("per_page")
    private int per_page;
    @SerializedName("prev_page_url")
    private String prev_page_url;
    @SerializedName("to")
    private int to;
    @SerializedName("total")
    private int total;

    public CategoryResponse(int current_page, Data[] data, String first_page_url, int from, int last_page, String last_page_url, Links[] links, String next_page_url, String path, int per_page, String prev_page_url, int to, int total) {
        this.current_page = current_page;
        this.data = data;
        this.first_page_url = first_page_url;
        this.from = from;
        this.last_page = last_page;
        this.last_page_url = last_page_url;
        this.links = links;
        this.next_page_url = next_page_url;
        this.path = path;
        this.per_page = per_page;
        this.prev_page_url = prev_page_url;
        this.to = to;
        this.total = total;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public String getFirst_page_url() {
        return first_page_url;
    }

    public void setFirst_page_url(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getLast_page_url() {
        return last_page_url;
    }

    public void setLast_page_url(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public Links[] getLinks() {
        return links;
    }

    public void setLinks(Links[] links) {
        this.links = links;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
        @SerializedName("updated_at")
        private String updated_at;

        public Data(int id, String name, int status, String created_at, String updated_at) {
            this.id = id;
            this.name = name;
            this.status = status;
            this.created_at = created_at;
            this.updated_at = updated_at;
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

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", status=" + status +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    '}';
        }
    }

    public static class Links {
        @SerializedName("url")
        private String url;
        @SerializedName("label")
        private String label;
        @SerializedName("active")
        private Boolean active;

        public Links(String url, String label, Boolean active) {
            this.url = url;
            this.label = label;
            this.active = active;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        @Override
        public String toString() {
            return "Links{" +
                    "url='" + url + '\'' +
                    ", label='" + label + '\'' +
                    ", active=" + active +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CategoryResponse{" +
                "current_page=" + current_page +
                ", data=" + Arrays.toString(data) +
                ", first_page_url='" + first_page_url + '\'' +
                ", from=" + from +
                ", last_page=" + last_page +
                ", last_page_url='" + last_page_url + '\'' +
                ", links=" + Arrays.toString(links) +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", per_page=" + per_page +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", to=" + to +
                ", total=" + total +
                '}';
    }
}
