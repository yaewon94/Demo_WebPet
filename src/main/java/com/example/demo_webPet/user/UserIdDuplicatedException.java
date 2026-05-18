package com.example.demo_webPet.user;

import com.example.demo_webPet.Global.ErrorCode;

public class UserIdDuplicatedException extends UserException{

    public UserIdDuplicatedException(){
        super(ErrorCode.DUPLICATED_USER_ID);
    }
}
