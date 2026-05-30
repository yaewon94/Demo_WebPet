package com.example.demo_webPet.user;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(String userId);
}