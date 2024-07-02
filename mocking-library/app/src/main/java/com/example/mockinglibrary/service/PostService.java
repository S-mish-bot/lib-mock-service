package com.example.mockinglibrary.service;

import com.example.mockinglibrary.dto.RequestDTO;
import com.example.mockinglibrary.dto.ResponseDTO;
import com.example.mockinglibrary.dto.UserRequestDTO;
import com.example.mockinglibrary.dto.UserResponseDTO;
import com.example.mockinglibrary.entity.Post;
import com.example.mockinglibrary.entity.User;
import com.example.mockinglibrary.repository.PostRepository;
import com.example.mockinglibrary.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
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

    public UserResponseDTO createUser(UserRequestDTO request) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        try {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user = userRepository.save(user);

            responseDTO.setUser(user);

            RestTemplate restTemplate = new RestTemplate();
            String apiResponse = restTemplate.getForObject("http://worldtimeapi.org/api/timezone/Asia/Kolkata", String.class);
            responseDTO.setHttpOutbound(apiResponse);
            return responseDTO;

        } catch (Exception e) {
            e.printStackTrace();
            return new UserResponseDTO();
        }
    }
}
