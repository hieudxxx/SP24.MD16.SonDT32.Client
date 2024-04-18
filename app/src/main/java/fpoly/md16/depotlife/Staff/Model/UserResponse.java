package fpoly.md16.depotlife.Staff.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserResponse {
    @SerializedName("user")
    private User user;
    @SerializedName("token")
    private String token;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("device_imei")
    private String device_imei;

    public UserResponse(String email, String password, String device_imei) {
        this.email = email;
        this.password = password;
        this.device_imei = device_imei;
    }

    public String getDevice_imei() {
        return device_imei;
    }

    public void setDevice_imei(String device_imei) {
        this.device_imei = device_imei;
    }

    public UserResponse() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getters and Setters

    public static class User {
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
        //        @SerializedName("password")
//        private String password; // Chú ý: Mật khẩu thường không được truyền qua API
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("address")
        private String address;
        @SerializedName("status")
        private int status;
        @SerializedName("device_imei")
        private String deviceImei;

        public User(int id, String name, String phoneNumber, int role, String avatar, String email, Date emailVerifiedAt, String birthday, String address, int status, String deviceImei) {
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
            this.deviceImei = deviceImei;
        }

        public User(String name, String phoneNumber, String avatar, String email, int status) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.avatar = avatar;
            this.email = email;
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

        public String getDeviceImei() {
            return deviceImei;
        }

        public void setDeviceImei(String deviceImei) {
            this.deviceImei = deviceImei;
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
                    ", deviceImei='" + deviceImei + '\'' +
                    '}';
        }
    }
    // Getters and Setters for ApiResponse


    @Override
    public String toString() {
        return "UserResponse{" +
                "user=" + user +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}