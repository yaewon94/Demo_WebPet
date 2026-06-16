package com.example.demo_webPet.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // SELECT * FROM 테이블 WHERE 컬럼=값
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);
}