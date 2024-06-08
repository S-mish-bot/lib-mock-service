package com.mock.controller;

import com.mock.model.Post;
import com.mock.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/createNewPost")
    public ResponseEntity<Map<String, Object>> createNewPost(@RequestBody Map<String, String> request) {
        try {
            String postName = request.get("post_name");
            String postContents = request.get("post_contents");
            Post post = postService.createPost(postName, postContents);
            String httpResponse = postService.getExternalApiResponse();
            Map<String, Object> response = new HashMap<>();
            response.put("db_post", post);
            response.put("http_outbound", httpResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
