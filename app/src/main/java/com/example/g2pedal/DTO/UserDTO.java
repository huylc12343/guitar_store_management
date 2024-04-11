package com.example.g2pedal.DTO;

public class UserDTO {
    private String uid;
    private String fullName;
    private String mail;
    private String phone;
    private String password;

    public UserDTO() {

    }

    public UserDTO(String fullName, String mail, String phone,String password) {
        this.uid = "";
        this.fullName = fullName;
        this.mail = mail;
        this.phone = phone;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}