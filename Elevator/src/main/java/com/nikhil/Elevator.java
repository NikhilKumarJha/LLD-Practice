package com.nikhil;

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
                if (Math.abs(request.getFloor() - currentFloor) < distance){
                    distance = Math.abs(request.getFloor() - currentFloor);
                    direction = request.getFloor() > currentFloor ? ElevatorDirection.UP : ElevatorDirection.DOWN;
                    nearest = request;
                }
            }
            if (nearest ==null){
                nearest = requests.iterator().next();
            }
            direction = nearest.getFloor() > currentFloor ? ElevatorDirection.UP : ElevatorDirection.DOWN;
        }
        if (direction == ElevatorDirection.UP) {
            currentFloor++;
        } else {
            currentFloor--;
        }
        requests.removeIf(request -> request.getFloor() == currentFloor);
        if (requests.isEmpty()) {
            direction = ElevatorDirection.IDLE;
        }
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
