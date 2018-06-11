package edu.fiu.cap4770;

import edu.fiu.cap4770.models.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Introduction to Data Mining!");
    }

    public static Node createDecisionTree(Set<Map<String, String>> trainingTuples, Set<CandidateAttribute> attributes, String classLabel) {
        InternalNode node = new InternalNode("", new LinkedList<>());

        if (allTuplesOfSameClass(trainingTuples, classLabel)) {
            return new LeafNode(classLabel);
        }

        else if (attributes.isEmpty()) {
            return new LeafNode(getMajorityClass(trainingTuples, classLabel));
        }

        CandidateAttribute splittingAttribute = getSplittingAttribute(trainingTuples, attributes);
        node.setLabel(splittingAttribute.getName());

        attributes.remove(splittingAttribute);

        for (String value : splittingAttribute.getKnownValues()) {
            Set<Map<String, String>> matchingTuples = getMatchingTuples(trainingTuples, value);

            Node newNode;

            if (matchingTuples.isEmpty()) {
                newNode = new LeafNode(getMajorityClass(trainingTuples, classLabel));
            }

            else {
                newNode = createDecisionTree(matchingTuples, attributes, classLabel);
            }

            node.addBranch(new Branch(value, newNode));
        }

        return node;
    }

    public static boolean allTuplesOfSameClass(Set<Map<String, String>> trainingTuples, String classLabel) {
        String previousValue = null;

        for (Map<String, String> tuple : trainingTuples) {
            String currentValue = tuple.get(classLabel);

            if (!currentValue.equals(previousValue)) {
                return false;
            }

            previousValue = currentValue;
        }

        return true;
    }

    public static String getMajorityClass(Set<Map<String, String>> trainingTuples, String classLabel) {
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

    public static CandidateAttribute getSplittingAttribute(Set<Map<String, String>> trainingTuples, Set<CandidateAttribute> attributes) {
        return null;
    }

    public static Set<Map<String, String>> getMatchingTuples(Set<Map<String, String>> trainingTuples, String attributeValue) {
        Set<Map<String, String>> matchingTuples = new HashSet();

        for (Map<String, String> tuple : trainingTuples) {
            for (Map.Entry<String, String> attribute : tuple.entrySet()) {
                if (attribute.getValue().equals(attributeValue)) {
                    matchingTuples.add(tuple);
                }
            }
        }

        return matchingTuples;
    }
}
