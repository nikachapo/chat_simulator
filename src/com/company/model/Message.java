package com.company.model;

import java.io.Serializable;

public class Message implements Serializable {

    private final String text;
    private final String senderName;

    public Message(String text, String senderName) {
        this.text = text;
        this.senderName = senderName;
    }

    @Override
    public String toString() {
        return senderName + ": " + text;
    }
}
