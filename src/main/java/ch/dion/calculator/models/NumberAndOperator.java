package ch.dion.calculator.models;

import ch.dion.calculator.enums.OperatorEnum;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class NumberAndOperator {
    private BigDecimal number;
    private OperatorEnum operator;

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public OperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(OperatorEnum operator) {
        this.operator = operator;
    }

    public NumberAndOperator(String value) {

        operator = convertOperatorToEnum(value.substring(0, 1));

        if (operator.equals(OperatorEnum.SUBTRACT)) {
            number = new BigDecimal("-" + value.substring(1));
        } else {
            number = new BigDecimal(value.substring(1));
        }
    }

    private OperatorEnum convertOperatorToEnum(String operator) {
        switch (operator) {
            case "+" -> {
                return OperatorEnum.ADD;
            }
            case "-" -> {
                return OperatorEnum.SUBTRACT;
            }
            case "*" -> {
                return OperatorEnum.MULTIPLY;
            }
            case "/" -> {
                return OperatorEnum.DIVIDE;
            }
        }
        return null;
    }
}
