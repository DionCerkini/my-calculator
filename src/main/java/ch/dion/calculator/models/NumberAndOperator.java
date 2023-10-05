package ch.dion.calculator.models;

import ch.dion.calculator.enums.OperatorEnum;

import java.math.BigDecimal;

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
        operator = OperatorEnum.valueOf(value.substring(0, 1));

        if (operator.equals(OperatorEnum.SUBTRACT)) {
            number =  new BigDecimal("-" + value.substring(1));
        } else {
            number = new BigDecimal(value.substring(1));
        }
    }
}
