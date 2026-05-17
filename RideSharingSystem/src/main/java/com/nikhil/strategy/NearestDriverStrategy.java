package com.nikhil.strategy;

import com.nikhil.Driver;
import com.nikhil.Location;

import java.util.List;

public class NearestDriverStrategy implements TripMatchingStrategy {
    
    @Override
    public Driver findBestDriver(List<Driver> availableDrivers, Location pickupLocation) {
        if (availableDrivers == null || availableDrivers.isEmpty() || pickupLocation == null) {
            return null;
        }

        Driver nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver driver : availableDrivers) {
            if (driver.getCurrentLocation() == null) {
                continue;
            }

            double distance = driver.getCurrentLocation().distanceTo(pickupLocation);
            if (distance < minDistance) {
                minDistance = distance;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }
}
