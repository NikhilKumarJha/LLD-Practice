package com.nikhil;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws InterruptedException{
        PubSubService pubSubService = PubSubService.getInstance();

        // Create topics
        pubSubService.addTopic("sports");
        pubSubService.addTopic("tech");

        // Create subscribers
        Subscriber printSubscriber = new PrintSubscriber("P1");
        Subscriber loggingSubscriber = new LoggingSubscriber("L1");

        // Subscribe to topics
        pubSubService.subscribe("sports", printSubscriber);
        pubSubService.subscribe("sports", loggingSubscriber);
        pubSubService.subscribe("tech", printSubscriber);

        // Publish messages
        pubSubService.publish("sports", new Message("India won the match!"));
        pubSubService.publish("sports", new Message("IPL finals tonight!"));
        pubSubService.publish("tech", new Message("Java 21 released!"));

        // Give dispatcher threads time to process
        Thread.sleep(1000);
    }
}
