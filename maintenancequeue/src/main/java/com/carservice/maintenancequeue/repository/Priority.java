package com.carservice.maintenancequeue.repository;

public enum Priority {
    HIGH(30),
    NORMAL(120),
    LOW(Long.MAX_VALUE);

    private long maxEstimation;

    Priority(long maxEstimation) {
        this.maxEstimation = maxEstimation;
    }

    public static Priority getByEstimatedTime(Long estimation) {
        return estimation <= HIGH.maxEstimation
                       ? HIGH
                       : estimation <= NORMAL.maxEstimation
                                 ? NORMAL
                                 : LOW;
    }
}
