package com.nikhil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {
    private final Map<String, Topic> topicNameVsTopic = new ConcurrentHashMap<>();

    public Map<String, Topic> getTopicNameVsTopic() {
        return topicNameVsTopic;
    }

    private static volatile Broker instance = null;

    private Broker() {
    }

    public static Broker getInstance() {
        if (instance == null) {
            synchronized (Broker.class) {
                if (instance == null) {
                    instance = new Broker();
                }
            }
        }
        return instance;
    }

    public void createTopic(String topicName) {
        Topic topic = new Topic(topicName);
        topicNameVsTopic.put(topic.getName(), topic);
    }

    public Topic getTopic(String topicName) {
        return topicNameVsTopic.get(topicName);
    }

}
