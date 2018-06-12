package edu.fiu.cap4770.services;

import edu.fiu.cap4770.models.*;

import java.util.*;

/**
 * Handles all the major operations when creating a decision tree.
 */
public class DecisionTreeService implements DecisionTreeServiceInterface {
    public boolean allTuplesOfSameClass(Set<DataTuple> tuples, String classLabel) {
        String previousValue = null;

        for (DataTuple tuple : tuples) {
            String currentValue = tuple.get(classLabel);

            if (previousValue != null && !currentValue.equals(previousValue)) {
                return false;
            }

            previousValue = currentValue;
        }

        return true;
    }

    public String getMajorityClass(Set<DataTuple> tuples, String classLabelKey) {
        Map<String, Integer> counts = new HashMap();
        String maxClassLabel = "";
        int maxCount = 0;

        for (Map<String, String> tuple : tuples) {
            String currentClassLabel = tuple.get(classLabelKey);
            int currentCount = 0;

            if (counts.containsKey(currentClassLabel)) {
                currentCount = counts.get(currentClassLabel);
            }

            counts.put(currentClassLabel, currentCount + 1);
        }

        for (String key : counts.keySet()) {
            int currentCount = counts.get(key);

            if (currentCount > maxCount) {
                maxCount = currentCount;
                maxClassLabel = key;
            }
        }

        return maxClassLabel;
    }

    public String getSplittingAttribute(Set<DataTuple> tuples, Set<String> attributes, String classLabelKey) {
        Map<String, Double> gains = new HashMap();
        double max = 0.0;
        String splittingAttribute = null;

        for (String attribute : attributes) {
            double attributeGain = getAttributeGain(tuples, attribute, classLabelKey);

            gains.put(attribute, attributeGain);

            if (max < attributeGain) {
                max = attributeGain;
                splittingAttribute = attribute;
            }
        }

        return splittingAttribute;
    }

    public Set<DataTuple> getMatchingTuples(Set<DataTuple> tuples, String attributeKey, String attributeValue) {
        Set<DataTuple> matchingTuples = new HashSet();

        for (DataTuple tuple : tuples) {
            if (tuple.get(attributeKey).equals(attributeValue)) {
                matchingTuples.add(tuple);
            }
        }

        return matchingTuples;
    }

    /**
     * Retrieves the Gain score for the specified attribute.
     * The formula is: Gain(attribute) = Info(class) - Info<attribute>().
     * @param tuples The set of tuples to check.
     * @param attributeKey The attribute to score.
     * @param classLabelKey The key for the class attribute.
     * @return The Gain score calculated for the attribute.
     */
    private double getAttributeGain(Set<DataTuple> tuples, String attributeKey, String classLabelKey) {
        return getClassLabelInfo(tuples, classLabelKey) - getExpectedAttributeInfo(tuples, attributeKey, classLabelKey);
    }

    /**
     * Calculates the expected information required to classify an attribute.
     * The formula is: Info<attribute>() = Sum((Count(attributeValue) / Count(tuples))* Info(attribute))
     * @param tuples The set of tuples to check.
     * @param attributeKey The attribute to score.
     * @param classLabelKey The key for the class attribute.
     * @return The expected information score calculated for the attribute.
     */
    private double getExpectedAttributeInfo(Set<DataTuple> tuples, String attributeKey, String classLabelKey) {
        double infoValue = 0.0;
        Map<String, Double> attributeTotals = new HashMap();

        for (Map<String, String> tuple : tuples) {
            String attributeValue = tuple.get(attributeKey);

            attributeTotals.putIfAbsent(attributeValue, 0.0);
            attributeTotals.put(attributeValue, attributeTotals.get(attributeValue) + 1.0);
        }

        for (Map.Entry<String, Double> totalEntry : attributeTotals.entrySet()) {
            infoValue += (totalEntry.getValue() / tuples.size()) * getAttributeInfo(tuples, attributeKey, totalEntry.getKey(), classLabelKey);
        }

        return infoValue;
    }

    /**
     * Calculates the average amount of information needed to identify
     * the class label of a tuple.
     * The formula is: -Sum((Count(classLabelValue) / Count(tuples)) * log<2>((Count(classLabelValue) / Count(tuples))))
     * @param tuples The set of tuples to check.
     * @param classLabelKey The key for the class attribute.
     * @return The expected information score calculated for the class attribute.
     */
    private double getClassLabelInfo(Set<DataTuple> tuples, String classLabelKey) {
        Map<String, Double> attributeClassLabelTotals = new HashMap();

        for (Map<String, String> tuple : tuples) {
            String classLabelValue = tuple.get(classLabelKey);

            attributeClassLabelTotals.putIfAbsent(classLabelValue, 0.0);
            attributeClassLabelTotals.put(classLabelValue, attributeClassLabelTotals.get(classLabelValue) + 1.0);
        }

        return getInfo(attributeClassLabelTotals, tuples.size());
    }

    /**
     * Calculates the average amount of information needed to identify
     * the class label of a tuple.
     * The formula is: -Sum((Count(attributeValue) / Count(classLabelValue)) * log<2>((Count(attributeValue) / Count(classLabelValue))))
     * @param tuples The set of tuples to check.
     * @param attributeKey The key of the attribute to score.
     * @param attributeValue The value of the attribute to score.
     * @param classLabelKey The key for the class attribute.
     * @return The expected information score calculated for the given attribute.
     */
    private double getAttributeInfo(Set<DataTuple> tuples, String attributeKey, String attributeValue, String classLabelKey) {
        Map<String, Double> attributeClassLabelTotals = new HashMap();
        int total = 0;

        for (Map<String, String> tuple : tuples) {
            if (tuple.get(attributeKey).equals(attributeValue)) {
                String classLabelValue = tuple.get(classLabelKey);

                attributeClassLabelTotals.putIfAbsent(classLabelValue, 0.0);
                attributeClassLabelTotals.put(classLabelValue, attributeClassLabelTotals.get(classLabelValue) + 1.0);

                total++;
            }
        }

        return getInfo(attributeClassLabelTotals, total);
    }

    /**
     * Gets the expected information needed to classify a tuple from the given set.
     * @param counts The number of occurrences for every known value of a single attribute.
     * @param total The total number of occurrences for all known values of a single attribute.
     * @return The expected information of the a single attribute.
     */
    private double getInfo(Map<String, Double> counts, int total) {
        final double LOG_BASE = 2;

        double attributeInfoValue = 0.0;

        for (Map.Entry<String, Double> count : counts.entrySet()) {
            double probability = count.getValue() / total;
            double probabilityLog = Math.log(probability) / Math.log(LOG_BASE);

            attributeInfoValue += -probability * probabilityLog;
        }

        return attributeInfoValue;
    }
}
