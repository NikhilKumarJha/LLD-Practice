package com.nikhil.state;

import com.nikhil.Trip;

public class CancelledState implements TripState {
    @Override
    public void assign(Trip trip) {
        System.out.println("Trip already cancelled");
    }

    @Override
    public void start(Trip trip) {
        System.out.println("Trip already cancelled");
    }

    @Override
    public void moveInProgress(Trip trip) {
        System.out.println("Trip already cancelled");
    }

    @Override
    public void cancel(Trip trip) {
        System.out.println("Trip already cancelled");
    }

    @Override
    public void complete(Trip trip) {
        System.out.println("Trip already cancelled");
    }
}
