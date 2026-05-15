package com.nikhil;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher {

    public void startDispatch(Topic topic){
        Thread thread = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Message message = topic.getMessageBlockingQueue().take();
                    // Create a snapshot of subscribers to avoid concurrent modification
                    List<Subscriber> subscriberSnapshot = new ArrayList<>(topic.getSubscribers());
                    for (Subscriber subscriber : subscriberSnapshot) {
                        try {
                            subscriber.onMessage(message);
                        } catch (Exception e) {
                            // Log error but continue processing for other subscribers
                            System.err.println("Error processing message for subscriber: " + e.getMessage());
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
