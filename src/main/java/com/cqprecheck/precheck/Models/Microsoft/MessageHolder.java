package com.cqprecheck.precheck.Models.Microsoft;

public class MessageHolder {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public MessageHolder(Message message) {
        this.message = message;
    }
}
