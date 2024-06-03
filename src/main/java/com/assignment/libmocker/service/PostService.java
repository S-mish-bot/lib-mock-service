package com.assignment.libmocker.service;

import com.assignment.libmocker.entity.Post;
import com.assignment.libmocker.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<Map<String, Object>> createPost(final Map<String, String> request) {
        log.info("---------------RECORD MODE----------------");
        String postName = request.get("post_name");
        log.info("Post Name: {}", postName);
        String postContents = request.get("post_contents");
        log.info("Post Contents: {}", postContents);

        Post post = new Post();
        post.setName(postName);
        post.setContent(postContents);

        try {
            Post savedPost = postRepository.save(post);
            if (savedPost == null) {
                throw new Exception("Failed to save post");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("Saved Post: {}", objectMapper.writeValueAsString(savedPost));
            String httpResponse = makeExternalHttpCall();
            Map<String, Object> response = new HashMap<>();
            response.put("db_post", savedPost.getId());
            response.put("http_outbound", httpResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while saving post or making HTTP call", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private String makeExternalHttpCall() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://worldtimeapi.org/api/timezone/Asia/Kolkata";
        return restTemplate.getForObject(url, String.class);
    }
}
