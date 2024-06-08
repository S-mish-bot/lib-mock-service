package com.mock.service;

import com.mock.model.Post;
import com.mock.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post createPost(String name, String contents) {
        Post post = new Post(name, contents);
        return postRepository.save(post);
    }

    public String getExternalApiResponse() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("http://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);
    }
}
