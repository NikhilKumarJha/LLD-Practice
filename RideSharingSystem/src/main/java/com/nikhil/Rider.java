package com.nikhil;

import java.util.ArrayList;
import java.util.List;

public class Rider extends User {
    private List<Trip> tripHistory;
    private Location homeLocation;
    private Location workLocation;

    public Rider() {
        super();
        this.tripHistory = new ArrayList<>();
    }

    public Rider(String name, String emailId, String phoneNumber) {
        super(name, emailId, phoneNumber);
        this.tripHistory = new ArrayList<>();
    }

    public List<Trip> getTripHistory() {
        return tripHistory;
    }

    public void setTripHistory(List<Trip> tripHistory) {
        this.tripHistory = tripHistory;
    }

    public Location getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;
    }

    public Location getWorkLocation() {
        return workLocation;
    }

    public void setWorkLocation(Location workLocation) {
        this.workLocation = workLocation;
    }

    public void addTrip(Trip trip) {
        this.tripHistory.add(trip);
    }

    @Override
    public String toString() {
        return "Rider{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", totalTrips=" + tripHistory.size() +
                '}';
    }
}
