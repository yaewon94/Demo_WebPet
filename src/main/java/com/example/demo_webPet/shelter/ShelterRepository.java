package com.example.demo_webPet.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ShelterRepository extends JpaRepository<Shelter, String> {

    @Query("""
    select s
    from Shelter s
    join s.address a
    where a.id = :sigunguCode
      and a.parentId = :sidoCode
      and s.isActive = true
    """)
    List<Shelter> findActiveShelters(String sidoCode, String sigunguCode);

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
