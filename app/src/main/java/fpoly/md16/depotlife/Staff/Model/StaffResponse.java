package fpoly.md16.depotlife.Staff.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StaffResponse {
    @SerializedName("data")
    private User[] user;
    @SerializedName("next_page_url")
    private String next_page_url;
    @SerializedName("path")
    private String path;
    @SerializedName("last_page")
    private int last_page;
    @SerializedName("total")
    private int total;




    public StaffResponse(User[] user) {
        this.user = user;
    }

    public User[] getUser() {
        return user;
    }

    public void setUser(User[] user) {
        this.user = user;
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

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    // Getters and Setters

    public static class User implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("phone_number")
        private String phoneNumber;
        @SerializedName("role")
        private int role;
        @SerializedName("avatar")
        private String avatar;
        @SerializedName("email")
        private String email;
        @SerializedName("email_verified_at")
        private Date emailVerifiedAt; // Chú ý: JSON phải có định dạng ngày thích hợp
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("address")
        private String address;
        @SerializedName("status")
        private int status;

        public User() {
        }

        public User(int id, String name, String phoneNumber, int role, String avatar, String email, Date emailVerifiedAt, String birthday, String address, int status) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.role = role;
            this.avatar = avatar;
            this.email = email;
            this.emailVerifiedAt = emailVerifiedAt;
            this.birthday = birthday;
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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getEmailVerifiedAt() {
            return emailVerifiedAt;
        }

        public void setEmailVerifiedAt(Date emailVerifiedAt) {
            this.emailVerifiedAt = emailVerifiedAt;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }


        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", phoneNumber='" + phoneNumber + '\'' +
                    ", role=" + role +
                    ", avatar='" + avatar + '\'' +
                    ", email='" + email + '\'' +
                    ", emailVerifiedAt=" + emailVerifiedAt +
                    ", birthday='" + birthday + '\'' +
                    ", address='" + address + '\'' +
                    ", status='" + status + '\'' +

                    '}';
        }
    }
    // Getters and Setters for ApiResponse


    @Override
    public String toString() {
        return "StaffResponse{" +
                "user=" + Arrays.toString(user) +
                ", next_page_url='" + next_page_url + '\'' +
                ", path='" + path + '\'' +
                ", last_page=" + last_page +
                ", total=" + total +
                '}';
    }
}