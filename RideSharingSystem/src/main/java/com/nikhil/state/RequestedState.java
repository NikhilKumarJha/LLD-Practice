package com.nikhil.state;

import com.nikhil.Driver;
import com.nikhil.Rider;
import com.nikhil.Trip;

public class RequestedState implements TripState {
    @Override
    public void assign(Trip trip) {
        trip.setDriver(new Driver());
        trip.setRider(new Rider());
        trip.setState(new AssignedState());
    }

    @Override
    public void start(Trip trip) {
        System.out.println("No driver assigned");
    }

    @Override
    public void moveInProgress(Trip trip) {
        System.out.println("No driver assigned");
    }

    @Override
    public void cancel(Trip trip) {
        trip.setState(new CancelledState());
    }

    @Override
    public void complete(Trip trip) {
        System.out.println("Trip yet not started");
    }
}
