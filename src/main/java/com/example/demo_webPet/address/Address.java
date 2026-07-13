package com.example.demo_webPet.address;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="TB_Address")
public class Address {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private String parent_id; // 시군구 일 경우, 부모 시/도 코드

    @Column(nullable = false)
    private LocalDate updateDate;

    @PrePersist
    @PreUpdate
    private void updateDate() {
        setUpdateDate(LocalDate.now());
    }

    static Address from(AddressDto dto){
        Address address = new Address();
        address.setId(dto.orgCd());
        address.setName(dto.orgdownNm());
        address.setParent_id(dto.uprCd());
        return address;
    }

    void update(AddressDto dto){
        setId(dto.orgCd());
        setName(dto.orgdownNm());
        setParent_id(dto.uprCd());
    }
}
