package com.flyingburritoco.mobilemenu.Model;

public class User {
    private String Name;
    private String Password;
    private String Email;
    private String Phone;

    public User(){

    }

    public User(String Email, String Name, String Password, String Phone){
        this.Email = Email;
        this.Name = Name;
        this.Password = Password;
        this.Phone = Phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
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
