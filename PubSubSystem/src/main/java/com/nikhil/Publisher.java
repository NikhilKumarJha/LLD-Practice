package com.nikhil;

public class Publisher {
    private String name;

    public void publish(Topic topic, Message message){
        topic.getMessageBlockingQueue().add(message);
    }

}
