package com.example.mockinglibrary.controller;

import com.example.mockinglibrary.dto.RequestDTO;
import com.example.mockinglibrary.dto.UserRequestDTO;
import com.example.mockinglibrary.entity.Post;
import com.example.mockinglibrary.repository.PostRepository;
import com.example.mockinglibrary.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createNewPost")
    public ResponseEntity<?> createNewPost(@RequestBody RequestDTO request) {
        return new ResponseEntity<>(postService.createPost(request), HttpStatus.OK);
    }

    @PostMapping("/createNewUser")
    public ResponseEntity<?> createNewUser(@RequestBody UserRequestDTO request) {
        return new ResponseEntity<>(postService.createUser(request), HttpStatus.OK);
    }
}
