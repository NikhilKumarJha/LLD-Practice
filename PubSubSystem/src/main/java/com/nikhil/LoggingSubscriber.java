package com.nikhil;

public class LoggingSubscriber implements Subscriber {

    String name;

    public LoggingSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("Logging message: " + message.getPayload() + " " + message.getTimestamp());
    }
}
