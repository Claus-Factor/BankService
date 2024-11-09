// User.java
package com.bank.model;

public class User {
    private Long id; // в базе данных это был бы первичный ключ;
    private String name;
    private String email;
    private String telephone;
    private String passwordHash;

    public User(Long id, String name, String email, String telephone, String passwordHash) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.telephone = telephone;
        this.passwordHash = passwordHash;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = passwordHash;
    }
}
