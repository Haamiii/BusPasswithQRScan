package com.example.buspasswithqrscan.Conductor.model;

public class JourneyStopsChecker {
    private int remainingStops;
    private int totalStops;

    public int getRemainingStops() {
        return remainingStops;
    }

    public void setRemainingStops(int remainingStops) {
        this.remainingStops = remainingStops;
    }

    public int getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(int totalStops) {
        this.totalStops = totalStops;
    }

    public JourneyStopsChecker(int remainingStops, int totalStops) {
        this.remainingStops = remainingStops;
        this.totalStops = totalStops;
    }
}
