package com.nikhil.manager;

import com.nikhil.Driver;
import com.nikhil.DriverStatus;
import com.nikhil.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DriverManager {
    private final Map<String, Driver> drivers;
    private static DriverManager instance;

    private DriverManager() {
        this.drivers = new ConcurrentHashMap<>();
    }

    public static synchronized DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    /**
     * Register a new driver in the system
     */
    public void registerDriver(Driver driver) {
        if (driver == null || driver.getId() == null) {
            throw new IllegalArgumentException("Driver or Driver ID cannot be null");
        }
        drivers.put(driver.getId(), driver);
        System.out.println("Driver registered: " + driver.getName() + " (ID: " + driver.getId() + ")");
    }

    /**
     * Get driver by ID
     */
    public Driver getDriver(String driverId) {
        return drivers.get(driverId);
    }

    /**
     * Get all registered drivers
     */
    public List<Driver> getAllDrivers() {
        return new ArrayList<>(drivers.values());
    }

    /**
     * Get all available drivers (idle and available)
     */
    public List<Driver> getAvailableDrivers() {
        return drivers.values().stream()
                .filter(driver -> driver.isAvailable() && driver.getStatus() == DriverStatus.IDLE)
                .collect(Collectors.toList());
    }

    /**
     * Update driver location
     */
    public void updateDriverLocation(String driverId, Location newLocation) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setCurrentLocation(newLocation);
            System.out.println("Updated location for driver: " + driver.getName());
        }
    }

    /**
     * Update driver availability
     */
    public void updateDriverAvailability(String driverId, boolean available) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setAvailable(available);
            if (!available) {
                driver.setStatus(DriverStatus.RIDING);
            } else {
                driver.setStatus(DriverStatus.IDLE);
            }
            System.out.println("Updated availability for driver: " + driver.getName() + " -> " + available);
        }
    }

    /**
     * Mark driver as busy (on a trip)
     */
    public void markDriverBusy(String driverId) {
        updateDriverAvailability(driverId, false);
    }

    /**
     * Mark driver as available (trip completed)
     */
    public void markDriverAvailable(String driverId) {
        updateDriverAvailability(driverId, true);
    }

    /**
     * Remove a driver from the system
     */
    public void removeDriver(String driverId) {
        Driver removed = drivers.remove(driverId);
        if (removed != null) {
            System.out.println("Driver removed: " + removed.getName());
        }
    }

    /**
     * Get total number of drivers
     */
    public int getTotalDrivers() {
        return drivers.size();
    }

    /**
     * Get number of available drivers
     */
    public int getAvailableDriversCount() {
        return getAvailableDrivers().size();
    }
}
