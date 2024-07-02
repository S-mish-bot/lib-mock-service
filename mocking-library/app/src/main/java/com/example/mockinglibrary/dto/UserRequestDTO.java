package com.example.mockinglibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestDTO {
    @JsonProperty(value = "user_name")
    private String name;
    @JsonProperty(value = "user_email")
    private String email;
    @JsonProperty(value = "user_mobile")
    private String phoneNumber;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
