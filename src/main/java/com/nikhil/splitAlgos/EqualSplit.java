package com.nikhil.splitAlgos;

import com.nikhil.entities.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EqualSplit implements Split {
    @Override
    public Map<User, Double> getAmount(Double amount, List<User> participants, Map<String, Object> splitDetails) {
        Double amountPerPerson = amount / participants.size();
        Map<User, Double> userVsAmount = new HashMap<>();
        for(User user:participants){
            userVsAmount.put(user, amountPerPerson);
        }
        return userVsAmount;
    }
}
