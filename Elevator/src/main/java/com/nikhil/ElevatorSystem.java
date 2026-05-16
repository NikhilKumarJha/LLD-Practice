package com.nikhil;

import java.util.ArrayList;
import java.util.List;

import static com.nikhil.RequestDirection.DESTINATION;
import static com.nikhil.RequestDirection.PICKUP_UP;

public class ElevatorSystem {
    private final List<Elevator> elevators = new ArrayList<>();

    private static ElevatorSystem instance;

    ElevatorSystem() {
        elevators.add(new Elevator());
        elevators.add((new Elevator()));
        elevators.add(new Elevator());
    }

    public static ElevatorSystem getInstance() {
        if (instance == null) {
            synchronized (ElevatorSystem.class) {
                if (instance == null) {
                    instance = new ElevatorSystem();
                }
            }
        }
        return instance;
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public boolean requestElevator(int floor, RequestDirection requestDirection) {
        if (floor < 0 || floor > 9) {
            return false;
        }
        if (DESTINATION.equals(requestDirection)) {
            return false;
        }
        ElevatorRequest request = new ElevatorRequest(floor, requestDirection);
        Elevator elevator = getBestElevator(request);
        if (elevator == null) {
            return false;
        }
        return elevator.addRequest(request);
    }

    private Elevator getBestElevator(ElevatorRequest request) {
        Elevator best = findNearestInSameDirection(request);
        if (best == null) {
            best = findNearestIdle(request);
        }
        if (best == null) {
            best = findAnyNearest(request);
        }
        return best;
    }

    private Elevator findAnyNearest(ElevatorRequest request) {
        int distance = Integer.MAX_VALUE;
        Elevator best = null;
        for (Elevator elevator : elevators) {
            if (distance > Math.abs(elevator.getCurrentFloor() - request.getFloor())) {
                distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
                best = elevator;
            }
        }
        return best;
    }

    private Elevator findNearestIdle(ElevatorRequest request) {
        int distance = Integer.MAX_VALUE;
        Elevator best = null;
        for (Elevator elevator : elevators) {
            if (elevator.getDirection().equals(ElevatorDirection.IDLE) && distance > Math.abs(elevator.getCurrentFloor() - request.getFloor())) {
                distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
                best = elevator;
            }
        }
        return best;
    }

    private Elevator findNearestInSameDirection(ElevatorRequest request) {
        RequestDirection requestedDirection = request.getRequestDirection();
        int requestedFloor = request.getFloor();
        ElevatorDirection targetElevatorDirection = PICKUP_UP.equals(requestedDirection) ? ElevatorDirection.UP : ElevatorDirection.DOWN;
        int distance = Integer.MAX_VALUE;
        Elevator best = null;
        for (Elevator elevator : elevators) {
            if (!elevator.getDirection().equals(targetElevatorDirection)) {
                continue;
            }
            if (elevator.getCurrentFloor() > requestedFloor && ElevatorDirection.UP.equals(elevator.getDirection())) {
                continue;
            }
            if (elevator.getCurrentFloor() < requestedFloor && ElevatorDirection.DOWN.equals(elevator.getDirection())) {
                continue;
            }
            if (elevator.hasRequestBeyond(requestedFloor, targetElevatorDirection)) {
                continue;
            }
            if (distance > Math.abs(elevator.getCurrentFloor() - requestedFloor)) {
                distance = Math.abs(elevator.getCurrentFloor() - requestedFloor);
                best = elevator;
            }
        }
        return best;
    }
}