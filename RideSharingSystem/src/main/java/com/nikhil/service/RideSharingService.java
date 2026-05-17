package com.nikhil.service;

import com.nikhil.*;
import com.nikhil.manager.DriverManager;
import com.nikhil.manager.TripManager;
import com.nikhil.strategy.NearestDriverStrategy;
import com.nikhil.strategy.TripMatchingStrategy;

import java.util.List;

public class RideSharingService {
    private final DriverManager driverManager;
    private final TripManager tripManager;
    private final TripMatchingStrategy matchingStrategy;
    private static RideSharingService instance;

    private RideSharingService() {
        this.driverManager = DriverManager.getInstance();
        this.tripManager = TripManager.getInstance();
        this.matchingStrategy = new NearestDriverStrategy();
    }

    public static synchronized RideSharingService getInstance() {
        if (instance == null) {
            instance = new RideSharingService();
        }
        return instance;
    }

    /**
     * Register a new driver
     */
    public void registerDriver(Driver driver) {
        driverManager.registerDriver(driver);
    }

    /**
     * Request a trip
     */
    public Trip requestTrip(Rider rider, Location pickupLocation, Location dropLocation) {
        if (rider == null || pickupLocation == null || dropLocation == null) {
            throw new IllegalArgumentException("Rider, pickup location, and drop location cannot be null");
        }

        System.out.println("\n--- New Trip Request ---");
        System.out.println("Rider: " + rider.getName());
        System.out.println("Pickup: " + pickupLocation);
        System.out.println("Drop: " + dropLocation);

        // Get available drivers
        List<Driver> availableDrivers = driverManager.getAvailableDrivers();
        
        if (availableDrivers.isEmpty()) {
            System.out.println("No drivers available at the moment");
            return null;
        }

        System.out.println("Available drivers: " + availableDrivers.size());

        // Find best driver using strategy
        Driver matchedDriver = matchingStrategy.findBestDriver(availableDrivers, pickupLocation);

        if (matchedDriver == null) {
            System.out.println("No suitable driver found");
            return null;
        }

        // Create trip (driver is set but trip starts in RequestedState)
        Trip trip = new Trip(rider, matchedDriver);
        
        // Assign driver (this will transition to AssignedState)
        trip.assignDriver();
        
        // Mark driver as busy
        driverManager.markDriverBusy(matchedDriver.getId());

        // Add trip to manager
        tripManager.addTrip(trip);
        
        // Add to rider and driver history
        rider.addTrip(trip);
        matchedDriver.addTrip(trip);

        System.out.println("Trip assigned to driver: " + matchedDriver.getName());
        System.out.println("Distance to pickup: " + 
            String.format("%.2f", matchedDriver.getCurrentLocation().distanceTo(pickupLocation)) + " km");
        
        return trip;
    }

    /**
     * Start a trip
     */
    public void startTrip(String tripId) {
        Trip trip = tripManager.getTrip(tripId);
        if (trip != null) {
            trip.startTrip();
            System.out.println("Trip started: " + tripId);
        }
    }

    /**
     * Complete a trip
     */
    public void completeTrip(String tripId, double fare) {
        Trip trip = tripManager.getTrip(tripId);
        if (trip != null) {
            trip.completeTrip();
            
            // Update driver
            Driver driver = trip.getDriver();
            if (driver != null) {
                driver.addEarnings(fare);
                driverManager.markDriverAvailable(driver.getId());
            }
            
            System.out.println("Trip completed: " + tripId + " | Fare: $" + fare);
        }
    }

    /**
     * Cancel a trip
     */
    public void cancelTrip(String tripId) {
        Trip trip = tripManager.getTrip(tripId);
        if (trip != null) {
            trip.cancelTrip();
            
            // Make driver available again
            Driver driver = trip.getDriver();
            if (driver != null) {
                driverManager.markDriverAvailable(driver.getId());
            }
            
            System.out.println("Trip cancelled: " + tripId);
        }
    }

    /**
     * Update driver location
     */
    public void updateDriverLocation(String driverId, Location newLocation) {
        driverManager.updateDriverLocation(driverId, newLocation);
    }

    /**
     * Get driver statistics
     */
    public void printDriverStats() {
        System.out.println("\n--- Driver Statistics ---");
        System.out.println("Total Drivers: " + driverManager.getTotalDrivers());
        System.out.println("Available Drivers: " + driverManager.getAvailableDriversCount());
    }
}
