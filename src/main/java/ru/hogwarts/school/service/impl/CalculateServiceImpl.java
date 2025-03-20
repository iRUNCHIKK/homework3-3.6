package ru.hogwarts.school.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.CalculationResult;
import ru.hogwarts.school.service.CalculateService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CalculateServiceImpl implements CalculateService {

    private final Logger LOGGER = LoggerFactory.getLogger(CalculateServiceImpl.class);

    @Override
    public CalculationResult performCalculations() {

        Map<String, Long> executionTimes = new HashMap<>();

        int sum = 0;

        //option 1
        long startTime = System.currentTimeMillis();

        sum = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);

        long endTime = System.currentTimeMillis();

        LOGGER.info("Option 1: {} ms", endTime - startTime);

        //option 2
        startTime = System.currentTimeMillis();

        sum = 0;
        for (int i = 0; i <= 1_000_000 ; i++) {
            sum += 1;
        }

        endTime = System.currentTimeMillis();

        LOGGER.info("Option 2: {} ms", endTime - startTime);

        //option 3
        startTime = System.currentTimeMillis();

        sum = (1 + 1_000_000) * 5000_000;

        endTime = System.currentTimeMillis();

        LOGGER.info("Option 3: {} ms", endTime - startTime);

        return new CalculationResult(executionTimes, sum);
    }
}
