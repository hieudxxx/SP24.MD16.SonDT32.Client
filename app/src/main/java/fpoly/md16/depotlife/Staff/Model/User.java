package fpoly.md16.depotlife.Staff.Model;

public class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String avt;
    private boolean role;
    private boolean status;

    public User() {
    }

    public User(String id, String name, String email, String phone, String username, String password, String avt, boolean role, boolean status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.avt = avt;
        this.role = role;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
