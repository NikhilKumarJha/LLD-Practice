package com.nikhil;

import com.nikhil.service.RideSharingService;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Ride Sharing System Demo ===\n");

        // Initialize service
        RideSharingService service = RideSharingService.getInstance();

        // Create vehicles
        Vehicle sedan1 = new Vehicle("KA01AB1234", "Toyota", "Camry", "White", 4, VehicleType.SEDAN);
        Vehicle sedan2 = new Vehicle("KA01CD5678", "Honda", "Accord", "Black", 4, VehicleType.SEDAN);
        Vehicle suv1 = new Vehicle("KA01EF9012", "Ford", "Explorer", "Blue", 6, VehicleType.SUV);

        // Create drivers
        Driver driver1 = new Driver("John Doe", "john@example.com", "9876543210", "DL123456", sedan1);
        driver1.setCurrentLocation(new Location(12.9716, 77.5946)); // Bangalore coordinates

        Driver driver2 = new Driver("Jane Smith", "jane@example.com", "9876543211", "DL123457", sedan2);
        driver2.setCurrentLocation(new Location(12.9352, 77.6245));

        Driver driver3 = new Driver("Bob Johnson", "bob@example.com", "9876543212", "DL123458", suv1);
        driver3.setCurrentLocation(new Location(12.9082, 77.6476));

        // Register drivers
        service.registerDriver(driver1);
        service.registerDriver(driver2);
        service.registerDriver(driver3);

        service.printDriverStats();

        // Create riders
        Rider rider1 = new Rider("Alice Brown", "alice@example.com", "9876543213");
        Rider rider2 = new Rider("Charlie Wilson", "charlie@example.com", "9876543214");

        // Rider 1 requests a trip
        Location pickup1 = new Location(12.9716, 77.5946);
        Location drop1 = new Location(12.9352, 77.6245);
        Trip trip1 = service.requestTrip(rider1, pickup1, drop1);

        if (trip1 != null) {
            // Start the trip
            service.startTrip(trip1.getId());

            // Simulate trip completion
            service.completeTrip(trip1.getId(), 250.50);
        }

        // Rider 2 requests a trip
        Location pickup2 = new Location(12.9082, 77.6476);
        Location drop2 = new Location(12.9716, 77.5946);
        Trip trip2 = service.requestTrip(rider2, pickup2, drop2);

        if (trip2 != null) {
            // Start the trip
            service.startTrip(trip2.getId());

            // Cancel the trip
            service.cancelTrip(trip2.getId());
        }

        service.printDriverStats();

        // Print driver details
        System.out.println("\n--- Driver Details ---");
        System.out.println(driver1);
        System.out.println(driver2);
        System.out.println(driver3);

        System.out.println("\n=== Demo Complete ===");
    }
}
