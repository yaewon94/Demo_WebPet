package com.example.demo_webPet.user;

import com.example.demo_webPet.Global.ErrorCode;

public class DuplicatedUserIdException extends UserException{

    public DuplicatedUserIdException(){
        super(ErrorCode.DUPLICATED_USER_ID);
    }
}
