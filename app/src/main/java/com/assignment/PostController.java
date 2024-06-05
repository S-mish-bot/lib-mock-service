package com.assignment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/createNewPost")
    public ResponseEntity<?> createNewPost(@RequestBody Post post) {
        log.info("Creating new post: {}", post.getContent());
        try {

            Post savedPost = postRepository.save(post);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject("https://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);

            CreatePostResponse createPostResponse = new CreatePostResponse(savedPost, response);
            return ResponseEntity.ok(createPostResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Getter
    @AllArgsConstructor
    static class CreatePostResponse {
        private final Post dbPost;
        private final String httpOutbound;

        //        public CreatePostResponse(Post dbPost, String httpOutbound) {
//            this.dbPost = dbPost;
//            this.httpOutbound = httpOutbound;
//        }

        // Getters and Setters
    }
}

