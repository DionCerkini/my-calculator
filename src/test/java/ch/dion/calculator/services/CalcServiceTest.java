package ch.dion.calculator.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalcServiceTest {
    private CalcService calcService;
    @BeforeEach
    public void setUp() {
        calcService = new CalcService();
    }

    @Test
    public void testCalculateAddition() {
        String input = "2+3";
        String result = calcService.calculate(input);
        assertEquals("5", result);
    }

    @Test
    public void testCalculateSubtraction() {
        String input = "8-2";
        String result = calcService.calculate(input);
        assertEquals("6", result);
    }

    @Test
    public void testCalculateDivide() {
        String input = "8/4";
        String result = calcService.calculate(input);
        assertEquals("2", result);
    }

    @Test
    public void testCalculateMultiply() {
        String input = "8*8";
        String result = calcService.calculate(input);
        assertEquals("64", result);
    }

    @Test
    public void testPunktvorStrich() {
        String input = "6+8*8";
        String result = calcService.calculate(input);
        assertEquals("70", result);
    }




}