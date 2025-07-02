package org.acme.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserPostRequest {
    @NotBlank(message = "First name is required")
    private String firstName;
    private String secondName;
    @NotBlank(message = "First last name is required")
    private String firstLastName;
    private String secondLastName;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long")
    private String phoneNumber;
    @NotBlank(message = "Country is required")
    @Size(min = 2, max = 2, message = "Country code must be exactly 2 characters long")
    private String country;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }

    public String getFirstLastName() {
        return this.firstLastName;
    }

    public void setFirstLastName(final String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return this.secondLastName;
    }

    public void setSecondLastName(final String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry(){
        return this.country;
    }

    public void setCountry(final String country){
        this.country = country;
    }
}
