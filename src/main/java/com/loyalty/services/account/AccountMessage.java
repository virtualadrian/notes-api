package com.notes.services.account;

import com.notes.core.ApplicationMessage;

class AccountMessage extends ApplicationMessage {
    AccountMessage(AccountMessageType messageType, String text) {
        super(messageType.toString(), text);
    }
}
