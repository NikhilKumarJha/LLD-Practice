package com.nikhil.state;

import com.nikhil.Trip;

import java.time.Instant;

public class InTransitState implements TripState {
    @Override
    public void assign(Trip trip) {
        System.out.println("Driver already assigned");
    }

    @Override
    public void start(Trip trip) {
        System.out.println("Trip already started");
    }

    @Override
    public void moveInProgress(Trip trip) {
        System.out.println("Trip already in progress");
    }

    @Override
    public void cancel(Trip trip) {
        trip.setEndTime(Instant.now());
        trip.setState(new CancelledState());
    }

    @Override
    public void complete(Trip trip) {
        trip.setEndTime(Instant.now());
        trip.setState(new CompletedState());
    }
}
