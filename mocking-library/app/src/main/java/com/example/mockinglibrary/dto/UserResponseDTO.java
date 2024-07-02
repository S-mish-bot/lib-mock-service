package com.example.mockinglibrary.dto;

import com.example.mockinglibrary.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponseDTO {
    @JsonProperty(value = "db_user")
    private User user;
    @JsonProperty(value = "http_outbound")
    private String httpOutbound;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User user, String httpOutbound) {
        this.user = user;
        this.httpOutbound = httpOutbound;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHttpOutbound() {
        return httpOutbound;
    }

    public void setHttpOutbound(String httpOutbound) {
        this.httpOutbound = httpOutbound;
    }
}
