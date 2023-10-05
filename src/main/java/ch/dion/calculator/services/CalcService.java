package ch.dion.calculator.services;

import ch.dion.calculator.enums.OperatorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class CalcService implements ICalcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalcService.class);
    private final ArrayList<BigDecimal> numberList = new ArrayList<>();
    private final ArrayList<OperatorEnum> operatorList = new ArrayList<>();


    @Override
    public String add() {
        return null;
    }

    @Override
    public String subtract() {
        return null;
    }

    @Override
    public String multiply() {
        return null;
    }

    @Override
    public String divide() {
        return null;
    }

    @Override
    public void findOperator(String value) {
        // Alle Operatorzeichen in das entsprechende Array speichern
        for (char c : value.toCharArray()) {
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                operatorList.add(OperatorEnum.valueOf(String.valueOf(c)));
            }
        }
    }

    @Override
    public String calculate() {
        return null;
    }

    @Override
    public void findNumbers(String value) {

        // Aufteilungen in separate Arrays speichern (Zahlen und Operationszeichen)
        Pattern pattern = Pattern.compile("[()+\\-*/]");
        String[] splitOperators = pattern.split(value);

        for (String s : splitOperators) {
            BigDecimal numberValue = new BigDecimal(s);
            numberList.add(numberValue);
        }
    }

    @Override
    public String calculate(String value) {
        findOperator(value);
        findNumbers(value);

        if (operatorList.contains(OperatorEnum.MULTIPLY) || operatorList.contains(OperatorEnum.DIVIDE))
            prioritizeOperators();

        for (OperatorEnum o : operatorList) {
            switch (o) {
                case ADD -> add();
                case SUBTRACT -> subtract();
            }
        }
    }

    // * and /
    public void prioritizeOperators() {
        int initialLength = operatorList.size();
        ArrayList<Integer> operatorsToRemove = new ArrayList<>();
        for (int i = 0; i < initialLength; i++) {
            switch (operatorList.get(i)) {
                case MULTIPLY -> {
                    multiply();
                    operatorsToRemove.add(i);
                }
                case DIVIDE -> {
                    divide();
                    operatorsToRemove.add(i);
                }
            }
        }

        removeOperator(operatorsToRemove);
    }

    private void removeOperator(List<Integer> operators) {
        for (Integer i : operators) {
            operatorList.remove(i.intValue());
        }
    }
}