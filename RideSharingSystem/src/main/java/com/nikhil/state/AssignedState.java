package com.nikhil.state;

import com.nikhil.Trip;

import java.time.Instant;

public class AssignedState implements TripState {
    @Override
    public void assign(Trip trip) {
        System.out.println("Driver already assigned");
    }

    @Override
    public void start(Trip trip) {
        trip.setStartTime(Instant.now());
        trip.setState(new InTransitState());
    }

    @Override
    public void moveInProgress(Trip trip) {
        System.out.println("Assigning the driver");
    }

    @Override
    public void cancel(Trip trip) {
        trip.setState(new CancelledState());
    }

    @Override
    public void complete(Trip trip) {
        System.out.println("Trip not started");
    }
}
