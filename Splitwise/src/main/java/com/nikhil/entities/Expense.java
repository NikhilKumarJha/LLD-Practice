package com.nikhil.entities;

import java.util.List;
import java.util.Map;

public class Expense {
    private String id;
    private User payer;
    private Double amount;
    private String description;
    private List<User> participants;
    private Map<User, Double> userVsAmount;

    public Expense() {
    }

    public Expense(String id, User payer, Double amount, String description, List<User> participants, Map<User, Double> userVsAmount) {
        this.id = id;
        this.payer = payer;
        this.amount = amount;
        this.description = description;
        this.participants = participants;
        this.userVsAmount = userVsAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public Map<User, Double> getUserVsAmount() {
        return userVsAmount;
    }

    public void setUserVsAmount(Map<User, Double> userVsAmount) {
        this.userVsAmount = userVsAmount;
    }

    public static class Builder {
        private Expense expense;

        public Builder(){
            expense = new Expense();
        }

        public Builder setId(String id){
            expense.setId(id);
            return this;
        }

        public Builder setPayer(User payer){
            expense.setPayer(payer);
            return this;
        }

        public Builder setAmount(Double amount){
            expense.setAmount(amount);
            return this;
        }

        public Builder setDescription(String description){
            expense.setDescription(description);
            return this;
        }

        public Builder setParticipants(List<User> participants){
            expense.setParticipants(participants);
            return this;
        }

        public Builder setUserVsAmount(Map<User, Double> userVsAmount){
            expense.setUserVsAmount(userVsAmount);
            return this;
        }

        public Expense build(){
            return expense;
        }
    }
}
