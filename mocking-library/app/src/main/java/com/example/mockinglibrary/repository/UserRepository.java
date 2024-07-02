package com.example.mockinglibrary.repository;

import com.example.mockinglibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
