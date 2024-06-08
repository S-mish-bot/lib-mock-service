package com.example.mockinglibrary.controller;

import com.example.mockinglibrary.entity.Post;
import com.example.mockinglibrary.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/createNewPost")
    public Map<String, Object> createNewPost(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Post post = new Post();
            post.setName(request.get("post_name"));
            post.setContents(request.get("post_contents"));
            post = postRepository.save(post);

            response.put("db_post", post);

            RestTemplate restTemplate = new RestTemplate();
            String apiResponse = restTemplate.getForObject("http://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);

            response.put("http_outbound", apiResponse);
        } catch (Exception e) {
            response.put("error", e.getMessage());
        }

        return response;
    }
}
