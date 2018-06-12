package edu.fiu.cap4770.services;

import edu.fiu.cap4770.models.*;

import java.util.*;

/**
 * Handles all the major operations when creating a decision tree.
 */
public class DecisionTreeService implements DecisionTreeServiceInterface {

    /**
     * Determines if the all the tuples from the given set have the same class label.
     * @param tuples The set of tuples to check.
     * @param classLabel The class label value to check for.
     * @return True if all the tuples share the same class label.
     */
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

    /**
     * 
     * @param trainingTuples
     * @param classLabel
     * @return
     */
    public String getMajorityClass(Set<DataTuple> trainingTuples, String classLabel) {
        Map<String, Integer> counts = new HashMap();
        String maxClassLabel = "";
        int maxCount = 0;

        for (Map<String, String> tuple : trainingTuples) {
            String currentClassLabel = tuple.get(classLabel);
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

    public String getSplittingAttribute(Set<DataTuple> trainingTuples, Set<String> attributes, String classLabel) {
        Map<String, Double> gains = new HashMap();
        double max = 0.0;
        String splittingAttribute = null;

        for (String attribute : attributes) {
            double attributeGain = getAttributeGain(trainingTuples, attribute, classLabel);

            gains.put(attribute, attributeGain);

            if (max < attributeGain) {
                max = attributeGain;
                splittingAttribute = attribute;
            }
        }

        return splittingAttribute;
    }

    private double getAttributeGain(Set<DataTuple> trainingTuples, String attribute, String classLabel) {
        return getClassLabelInfo(trainingTuples, classLabel) - getExpectedAttributeInfo(trainingTuples, attribute, classLabel);
    }

    private double getExpectedAttributeInfo(Set<DataTuple> trainingTuples, String attribute, String classLabel) {
        double infoValue = 0.0;
        Map<String, Double> attributeTotals = new HashMap();

        for (Map<String, String> tuple : trainingTuples) {
            String attributeValue = tuple.get(attribute);

            attributeTotals.putIfAbsent(attributeValue, 0.0);
            attributeTotals.put(attributeValue, attributeTotals.get(attributeValue) + 1.0);
        }

        for (Map.Entry<String, Double> totalEntry : attributeTotals.entrySet()) {
            infoValue += (totalEntry.getValue() / trainingTuples.size()) * getAttributeInfo(trainingTuples, attribute, totalEntry.getKey(), classLabel);
        }

        return infoValue;
    }

    private double getClassLabelInfo(Set<DataTuple> trainingTuples, String classLabel) {
        Map<String, Double> attributeClassLabelTotals = new HashMap();

        for (Map<String, String> tuple : trainingTuples) {
            String classLabelValue = tuple.get(classLabel);

            attributeClassLabelTotals.putIfAbsent(classLabelValue, 0.0);
            attributeClassLabelTotals.put(classLabelValue, attributeClassLabelTotals.get(classLabelValue) + 1.0);
        }

        return getInfo(attributeClassLabelTotals, trainingTuples.size());
    }

    private double getAttributeInfo(Set<DataTuple> trainingTuples, String attributeName, String attributeValue, String classLabel) {
        Map<String, Double> attributeClassLabelTotals = new HashMap();
        int total = 0;

        for (Map<String, String> tuple : trainingTuples) {
            if (tuple.get(attributeName).equals(attributeValue)) {
                String classLabelValue = tuple.get(classLabel);

                attributeClassLabelTotals.putIfAbsent(classLabelValue, 0.0);
                attributeClassLabelTotals.put(classLabelValue, attributeClassLabelTotals.get(classLabelValue) + 1.0);

                total++;
            }
        }

        return getInfo(attributeClassLabelTotals, total);
    }

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

    public Set<DataTuple> getMatchingTuples(Set<DataTuple> trainingTuples, String attributeValue) {
        Set<DataTuple> matchingTuples = new HashSet();

        for (DataTuple tuple : trainingTuples) {
            for (Map.Entry<String, String> attribute : tuple.entrySet()) {
                if (attribute.getValue().equals(attributeValue)) {
                    matchingTuples.add(tuple);
                }
            }
        }

        return matchingTuples;
    }
}
