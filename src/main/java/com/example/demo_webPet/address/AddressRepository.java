package com.example.demo_webPet.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {

    @Query("""
    select a
    from Address a
    where a.parentId = :sido
    and a.id = :sigungu
    """)
    Address findBySidoAndSigungu(String sido, String sigungu);

    @Query("""
    select new com.example.demo_webPet.address.AddressResponse(
        a.id,
        a.name
    )
    from Address a
    where a.parentId is null
    order by a.name
    """)
    List<AddressResponse> findSidoList();

    @Query("""
    select new com.example.demo_webPet.address.AddressResponse(
        a.id,
        a.name
    )
    from Address a
    where a.parentId = :parentId
    order by a.name
    """)
    List<AddressResponse> findByParentId(String parentId);

    @Modifying
    @Query("""
    delete from Address a
    where a.updateDate < CURRENT_DATE
    """)
    void deleteOldAddresses();
}
