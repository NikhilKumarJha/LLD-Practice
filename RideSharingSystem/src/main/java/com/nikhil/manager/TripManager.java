package com.nikhil.manager;

import com.nikhil.Trip;
import com.nikhil.Rider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TripManager {
    private final Map<String, Trip> trips;
    private static TripManager instance;

    private TripManager() {
        this.trips = new ConcurrentHashMap<>();
    }

    public static synchronized TripManager getInstance() {
        if (instance == null) {
            instance = new TripManager();
        }
        return instance;
    }

    /**
     * Add a new trip to the system
     */
    public void addTrip(Trip trip) {
        if (trip == null || trip.getId() == null) {
            throw new IllegalArgumentException("Trip or Trip ID cannot be null");
        }
        trips.put(trip.getId(), trip);
        System.out.println("Trip created: " + trip.getId());
    }

    /**
     * Get trip by ID
     */
    public Trip getTrip(String tripId) {
        return trips.get(tripId);
    }

    /**
     * Get all trips
     */
    public List<Trip> getAllTrips() {
        return new ArrayList<>(trips.values());
    }

    /**
     * Get trips for a specific rider
     */
    public List<Trip> getTripsByRider(Rider rider) {
        if (rider == null) {
            return new ArrayList<>();
        }
        return trips.values().stream()
                .filter(trip -> trip.getRider() != null && trip.getRider().getId().equals(rider.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Get trips for a specific driver
     */
    public List<Trip> getTripsByDriver(String driverId) {
        if (driverId == null) {
            return new ArrayList<>();
        }
        return trips.values().stream()
                .filter(trip -> trip.getDriver() != null && trip.getDriver().getId().equals(driverId))
                .collect(Collectors.toList());
    }

    /**
     * Remove a trip
     */
    public void removeTrip(String tripId) {
        Trip removed = trips.remove(tripId);
        if (removed != null) {
            System.out.println("Trip removed: " + tripId);
        }
    }

    /**
     * Get total number of trips
     */
    public int getTotalTrips() {
        return trips.size();
    }

    /**
     * Clear all trips (for testing purposes)
     */
    public void clearAllTrips() {
        trips.clear();
        System.out.println("All trips cleared");
    }
}
