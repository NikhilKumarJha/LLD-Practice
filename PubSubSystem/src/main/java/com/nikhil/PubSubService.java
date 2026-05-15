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

    public void addTopic(String topicName) {
        broker.createTopic(topicName);
        Topic topic = broker.getTopic(topicName);
        dispatcher.startDispatch(topic);
    }

    public void subscribe(String topicName, Subscriber subscriber){
        Topic topic = broker.getTopic(topicName);
        topic.addSubscriber(subscriber);
    }

    public void publish(String topicName, Message message){
        Topic topic = broker.getTopic(topicName);
        topic.addMessageToQueue(message);
    }


}
