package com.example.demo_webPet.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, String> {

    @Modifying
    @Query("""
    delete from Address a
    where a.updateDate < CURRENT_DATE
    """)
    void deleteOldAddresses();
}
