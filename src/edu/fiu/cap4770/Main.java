package edu.fiu.cap4770;

import edu.fiu.cap4770.controllers.DecisionTreeController;
import edu.fiu.cap4770.models.DataTuple;
import edu.fiu.cap4770.models.Node;
import edu.fiu.cap4770.services.DecisionTreeService;
import edu.fiu.cap4770.services.DecisionTreeServiceInterface;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private static final String AGE_KEY = "age";
    private static final String INCOME_KEY = "income";
    private static final String STUDENT_KEY = "student";
    private static final String CREDIT_RATING_KEY = "credit_rating";
    private static final String CLASS_LABEL_KEY = "buys_computer";

    private static DecisionTreeController decisionTreeController;
    private static DecisionTreeServiceInterface decisionTreeService;
    private static Set<DataTuple> trainingTuples;
    private static Set<String> candidateAttributes;
    
    public static void main(String[] args) {
        setUp();

        Scanner reader = new Scanner(System.in);

        String age = getAttribute(reader, "Age", Age.valuesToString());
        String income = getAttribute(reader, "Income", Income.valuesToString());
        String student = getAttribute(reader,"Student", BoolProp.valuesToString());
        String creditRating = getAttribute(reader, "Credit Rating", CreditRating.valuesToString());

        reader.close();

        DataTuple tuple = createTuple(age, income, student, creditRating, BoolProp.NO.toString());
        Node rootNode = decisionTreeController.createDecisionTree(trainingTuples, candidateAttributes, CLASS_LABEL_KEY);

        System.out.println("\nBuys computer? " + rootNode.getClassLabel(tuple));
    }

    private static String getAttribute(Scanner reader, String attribute, String[] values) {
        String message = "";

        message += "Enter " + attribute + " (";

        for (int i = 0; i < values.length; i++) {
            message += values[i] + ", ";
        }

        message = message.substring(0, message.length() - 1);
        message += "): ";


        System.out.print(message);
        String input = reader.nextLine();


        return input;
    }

    private static void setUp() {
        decisionTreeService = new DecisionTreeService();

        decisionTreeController = new DecisionTreeController(decisionTreeService);

        trainingTuples = new HashSet();
        trainingTuples.add(createTuple(Age.YOUTH, Income.HIGH, BoolProp.NO, CreditRating.FAIR, BoolProp.NO));
        trainingTuples.add(createTuple(Age.YOUTH, Income.HIGH, BoolProp.NO, CreditRating.EXCELLENT, BoolProp.NO));
        trainingTuples.add(createTuple(Age.MIDDLE_AGED, Income.HIGH, BoolProp.NO, CreditRating.FAIR, BoolProp.YES));
        trainingTuples.add(createTuple(Age.SENIOR, Income.MEDIUM, BoolProp.NO, CreditRating.FAIR, BoolProp.YES));
        trainingTuples.add(createTuple(Age.SENIOR, Income.LOW, BoolProp.YES, CreditRating.FAIR, BoolProp.YES));
        trainingTuples.add(createTuple(Age.SENIOR, Income.LOW, BoolProp.YES, CreditRating.EXCELLENT, BoolProp.NO));
        trainingTuples.add(createTuple(Age.MIDDLE_AGED, Income.LOW, BoolProp.YES, CreditRating.EXCELLENT, BoolProp.YES));
        trainingTuples.add(createTuple(Age.YOUTH, Income.MEDIUM, BoolProp.NO, CreditRating.FAIR, BoolProp.NO));
        trainingTuples.add(createTuple(Age.YOUTH, Income.LOW, BoolProp.YES, CreditRating.FAIR, BoolProp.YES));
        trainingTuples.add(createTuple(Age.SENIOR, Income.MEDIUM, BoolProp.YES, CreditRating.FAIR, BoolProp.YES));
        trainingTuples.add(createTuple(Age.YOUTH, Income.MEDIUM, BoolProp.YES, CreditRating.EXCELLENT, BoolProp.YES));
        trainingTuples.add(createTuple(Age.MIDDLE_AGED, Income.MEDIUM, BoolProp.NO, CreditRating.EXCELLENT, BoolProp.YES));
        trainingTuples.add(createTuple(Age.MIDDLE_AGED, Income.HIGH, BoolProp.YES, CreditRating.FAIR, BoolProp.YES));
        trainingTuples.add(createTuple(Age.SENIOR, Income.MEDIUM, BoolProp.NO, CreditRating.EXCELLENT, BoolProp.NO));

        candidateAttributes = new HashSet();
        candidateAttributes.add(AGE_KEY);
        candidateAttributes.add(INCOME_KEY);
        candidateAttributes.add(STUDENT_KEY);
        candidateAttributes.add(CREDIT_RATING_KEY);
    }

    private enum Age {
        YOUTH("youth"),
        MIDDLE_AGED("middle_aged"),
        SENIOR("senior");

        final String property;

        Age(String property) {
            this.property = property;
        }

        @Override
        public String toString() {
            return property;
        }

        public static String[] valuesToString() {
            Age[] enumValues = values();
            String[] stringValues = new String[enumValues.length];

            for (int i = 0; i < stringValues.length; i++) {
                stringValues[i] = enumValues[i].toString();
            }

            return stringValues;
        }
    }

    private enum Income {
        LOW("low"),
        MEDIUM("medium"),
        HIGH("high");

        final String property;

        Income(String property) {
            this.property = property;
        }

        @Override
        public String toString() {
            return property;
        }

        public static String[] valuesToString() {
            Income[] enumValues = values();
            String[] stringValues = new String[enumValues.length];

            for (int i = 0; i < stringValues.length; i++) {
                stringValues[i] = enumValues[i].toString();
            }

            return stringValues;
        }
    }

    private enum BoolProp {
        YES("yes"),
        NO("no");

        final String property;

        BoolProp(String property) {
            this.property = property;
        }

        @Override
        public String toString() {
            return property;
        }

        public static String[] valuesToString() {
            BoolProp[] enumValues = values();
            String[] stringValues = new String[enumValues.length];

            for (int i = 0; i < stringValues.length; i++) {
                stringValues[i] = enumValues[i].toString();
            }

            return stringValues;
        }
    }

    private enum CreditRating {
        FAIR("fair"),
        EXCELLENT("excellent");

        final String property;

        CreditRating(String property) {
            this.property = property;
        }

        @Override
        public String toString() {
            return property;
        }

        public static String[] valuesToString() {
            CreditRating[] enumValues = values();
            String[] stringValues = new String[enumValues.length];

            for (int i = 0; i < stringValues.length; i++) {
                stringValues[i] = enumValues[i].toString();
            }

            return stringValues;
        }
    }

    private static DataTuple createTuple(Age age, Income income, BoolProp student, CreditRating creditRating, BoolProp buysComputer) {
        DataTuple tuple = new DataTuple();
        tuple.put(AGE_KEY, age.toString());
        tuple.put(INCOME_KEY, income.toString());
        tuple.put(STUDENT_KEY, student.toString());
        tuple.put(CREDIT_RATING_KEY, creditRating.toString());
        tuple.put(CLASS_LABEL_KEY, buysComputer.toString());

        return tuple;
    }

    private static DataTuple createTuple(String age, String income, String student, String creditRating, String buysComputer) {
        DataTuple tuple = new DataTuple();
        tuple.put(AGE_KEY, age);
        tuple.put(INCOME_KEY, income);
        tuple.put(STUDENT_KEY, student);
        tuple.put(CREDIT_RATING_KEY, creditRating);
        tuple.put(CLASS_LABEL_KEY, buysComputer);

        return tuple;
    }
}
