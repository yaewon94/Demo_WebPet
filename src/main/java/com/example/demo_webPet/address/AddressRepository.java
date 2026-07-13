package com.example.demo_webPet.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {

    @Query("""
    select a
    from Address a
    where a.parentId is not null
    """)
    List<Address> findByParentIdIsNotNull();

    @Query("""
    select a
    from Address a
    where a.parentId = :sido
    and a.id = :sigungu
    """)
    Address findBySidoAndSigungu(String sido, String sigungu);

    @Modifying
    @Query("""
    delete from Address a
    where a.updateDate < CURRENT_DATE
    """)
    void deleteOldAddresses();
}
