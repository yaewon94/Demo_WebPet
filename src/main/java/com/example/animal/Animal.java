package com.example.animal;

import com.example.adoption.AdoptionRequest;
import com.example.shelter.Shelter;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
public final class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    @Getter(AccessLevel.NONE)
    private Long id;

    // EnumType.ORDINAL을 쓸 경우 enum 순서가 바뀌면 DB에 저장된거랑 달라져 데이터 꼬이기 때문에 치명적
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species = Species.DOG;

    @NotNull
    @Min(0)
    private Integer age = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex = Sex.MALE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull // java, validation 레벨 검증
    @JoinColumn(name="shelter_id", nullable=false) // DB 레벨 검증
    private Shelter shelter;

    @OneToMany(mappedBy = "adoptionRequest")
    @Getter(AccessLevel.NONE)
    private List<AdoptionRequest> adoptionRequestList;

    public void setAge(Integer age) {
        if(age < 0){
            throw new IllegalArgumentException("나이는 0살 이상이여야 합니다");
        }
        this.age = age;
    }

    public enum Species {
        DOG("강아지"),
        CAT("고양이"),
        ETC("기타");

        private final String label;

        Species(String label){
            this.label = label;
        }

        public String GetLabel(){
            return label;
        }
    }

    public enum Sex {
        MALE("수컷"),
        FEMALE("암컷");

        private final String label;

        Sex(String label){
            this.label = label;
        }

        public String GetLabel(){
            return label;
        }
    }

    public enum Status{
        PROTECTING("보호 중"),
        ADOPTED("입양 완료");

        private final String label;

        Status(String label){
            this.label = label;
        }

        public String GetLabel(){
            return label;
        }
    }
}
