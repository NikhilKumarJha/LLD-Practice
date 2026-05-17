package com.nikhil;

public class Vehicle {
    private String number;
    private String make;
    private String model;
    private String color;
    private int capacity;
    private VehicleType type;

    public Vehicle() {
    }

    public Vehicle(String number, String make, String model, String color, int capacity, VehicleType type) {
        this.number = number;
        this.make = make;
        this.model = model;
        this.color = color;
        this.capacity = capacity;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
}
