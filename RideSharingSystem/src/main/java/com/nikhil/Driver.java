package com.nikhil;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User {
    private Vehicle vehicle;
    private DriverStatus status;
    private Location currentLocation;
    private boolean available;
    private String licenseNumber;
    private int totalTripsCompleted;
    private double earnings;
    private List<Trip> tripHistory;

    public Driver() {
        super();
        this.status = DriverStatus.IDLE;
        this.available = true;
        this.totalTripsCompleted = 0;
        this.earnings = 0.0;
        this.tripHistory = new ArrayList<>();
    }

    public Driver(String name, String emailId, String phoneNumber, String licenseNumber, Vehicle vehicle) {
        super(name, emailId, phoneNumber);
        this.licenseNumber = licenseNumber;
        this.vehicle = vehicle;
        this.status = DriverStatus.IDLE;
        this.available = true;
        this.totalTripsCompleted = 0;
        this.earnings = 0.0;
        this.tripHistory = new ArrayList<>();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getTotalTripsCompleted() {
        return totalTripsCompleted;
    }

    public void setTotalTripsCompleted(int totalTripsCompleted) {
        this.totalTripsCompleted = totalTripsCompleted;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public List<Trip> getTripHistory() {
        return tripHistory;
    }

    public void setTripHistory(List<Trip> tripHistory) {
        this.tripHistory = tripHistory;
    }

    public void addTrip(Trip trip) {
        this.tripHistory.add(trip);
        this.totalTripsCompleted++;
    }

    public void addEarnings(double amount) {
        this.earnings += amount;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", status=" + status +
                ", available=" + available +
                ", totalTripsCompleted=" + totalTripsCompleted +
                ", earnings=" + earnings +
                '}';
    }
}
