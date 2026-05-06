package com.nikhil.splitAlgos;

public class SplitFactory {
    public static Split createSplit(String splitType) throws IllegalArgumentException {
        switch (splitType) {
            case "equal": {
                return new EqualSplit();
            }
            case "percentage": {
                return new PercentageSplit();
            }
            default:
                throw new IllegalArgumentException("Invalid split type");
        }

    }
}
