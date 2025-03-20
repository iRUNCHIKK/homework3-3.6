package ru.hogwarts.school.model;

import java.util.Map;

public class CalculationResult {

    private Map<String, Long> executionTimes;

    private int sum;

    public CalculationResult(Map<String, Long> executionTimes, int sum) {
        this.executionTimes = executionTimes;
        this.sum = sum;
    }

    public Map<String, Long> getExecutionTimes() {
        return executionTimes;
    }

    public int getSum() {
        return sum;
    }
}
