package com.example.mockinglibrary.repository;

import com.example.mockinglibrary.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
