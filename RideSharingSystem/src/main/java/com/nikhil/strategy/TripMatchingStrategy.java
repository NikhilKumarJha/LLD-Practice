package com.nikhil.strategy;

import com.nikhil.Driver;
import com.nikhil.Location;

import java.util.List;

public interface TripMatchingStrategy {
    /**
     * Find the best driver for a trip based on the strategy
     * @param availableDrivers List of available drivers
     * @param pickupLocation Pickup location for the trip
     * @return The best matched driver, or null if no suitable driver found
     */
    Driver findBestDriver(List<Driver> availableDrivers, Location pickupLocation);
}
