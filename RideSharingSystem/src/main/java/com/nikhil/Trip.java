package com.nikhil;

import com.nikhil.state.RequestedState;
import com.nikhil.state.TripState;

import java.time.Instant;
import java.util.UUID;

public class Trip {

    private String id;
    private TripState state;
    private User rider;
    private User driver;
    private Instant startTime;
    private Instant endTime;

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Trip(User rider, User driver) {
        this.id = UUID.randomUUID().toString();
        this.rider = rider;
        this.driver = driver;
        this.state = new RequestedState();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getRider() {
        return rider;
    }

    public void setRider(User rider) {
        this.rider = rider;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public TripState getState() {
        return state;
    }

    public void setState(TripState state) {
        this.state = state;
    }

}
