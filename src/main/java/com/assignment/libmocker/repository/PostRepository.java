package com.assignment.libmocker.repository;

import com.assignment.libmocker.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

