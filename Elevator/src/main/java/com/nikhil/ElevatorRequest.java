package com.nikhil;

import java.util.Objects;

public class ElevatorRequest {
    private int floor;
    private RequestDirection requestDirection;

    public ElevatorRequest(int floor, RequestDirection requestDirection) {
        this.floor = floor;
        this.requestDirection = requestDirection;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public RequestDirection getRequestDirection() {
        return requestDirection;
    }

    public void setRequestDirection(RequestDirection requestDirection) {
        this.requestDirection = requestDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ElevatorRequest that = (ElevatorRequest) o;
        return floor == that.floor && requestDirection == that.requestDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, requestDirection);
    }
}
