package com.example.demo_webPet.animal;

import org.springframework.data.jpa.repository.JpaRepository;

interface AnimalRepository extends JpaRepository<Animal, Long> {
}
