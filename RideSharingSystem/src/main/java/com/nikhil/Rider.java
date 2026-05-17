package com.nikhil;

import java.util.List;

public class Rider extends User {
    private List<Trip> tripHistory;
    private Location homeLocation;
    private Location workLocation;
    private List<PaymentMethod> paymentMethods;
}
