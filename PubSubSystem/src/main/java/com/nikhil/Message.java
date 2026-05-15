package com.nikhil;

import java.time.Instant;

public class Message {
    String payload;
    Instant timestamp;

    public String getPayload() {
        return payload;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Message(String payload) {
        this.payload = payload;
        this.timestamp = Instant.now();
    }
}
