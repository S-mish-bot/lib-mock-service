package com.example.mockinglibrary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

public class RequestDTO {
    @NonNull
    @JsonProperty(value = "post_name")
    private String postName;
    @NonNull
    @JsonProperty(value = "post_contents")
    private String postContent;

    @NonNull
    public String getPostName() {
        return postName;
    }

    public void setPostName(@NonNull String postName) {
        this.postName = postName;
    }

    @NonNull
    public String getPostContent() {
        return postContent;
    }

    public RequestDTO() {
        postName = "";
        postContent = "";
    }

    public void setPostContent(@NonNull String postContent) {
        this.postContent = postContent;
    }
    public RequestDTO(@NonNull String postName, @NonNull String postContent) {
        this.postName = postName;
        this.postContent = postContent;
    }
}
