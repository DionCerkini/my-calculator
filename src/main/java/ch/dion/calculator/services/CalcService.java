package ch.dion.calculator.services;

import ch.dion.calculator.enums.OperatorEnum;
import ch.dion.calculator.models.NumberAndOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

        for (NumberAndOperator no : numbersAndOperators) {
            if (no.getOperator().equals(OperatorEnum.MULTIPLY) || no.getOperator().equals(OperatorEnum.DIVIDE)) {
                prioritizedCalculation(numbersAndOperators);
                break;
            }
        }

        return calculation;
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

    private void prioritizedCalculation(List<NumberAndOperator> numberAndOperators) {
        Map<Integer, NumberAndOperator> valuesToUpdate = new HashMap<>();


        for (int i = 1; i < numberAndOperators.size(); i++) {
            switch (numberAndOperators.get(i).getOperator()) {
                case MULTIPLY -> {
                    NumberAndOperator numberAndOperator = multiply(numberAndOperators.get(i), numberAndOperators.get(i - 1));
                    valuesToUpdate.put(i, numberAndOperator);
                }
                case DIVIDE -> divide();
            }
        }
    }

    private NumberAndOperator multiply(NumberAndOperator lead, NumberAndOperator behind) {
        BigDecimal result = behind.getNumber().multiply(lead.getNumber());

        return new NumberAndOperator(result.toString());
    }

    private NumberAndOperator divide(NumberAndOperator lead, NumberAndOperator behind) {

        BigDecimal result = behind.getNumber().multiply(lead.getNumber());

        return new NumberAndOperator(result.toString());
    }
}