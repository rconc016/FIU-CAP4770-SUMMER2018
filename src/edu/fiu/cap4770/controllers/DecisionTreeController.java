package edu.fiu.cap4770.controllers;

import edu.fiu.cap4770.models.*;
import edu.fiu.cap4770.services.DecisionTreeService;
import edu.fiu.cap4770.services.DecisionTreeServiceInterface;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Creates a new decision tree from a set of training tuples.
 */
public class DecisionTreeController {
    private DecisionTreeServiceInterface decisionTreeService;

    public DecisionTreeController() {
        decisionTreeService = new DecisionTreeService();
    }

    public DecisionTreeController(DecisionTreeServiceInterface decisionTreeService) {
        this.decisionTreeService = decisionTreeService;
    }

    public Node createDecisionTree(Set<DataTuple> trainingTuples, Set<String> attributes, String classLabel) {
        InternalNode node = new InternalNode("", new LinkedList<>());

        if (decisionTreeService.allTuplesOfSameClass(trainingTuples, classLabel)) {
            return new LeafNode(classLabel);
        }

        else if (attributes.isEmpty()) {
            return new LeafNode(decisionTreeService.getMajorityClass(trainingTuples, classLabel));
        }

        String splittingAttribute = decisionTreeService.getSplittingAttribute(trainingTuples, attributes, classLabel);
        node.setLabel(splittingAttribute);

        attributes.remove(splittingAttribute);

        for (String value : getKnownValues(trainingTuples, splittingAttribute)) {
            Set<DataTuple> matchingTuples = decisionTreeService.getMatchingTuples(trainingTuples, splittingAttribute, value);

            Node newNode;

            if (matchingTuples.isEmpty()) {
                newNode = new LeafNode(decisionTreeService.getMajorityClass(trainingTuples, classLabel));
            }

            else {
                newNode = createDecisionTree(matchingTuples, attributes, classLabel);
            }

            node.addBranch(new Branch(value, newNode));
        }

        return node;
    }

    private Set<String> getKnownValues(Set<DataTuple> trainingTuples, String attributeKey) {
        Set<String> knownValues = new HashSet();

        for (DataTuple tuple : trainingTuples) {
            String attributeValue = tuple.get(attributeKey);

            if (!knownValues.contains(attributeValue)) {
                knownValues.add(attributeValue);
            }
        }

        return knownValues;
    }
}
