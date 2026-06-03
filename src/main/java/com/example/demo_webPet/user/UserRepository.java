package com.example.demo_webPet.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId); // SELECT * FROM 테이블 WHERE 컬럼=값
}