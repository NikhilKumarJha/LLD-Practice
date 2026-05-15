package com.nikhil;

public class PubSubService {

    private static volatile PubSubService instance = null;
    private final Broker broker = Broker.getInstance();
    private final Dispatcher dispatcher = new Dispatcher();

    private PubSubService() {
    }

    public static PubSubService getInstance() {
        if (instance == null) {
            synchronized (PubSubService.class) {
                if (instance == null) {
                    instance = new PubSubService();
                }
            }
        }
        return instance;
    }

    public synchronized void addTopic(String topicName) {
        if (topicName == null || topicName.trim().isEmpty()) {
            throw new IllegalArgumentException("Topic name cannot be null or empty");
        }
        // Check if topic already exists
        Topic existingTopic = broker.getTopic(topicName);
        if (existingTopic != null) {
            // Topic already exists, don't start another dispatcher
            return;
        }
        broker.createTopic(topicName);
        Topic topic = broker.getTopic(topicName);
        dispatcher.startDispatch(topic);
    }

    public void subscribe(String topicName, Subscriber subscriber){
        Topic topic = broker.getTopic(topicName);
        if (topic == null) {
            throw new IllegalArgumentException("Topic does not exist: " + topicName);
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null");
        }
        topic.addSubscriber(subscriber);
    }

    public void publish(String topicName, Message message){
        Topic topic = broker.getTopic(topicName);
        if (topic == null) {
            throw new IllegalArgumentException("Topic does not exist: " + topicName);
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        topic.addMessageToQueue(message);
    }


}
