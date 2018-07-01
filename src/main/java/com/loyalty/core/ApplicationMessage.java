package com.notes.core;

import com.notes.services.account.AccountMessageType;
import lombok.Data;

@Data
public class ApplicationMessage {

    public ApplicationMessage(){
        this("","");
    }

    public ApplicationMessage(String type){
        this(type, "");
    }

    public ApplicationMessage(String type, String text){
        messageType = type;
        messageText = text;
    }

    private String messageType;

    private String messageText;
}
