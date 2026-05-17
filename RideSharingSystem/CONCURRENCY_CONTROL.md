# Concurrency Control in Ride Sharing System

## Problem Statement

When a trip is moved to the **Assigned** state, we need to prevent race conditions that could occur when:
1. Multiple drivers try to accept the same trip simultaneously
2. Concurrent state transitions happen on the same trip
3. Driver availability is updated while trip assignment is in progress

## Solution: ReentrantLock

### Implementation

```java
public class Trip {
    private final ReentrantLock lock = new ReentrantLock();
    
    public void assignDriver() {
        lock.lock();
        try {
            state.assign(this);
        } finally {
            lock.unlock();
        }
    }
}
```

### Why ReentrantLock?

1. **Mutual Exclusion**: Only one thread can acquire the lock at a time
2. **Fairness**: Optional fair queuing of threads
3. **Explicit Control**: Fine-grained control over lock acquisition and release
4. **Interruptibility**: Threads can be interrupted while waiting
5. **Try-Lock**: Can attempt to acquire lock without blocking

### Alternatives Considered

#### 1. Synchronized Methods
```java
public synchronized void assignDriver() {
    state.assign(this);
}
```
**Pros**: Simpler syntax
**Cons**: Less flexible, no try-lock, can't interrupt

#### 2. Volatile + CAS (Compare-And-Swap)
```java
private volatile TripState state;

public boolean assignDriver(Driver driver) {
    if (!(state instanceof RequestedState)) {
        return false;
    }
    synchronized(this) {
        if (!(state instanceof RequestedState)) {
            return false;
        }
        this.state = new AssignedState();
        return true;
    }
}
```
**Pros**: Good for simple state changes
**Cons**: Complex for multiple field updates

#### 3. AtomicReference
```java
private AtomicReference<TripState> state;
```
**Pros**: Lock-free for single field
**Cons**: Only works for single reference, not multiple fields

#### 4. Database-Level Locking
```java
SELECT * FROM trips WHERE id = ? FOR UPDATE
```
**Pros**: Works across distributed systems
**Cons**: Requires database, slower, not suitable for in-memory operations

## How It Works

### Normal Flow (No Contention)

```
Thread 1: assignDriver()
  ├─ lock.lock()           ✓ Acquired
  ├─ state.assign(this)
  ├─ setState(Assigned)
  └─ lock.unlock()         ✓ Released
```

### Concurrent Access (With Contention)

```
Thread 1: assignDriver()          Thread 2: assignDriver()
  ├─ lock.lock() ✓ Acquired         ├─ lock.lock() ⏳ WAITING
  ├─ state.assign(this)             │
  ├─ setState(Assigned)             │  
  └─ lock.unlock() ✓ Released       │
                                    ├─ lock.lock() ✓ Acquired (after T1)
                                    ├─ state.assign(this)
                                    │  └─ "Driver already assigned"
                                    └─ lock.unlock() ✓ Released
```

## Key Benefits

### 1. Atomicity
All state transitions are atomic - either complete fully or not at all.

### 2. Consistency
The trip can never be in an inconsistent state (e.g., assigned to two drivers).

### 3. Isolation
Each thread sees a consistent view of the trip state.

### 4. Durability
Once a state change is committed (lock released), it's visible to all threads.

## Protected Operations

All public state transition methods are protected:

1. `assignDriver()` - RequestedState → AssignedState
2. `startTrip()` - AssignedState → InTransitState
3. `completeTrip()` - InTransitState → CompletedState
4. `cancelTrip()` - Any State → CancelledState

## Thread Safety Guarantees

1. **No Double Booking**: A trip can only be assigned to one driver
2. **No Lost Updates**: Concurrent updates don't overwrite each other
3. **No Dirty Reads**: Threads always see consistent state
4. **No Race Conditions**: State transitions are serialized

## Performance Considerations

### Lock Contention
- Each `Trip` has its own lock (fine-grained locking)
- Contention only occurs for operations on the same trip
- Different trips can be processed concurrently

### Lock Hold Time
- Lock is held only during state transition (microseconds)
- No I/O operations while holding lock
- Quick lock release prevents bottlenecks

## Testing Concurrency

### Test Scenario
```java
// Simulate 10 drivers trying to accept the same trip
ExecutorService executor = Executors.newFixedThreadPool(10);
Trip trip = new Trip(rider, driver1);

for (int i = 0; i < 10; i++) {
    executor.submit(() -> {
        trip.assignDriver();
    });
}

// Result: Only first thread succeeds, others see "Driver already assigned"
```

## Best Practices

1. ✅ **Always use try-finally**: Ensures lock is released even on exceptions
2. ✅ **Keep critical section small**: Only state transition logic inside lock
3. ✅ **Avoid nested locks**: Prevents deadlocks
4. ✅ **Use timeout for production**: `lock.tryLock(timeout, unit)`
5. ✅ **Document lock ordering**: If multiple locks needed, define order

## Future Enhancements

### Distributed Systems
For multi-server deployments, consider:

1. **Database Locks**
   ```sql
   SELECT * FROM trips WHERE id = ? FOR UPDATE
   ```

2. **Redis Distributed Locks**
   ```java
   Redisson lock = redisson.getLock("trip:" + tripId);
   lock.lock();
   try {
       // state transition
   } finally {
       lock.unlock();
   }
   ```

3. **Optimistic Locking**
   ```java
   // Add version field to Trip
   private long version;
   
   // Update with version check
   UPDATE trips SET state = ?, version = version + 1
   WHERE id = ? AND version = ?
   ```

## Summary

The `ReentrantLock` in the `Trip` class ensures that all state transitions are thread-safe, preventing race conditions during trip assignment and throughout the trip lifecycle. This provides strong consistency guarantees while maintaining good performance through fine-grained locking.
