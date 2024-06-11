package com.example.mockinglibrary.dto;

import com.example.mockinglibrary.entity.Post;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO {
    @JsonProperty(value = "db_post")
    private Post post;
    @JsonProperty(value = "http_outbound")
    private String httpOutbound;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getHttpOutbound() {
        return httpOutbound;
    }

    public void setHttpOutbound(String httpOutbound) {
        this.httpOutbound = httpOutbound;
    }

    public ResponseDTO(Post post, String httpOutbound) {
        this.post = post;
        this.httpOutbound = httpOutbound;
    }

    public ResponseDTO() {
    }
}
