package edu.fiu.cap4770.tests.unit;

import edu.fiu.cap4770.models.CandidateAttribute;
import edu.fiu.cap4770.models.DataTuple;
import edu.fiu.cap4770.services.DecisionTreeService;
import edu.fiu.cap4770.services.DecisionTreeServiceInterface;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DecisionTreeServiceTest {

    private final String AGE_KEY = "age";
    private final String INCOME_KEY = "income";
    private final String STUDENT_KEY = "student";
    private final String CREDIT_RATING_KEY = "credit_rating";
    private final String CLASS_LABEL_KEY = "buys_computer";

    private DecisionTreeServiceInterface decisionTreeService;
    private Set<DataTuple> trainingTuples;
    private Set<String> candidateAttributes;

    @Before
    public void setUp() {
        decisionTreeService = new DecisionTreeService();

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

    @Test
    public void testAllTuplesOfSameClassShouldBeTrue() {
        for (Map<String, String> tuple : trainingTuples) {
            tuple.put(CLASS_LABEL_KEY, BoolProp.YES.toString());
        }

        assertTrue(decisionTreeService.allTuplesOfSameClass(trainingTuples, CLASS_LABEL_KEY));
    }

    @Test
    public void testAllTuplesOfSameClassShouldBeFalse() {
        assertFalse(decisionTreeService.allTuplesOfSameClass(trainingTuples, CLASS_LABEL_KEY));
    }

    @Test
    public void testGetMajorityClassShouldBeYes() {
        assertEquals(BoolProp.YES.toString(), decisionTreeService.getMajorityClass(trainingTuples, CLASS_LABEL_KEY));
    }

    @Test
    public void testGetSplittingAttributeShouldBeAge() {
        String splittingAttribute = decisionTreeService.getSplittingAttribute(trainingTuples, candidateAttributes, CLASS_LABEL_KEY);

        assertNotNull(splittingAttribute);
        assertEquals(AGE_KEY, splittingAttribute);
    }

    @Test
    public void testGetSplittingAttributeShouldBeStudent() {
        candidateAttributes.remove(AGE_KEY);

        String splittingAttribute = decisionTreeService.getSplittingAttribute(trainingTuples, candidateAttributes, CLASS_LABEL_KEY);

        assertNotNull(splittingAttribute);
        assertEquals(STUDENT_KEY, splittingAttribute);
    }

    @Test
    public void testGetSplittingAttributeShouldBeCreditRating() {
        candidateAttributes.remove(AGE_KEY);
        candidateAttributes.remove(STUDENT_KEY);

        String splittingAttribute = decisionTreeService.getSplittingAttribute(trainingTuples, candidateAttributes, CLASS_LABEL_KEY);

        assertNotNull(splittingAttribute);
        assertEquals(CREDIT_RATING_KEY, splittingAttribute);
    }

    @Test
    public void testGetSplittingAttributeShouldBeCreditIncome() {
        candidateAttributes.remove(AGE_KEY);
        candidateAttributes.remove(STUDENT_KEY);
        candidateAttributes.remove(CREDIT_RATING_KEY);

        String splittingAttribute = decisionTreeService.getSplittingAttribute(trainingTuples, candidateAttributes, CLASS_LABEL_KEY);

        assertNotNull(splittingAttribute);
        assertEquals(INCOME_KEY, splittingAttribute);
    }

    @Test
    public void testGetMatchingTuples() {
        assertEquals(5, decisionTreeService.getMatchingTuples(trainingTuples, Age.YOUTH.toString()).size());
    }

    private enum Age {
        YOUTH("young"),
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
    }

    private DataTuple createTuple(Age age, Income income, BoolProp student, CreditRating creditRating, BoolProp buysComputer) {
        DataTuple tuple = new DataTuple();
        tuple.put(AGE_KEY, age.toString());
        tuple.put(INCOME_KEY, income.toString());
        tuple.put(STUDENT_KEY, student.toString());
        tuple.put(CREDIT_RATING_KEY, creditRating.toString());
        tuple.put(CLASS_LABEL_KEY, buysComputer.toString());

        return tuple;
    }
}
