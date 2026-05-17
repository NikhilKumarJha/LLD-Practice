# Ride Sharing System

A thread-safe, production-ready ride-sharing system implementation in Java demonstrating key design patterns and concurrency control.

## Features

- **Thread-Safe Trip Assignment**: Uses `ReentrantLock` to prevent race conditions when multiple drivers try to accept the same trip
- **State Pattern**: Trip lifecycle managed through state transitions (Requested → Assigned → InTransit → Completed/Cancelled)
- **Strategy Pattern**: Pluggable driver matching algorithms (currently uses NearestDriverStrategy)
- **Singleton Pattern**: Centralized service managers
- **Concurrency Control**: Prevents double-booking and ensures atomic state transitions

## Architecture

### Core Components

1. **Domain Models**
   - `User` (abstract): Base class for Rider and Driver
   - `Rider`: Represents a passenger
   - `Driver`: Represents a driver with vehicle and location
   - `Trip`: Core entity with thread-safe state management
   - `Vehicle`: Driver's vehicle information
   - `Location`: GPS coordinates with distance calculation

2. **State Pattern (Trip Lifecycle)**
   - `TripState` (interface)
   - `RequestedState`: Initial state when trip is created
   - `AssignedState`: Driver assigned, waiting to start
   - `InTransitState`: Trip in progress
   - `CompletedState`: Trip finished successfully
   - `CancelledState`: Trip cancelled

3. **Strategy Pattern (Driver Matching)**
   - `TripMatchingStrategy` (interface)
   - `NearestDriverStrategy`: Finds closest available driver

4. **Service Layer**
   - `RideSharingService`: Main orchestrator
   - `DriverManager`: Manages driver registration and availability
   - `TripManager`: Manages trip lifecycle and history

## Concurrency Control

### Problem
When a trip transitions to "Assigned" state, multiple drivers could simultaneously accept it, causing:
- Race conditions on state transitions
- Double-booking of trips
- Inconsistent state

### Solution
```java
private final ReentrantLock lock = new ReentrantLock();

public void assignDriver() {
    lock.lock();
    try {
        state.assign(this);
    } finally {
        lock.unlock();
    }
}
```

**How it works:**
1. Each `Trip` has its own `ReentrantLock`
2. All state transition methods acquire the lock before executing
3. Only one thread can transition the state at a time
4. The `finally` block ensures lock is always released

## Design Patterns Used

1. **State Pattern**: Trip lifecycle management
2. **Strategy Pattern**: Driver matching algorithms
3. **Singleton Pattern**: Service managers (DriverManager, TripManager, RideSharingService)
4. **Factory Pattern**: Can be extended for creating different trip types

## Running the Demo

```bash
# Compile
cd RideSharingSystem
javac -d out src/main/java/com/nikhil/*.java src/main/java/com/nikhil/state/*.java src/main/java/com/nikhil/strategy/*.java src/main/java/com/nikhil/manager/*.java src/main/java/com/nikhil/service/*.java

# Run
java -cp out com.nikhil.Main
```

## Example Output

```
=== Ride Sharing System Demo ===

Driver registered: John Doe (ID: xxx)
Driver registered: Jane Smith (ID: xxx)
Driver registered: Bob Johnson (ID: xxx)

--- New Trip Request ---
Rider: Alice Brown
Pickup: Location{lat=12.9716, lng=77.5946}
Drop: Location{lat=12.9352, lng=77.6245}
Available drivers: 3
Trip assigned to driver: John Doe
Distance to pickup: 0.00 km
Trip started: xxx
Trip completed: xxx | Fare: $250.5
```

## Extension Points

1. **Add new matching strategies**: Implement `TripMatchingStrategy`
   - HighestRatedDriverStrategy
   - CheapestFareStrategy
   - ShortestETAStrategy

2. **Add pricing logic**: Create `FareCalculator` with surge pricing, distance-based pricing

3. **Add notifications**: Observer pattern for trip updates

4. **Add payment processing**: Payment gateway integration

5. **Add trip types**: Pool, Premium, Standard

## Key Classes

- `Trip.java`: Core entity with ReentrantLock for thread safety
- `RideSharingService.java`: Main service orchestrating all operations
- `DriverManager.java`: Manages driver pool with concurrent access
- `TripManager.java`: Manages all trips in the system
- `NearestDriverStrategy.java`: Driver matching based on proximity

## Thread Safety Considerations

- All state transitions in `Trip` are protected by `ReentrantLock`
- `DriverManager` and `TripManager` use `ConcurrentHashMap` for thread-safe collections
- Driver availability updates are synchronized to prevent race conditions
- Trip assignment is atomic - either succeeds completely or fails

## Future Enhancements

- Add database persistence layer
- Implement real-time location tracking
- Add WebSocket support for live updates
- Implement optimistic locking with version numbers for distributed systems
- Add caching layer for frequently accessed data
- Implement circuit breaker pattern for external service calls
