package com.example.demo_webPet.user;

public class UserIdDuplicatedException extends UserException{

    public UserIdDuplicatedException(){
        super(UserCode.ERROR_DUPLICATED_USER_ID);
    }
}
