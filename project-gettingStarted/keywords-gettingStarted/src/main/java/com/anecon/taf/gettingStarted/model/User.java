package com.anecon.taf.gettingStarted.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown=true)
public class User {

    private String id;
    private String userName;
    private String salutationName;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private String password;
    private String passwordConfirm;
    private boolean isAdmin;

    public User() {

    }

    public User(String userName, String password, String salutationName, String firstName, String lastName, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.passwordConfirm = password;
        this.salutationName = salutationName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = true;
        this.isAdmin = isAdmin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getSalutationName() {
        return salutationName;
    }

    public void setSalutationName(String salutationName) {
        this.salutationName = salutationName;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}