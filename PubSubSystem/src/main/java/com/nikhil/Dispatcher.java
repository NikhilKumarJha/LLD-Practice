package com.nikhil;

import java.util.List;

public class Dispatcher {

    public void startDispatch(Topic topic){
        Thread thread = new Thread(() -> {
            while(true){
                try {
                    Message message = topic.getMessageBlockingQueue().take();
                    List<Subscriber> subscribers = topic.getSubscribers();
                    for (Subscriber subscriber : subscribers) {
                        subscriber.onMessage(message);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
