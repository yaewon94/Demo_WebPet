package com.example.animal;

import com.example.shelter.Shelter;
import jakarta.persistence.*;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    // EnumType.ORDINAL을 쓸 경우 enum 순서가 바뀌면 DB에 저장된거랑 달라져 데이터 꼬이기 때문에 치명적
    @Enumerated(EnumType.STRING)
    private Species species;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name="shelter_id")
    private Shelter shelter;

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
