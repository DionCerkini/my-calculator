package ch.dion.calculator.controller;

import ch.dion.calculator.services.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalcController {
    private final CalcService calcService;

    @Autowired
    private CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @GetMapping("/calculate")
    public String calculateResult(@RequestParam String value) {
        return calcService.calculate(value);
    }
}
