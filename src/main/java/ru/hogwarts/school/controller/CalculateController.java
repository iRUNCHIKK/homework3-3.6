package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("calculation")
public class CalculateController {

    private final Logger LOGGER = LoggerFactory.getLogger(CalculateController.class);

    @GetMapping
    public void calculate() {

        //option 1
        long startTime = System.currentTimeMillis();

        int sum = Stream.iterate(1, a -> a +1)
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
    }

}
