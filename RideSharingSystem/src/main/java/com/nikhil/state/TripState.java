package com.nikhil.state;

import com.nikhil.Trip;

public interface TripState {
    void assign(Trip trip);
    void start(Trip trip);
    void moveInProgress(Trip trip);
    void cancel(Trip trip);
    void complete(Trip trip);
}
