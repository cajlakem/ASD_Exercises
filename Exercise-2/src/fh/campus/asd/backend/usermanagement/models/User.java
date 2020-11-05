package fh.campus.asd.backend.usermanagement.models;

public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;

    public String getPassword() {
        return password;
    }

    public User(String userName, String firstName, String lastName, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean amINamed(String name){
        return userName.equals(name);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
