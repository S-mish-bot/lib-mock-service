package com.assignment.libmocker.controller;

import com.assignment.libmocker.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/createNewPost")
    public ResponseEntity<Map<String, Object>> createNewPost(@RequestBody Map<String, String> request) {
        return postService.createPost(request);

    }
}

