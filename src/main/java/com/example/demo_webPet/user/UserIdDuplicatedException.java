package com.example.demo_webPet.user;

class UserIdDuplicatedException extends UserException{

    public UserIdDuplicatedException(){
        super(UserCode.ERROR_DUPLICATED_USER_ID);
    }
}
