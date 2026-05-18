package com.example.demo_webPet.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, USER_ID_TYPE> {
}