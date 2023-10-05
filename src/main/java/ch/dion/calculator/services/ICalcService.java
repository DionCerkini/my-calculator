package ch.dion.calculator.services;

import ch.dion.calculator.enums.OperatorEnum;

import java.util.ArrayList;

public interface ICalcService {
    public String add();
    public String subtract();
    public String multiply();
    public String divide();

    public void findOperator(String value);

    public String calculate();
}
