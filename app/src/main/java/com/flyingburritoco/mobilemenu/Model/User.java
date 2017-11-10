package com.flyingburritoco.mobilemenu.Model;

public class User {
    private String Name;
    private String Password;
    private String Email;

    public User(){

    }

    public User(String Email, String Name, String Password){
        this.Email = Email;
        this.Name = Name;
        this.Password = Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
}
