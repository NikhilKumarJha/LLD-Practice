package com.nikhil;

import com.sun.net.httpserver.Request;

import java.util.HashSet;
import java.util.Set;

public class Elevator {
    private int currentFloor = 0;
    private ElevatorDirection direction = ElevatorDirection.IDLE;
    private final Set<ElevatorRequest> requests = new HashSet<>();

    void step() {
        if (requests.isEmpty()) {
            direction = ElevatorDirection.IDLE;
            return;
        }
        if (direction == ElevatorDirection.IDLE) {
            int distance = Integer.MAX_VALUE;
            ElevatorRequest nearest = null;
            for (ElevatorRequest request : requests) {
                if (Math.abs(request.getFloor() - currentFloor) < distance) {
                    distance = Math.abs(request.getFloor() - currentFloor);
                    direction = request.getFloor() > currentFloor ? ElevatorDirection.UP : ElevatorDirection.DOWN;
                    nearest = request;
                }
            }
            if (nearest == null) {
                nearest = requests.iterator().next();
            }
            direction = nearest.getFloor() > currentFloor ? ElevatorDirection.UP : ElevatorDirection.DOWN;
        }
        RequestDirection requestDirection = direction == ElevatorDirection.UP ? RequestDirection.PICKUP_UP : RequestDirection.PICKUP_DOWN;
        ElevatorRequest pickupRequest = new ElevatorRequest(currentFloor, requestDirection);
        ElevatorRequest destinationRequest = new ElevatorRequest(currentFloor, RequestDirection.DESTINATION);
        if (requests.contains(pickupRequest)) {
            requests.remove(pickupRequest);
        }
        if (requests.contains(destinationRequest)) {
            requests.remove(destinationRequest);
        }
        if (requests.isEmpty()) {
            direction = ElevatorDirection.IDLE;
            return;
        }
        if (!requestAhead(direction)){
            direction = direction == ElevatorDirection.UP ? ElevatorDirection.DOWN : ElevatorDirection.UP;
            return;
        }
        if (direction == ElevatorDirection.UP) {
            currentFloor++;
        } else {
            currentFloor--;
        }
    }

    private boolean requestAhead(ElevatorDirection direction) {
        for (ElevatorRequest request : requests) {
            if (direction == ElevatorDirection.UP && request.getFloor() > currentFloor) {
                return true;
            }
            if (direction == ElevatorDirection.DOWN && request.getFloor() < currentFloor) {
                return true;
            }
        }
        return false;
    }

    public boolean addRequest(ElevatorRequest request) {
        if (request.getFloor() < 0 || request.getFloor() > 9) {
            return false;
        }
        if (RequestDirection.DESTINATION.equals(request.getRequestDirection())) {
            return false;
        }
        return requests.add(request);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    public Set<ElevatorRequest> getRequests() {
        return requests;
    }

    public boolean hasRequestBeyond(int floor, ElevatorDirection direction) {
        for (ElevatorRequest request : requests) {
            if (request.getFloor() > floor && ElevatorDirection.UP.equals(direction)) {
                return true;
            }
            if (request.getFloor() < floor && ElevatorDirection.DOWN.equals(direction)) {
                return true;
            }
        }
        return false;
    }
}
