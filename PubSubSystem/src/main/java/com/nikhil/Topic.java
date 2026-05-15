package com.nikhil;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class Topic {
    private final String name;
    private final BlockingQueue<Message> messageBlockingQueue = new LinkedBlockingQueue<>(100);
    private final List<Subscriber> subscribers = new CopyOnWriteArrayList<>();

    public Topic(String topicName) {
        this.name = topicName;
    }

    public String getName() {
        return name;
    }

    public BlockingQueue<Message> getMessageBlockingQueue() {
        return messageBlockingQueue;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void addMessageToQueue(Message message){
        messageBlockingQueue.add(message);
    }
}
