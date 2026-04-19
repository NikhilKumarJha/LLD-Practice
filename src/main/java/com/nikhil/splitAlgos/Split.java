package com.nikhil.splitAlgos;

import com.nikhil.entities.User;

import java.util.List;
import java.util.Map;

public interface Split {
    Map<User, Double> getAmount(Double amount, List<User> participants, Map<String, Object> splitDetails);
}
