package ch.dion.calculator.services;

import ch.dion.calculator.models.NumberAndOperator;

import java.util.List;

public interface ICalcService {
    private void add() {

    }

    private void subtract() {

    }

    private void multiply() {

    }

    private void divide() {

    }

    String calculate(String calculation);

    List<NumberAndOperator> getNumbersAndOperators(String calculation);


}
