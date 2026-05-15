package com.nikhil;

public class PrintSubscriber implements Subscriber {
    String name;

    public PrintSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("Printing message: " + message.getPayload() + " " + message.getTimestamp());
    }
}
