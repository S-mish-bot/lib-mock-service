package com.example.mockinglibrary.service;

import com.example.mockinglibrary.dto.RequestDTO;
import com.example.mockinglibrary.dto.ResponseDTO;
import com.example.mockinglibrary.entity.Post;
import com.example.mockinglibrary.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseDTO createPost(RequestDTO request) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Post post = new Post();
            post.setName(request.getPostName());
            post.setContents(request.getPostContent());
            post = postRepository.save(post);

            responseDTO.setPost(post);

            RestTemplate restTemplate = new RestTemplate();
            String apiResponse = restTemplate.getForObject("http://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);
            responseDTO.setHttpOutbound(apiResponse);
            return responseDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO();
        }
    }
}
