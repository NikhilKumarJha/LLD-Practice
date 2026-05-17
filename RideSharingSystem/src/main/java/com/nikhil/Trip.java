package com.nikhil;

import com.nikhil.state.RequestedState;
import com.nikhil.state.TripState;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class Trip {

    private String id;
    private TripState state;
    private Rider rider;
    private Driver driver;
    private Instant startTime;
    private Instant endTime;
    private final ReentrantLock lock = new ReentrantLock();

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

    public Trip(Rider rider, Driver driver) {
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

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    // Package-private method for internal state access (used by state classes)
    TripState getState() {
        return state;
    }

    // Public method for state mutation (used by state classes in different package)
    // Note: This should only be called from within state transition methods
    public void setState(TripState state) {
        this.state = state;
    }

    // Thread-safe method to assign a driver to the trip
    public void assignDriver() {
        lock.lock();
        try {
            state.assign(this);
        } finally {
            lock.unlock();
        }
    }

    // Thread-safe method to start the trip
    public void startTrip() {
        lock.lock();
        try {
            state.start(this);
        } finally {
            lock.unlock();
        }
    }

    // Thread-safe method to move trip in progress
    public void moveToInProgress() {
        lock.lock();
        try {
            state.moveInProgress(this);
        } finally {
            lock.unlock();
        }
    }

    // Thread-safe method to cancel the trip
    public void cancelTrip() {
        lock.lock();
        try {
            state.cancel(this);
        } finally {
            lock.unlock();
        }
    }

    // Thread-safe method to complete the trip
    public void completeTrip() {
        lock.lock();
        try {
            state.complete(this);
        } finally {
            lock.unlock();
        }
    }

}
