package com.daneshnaik.Tables;

public class Users {
    String id;
    String FullName;
    String Email;
    String MobileNumber;
    String password;

    public Users(String id, String fullName, String email, String mobileNumber, String password) {
        this.id = id;
        FullName = fullName;
        Email = email;
        MobileNumber = mobileNumber;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
