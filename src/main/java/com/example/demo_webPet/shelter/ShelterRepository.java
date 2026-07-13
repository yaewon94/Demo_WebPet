package com.example.demo_webPet.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

interface ShelterRepository extends JpaRepository<Shelter, String> {

    @Modifying
    @Query("""
    update Shelter s
    set s.isActive = false
    where s.isActive = true
    and not exists (
        select b.id
        from RescuedAnimalBoard b
        where b.shelter = s
        and b.isValid = true)
    """)
    void deactivateUnusedShelters();
}
