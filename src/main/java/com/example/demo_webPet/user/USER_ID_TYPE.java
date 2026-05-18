package com.example.demo_webPet.user;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class USER_ID_TYPE implements Serializable {
    @Override
    public String toString() {
        return id;
    }

    private String id;

    public USER_ID_TYPE(){}

    public static USER_ID_TYPE of(String id){
        USER_ID_TYPE tmp = new USER_ID_TYPE();
        tmp.id = id;
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof USER_ID_TYPE)) return false;
        USER_ID_TYPE tmp = (USER_ID_TYPE) o;
        return id != null && id.equals(tmp.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}