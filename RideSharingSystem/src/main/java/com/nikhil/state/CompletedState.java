package com.nikhil.state;

import com.nikhil.Trip;

public class CompletedState implements TripState {
    @Override
    public void assign(Trip trip) {
        System.out.println("Trip already completed");
    }

    @Override
    public void start(Trip trip) {
        System.out.println("Trip already completed");
    }

    @Override
    public void moveInProgress(Trip trip) {
        System.out.println("Trip already completed");
    }

    @Override
    public void cancel(Trip trip) {
        System.out.println("Trip already completed");
    }

    @Override
    public void complete(Trip trip) {
        System.out.println("Trip already completed");
    }
}
