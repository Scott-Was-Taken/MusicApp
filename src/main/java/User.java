public class User {
    private String username;
    private String password;

    // Create a class constructor for the user class
    public User(String Username, String Password) {
        setUsername(Username);
        setPassword(Password);
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
}
