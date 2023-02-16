package api;

public class AuthorizationData {
    private String email;
    private String password;
    public AuthorizationData(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public static AuthorizationData from(User user) {
        return new AuthorizationData(user.getEmail(), user.getPassword());
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
}
