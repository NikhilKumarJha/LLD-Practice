package com.nikhil.splitAlgos;

import com.nikhil.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PercentageSplit implements Split {

    @Override
    public Map<User, Double> getAmount(Double amount, List<User> participants, Map<String, Object> splitDetails) {
        if (splitDetails.get("percentage")==null || !(splitDetails.get("percentage") instanceof Map<?,?>)){
            throw new RuntimeException("Invalid percentage split details");
        }
        Map<User, Double> userVsPercentage = (Map<User, Double>) splitDetails.get("percentage");
        Map<User, Double> userVsAmount = new HashMap<>();
        for (User user : participants) {
            userVsAmount.put(user, amount * userVsPercentage.getOrDefault(user, 0.0) / 100.0);
        }
        return userVsAmount;

    }
}
