package ch.dion.calculator.services;

import ch.dion.calculator.enums.OperatorEnum;
import ch.dion.calculator.models.NumberAndOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CalcService implements ICalcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalcService.class);
    private static final Pattern NUMBER_OPERATOR_REGEX = Pattern.compile("(\\+|\\-|\\*|\\/)\\d+");

    @Override
    public String calculate(String calculation) {
        if (!calculation.startsWith("-")) {
            calculation = "+" + calculation;
        }

        List<NumberAndOperator> numbersAndOperators = getNumbersAndOperators(calculation);

        if (checkDivisionByZero(numbersAndOperators)) {
            return null;
        }

        // TODO: so lange rechnen, bis nicht mehr möglich
        for (NumberAndOperator no : numbersAndOperators) {
            if (no.getOperator().equals(OperatorEnum.MULTIPLY) || no.getOperator().equals(OperatorEnum.DIVIDE)) {
                prioritizedCalculation(numbersAndOperators);
                break;
            }
        }

        // TODO: addition und subtraktion

        // TODO: return real result
        return numbersAndOperators.get(0).getNumber().toString();
    }

    @Override
    public List<NumberAndOperator> getNumbersAndOperators(String calculation) {
        List<NumberAndOperator> numbersAndOperators = new ArrayList<>();
        Matcher matcher = NUMBER_OPERATOR_REGEX.matcher(calculation);

        while (matcher.find()) {
            numbersAndOperators.add(new NumberAndOperator(matcher.group()));
        }

        return numbersAndOperators;
    }

    private boolean checkDivisionByZero(List<NumberAndOperator> numbersAndOperators) {
        for (NumberAndOperator no : numbersAndOperators) {
            if (no.getNumber().equals(BigDecimal.ZERO) && no.getOperator().equals(OperatorEnum.DIVIDE)) {
                return true;
            }
        }
        return false;
    }

    private void prioritizedCalculation(List<NumberAndOperator> numbersAndOperators) {
        Map<Integer, NumberAndOperator> valuesToUpdate = new HashMap<>();

        for (int i = 1; i < numbersAndOperators.size(); i++) {
            switch (numbersAndOperators.get(i).getOperator()) {
                case MULTIPLY -> {
                    NumberAndOperator numberAndOperator = multiply(numbersAndOperators.get(i), numbersAndOperators.get(i - 1));
                    valuesToUpdate.put(i, numberAndOperator);
                }
                case DIVIDE -> {
                    NumberAndOperator numberAndOperator = divide(numbersAndOperators.get(i), numbersAndOperators.get(i - 1));
                    valuesToUpdate.put(i, numberAndOperator);
                }
            }
        }

        handleNewNumbers(numbersAndOperators, valuesToUpdate);
    }

    private NumberAndOperator multiply(NumberAndOperator lead, NumberAndOperator behind) {
        BigDecimal result = behind.getNumber().multiply(lead.getNumber());
        return new NumberAndOperator(handleNewOperator(result, behind));
    }

    private NumberAndOperator divide(NumberAndOperator lead, NumberAndOperator behind) {
        BigDecimal result = behind.getNumber().divide(lead.getNumber(), RoundingMode.HALF_UP);

        return new NumberAndOperator(handleNewOperator(result, behind));
    }

    private void handleNewNumbers(List<NumberAndOperator> numbersAndOperators, Map<Integer, NumberAndOperator> newValues) {
        for (Map.Entry<Integer, NumberAndOperator> entry : newValues.entrySet()) {
            // replace lead
            numbersAndOperators.set(entry.getKey(), entry.getValue());
            // remove behind
            numbersAndOperators.remove(entry.getKey() - 1);
        }
    }

    private String handleNewOperator(BigDecimal number, NumberAndOperator behind) {
        switch (behind.getOperator()) {
            case ADD -> {
                return "+" + number;
            }
            case SUBTRACT -> {
                return "-" + number;
            }
            case MULTIPLY -> {
                return "*" + number;
            }
            case DIVIDE -> {
                return "/" + number;
            }
        }
        return null;
    }
}