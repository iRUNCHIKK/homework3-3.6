package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.CalculationResult;
import ru.hogwarts.school.service.impl.CalculateServiceImpl;

import java.util.stream.Stream;

@RestController
@RequestMapping("calculation")
public class CalculateController {

    private final Logger LOGGER = LoggerFactory.getLogger(CalculateController.class);

    private CalculateServiceImpl calculateServiceImpl;

    @Autowired
    public CalculateController(CalculateServiceImpl calculateServiceImpl) {
        this.calculateServiceImpl = calculateServiceImpl;
    }

    @GetMapping
    public CalculationResult calculate() {
        return calculateServiceImpl.performCalculations();
    }
}
