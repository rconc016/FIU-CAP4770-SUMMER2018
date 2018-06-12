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

    /**
     * Creates a new decision tree from the given set of training tuples.
     * @param trainingTuples The set of tuples used to build the tree.
     * @param attributes The set of candidate attributes creates nodes from.
     * @param classLabelKey The key of the class attribute.
     * @return The root node of the newly created decision tree.
     */
    public Node createDecisionTree(Set<DataTuple> trainingTuples, Set<String> attributes, String classLabelKey) {
        if (decisionTreeService.allTuplesOfSameClass(trainingTuples, classLabelKey)) {
            return new LeafNode(decisionTreeService.getMajorityClass(trainingTuples, classLabelKey));
        }

        else if (attributes.isEmpty()) {
            return new LeafNode(decisionTreeService.getMajorityClass(trainingTuples, classLabelKey));
        }

        String splittingAttribute = decisionTreeService.getSplittingAttribute(trainingTuples, attributes, classLabelKey);

        attributes.remove(splittingAttribute);

        LinkedList<Branch> branches = new LinkedList<>();

        for (String value : getKnownValues(trainingTuples, splittingAttribute)) {
            Set<DataTuple> matchingTuples = decisionTreeService.getMatchingTuples(trainingTuples, splittingAttribute, value);

            Node newNode;

            if (matchingTuples.isEmpty()) {
                newNode = new LeafNode(decisionTreeService.getMajorityClass(trainingTuples, classLabelKey));
            }

            else {
                newNode = createDecisionTree(matchingTuples, attributes, classLabelKey);
            }

            branches.add(new Branch(value, newNode));
        }

        return new InternalNode(splittingAttribute, branches);
    }

    /**
     * Retrieves the set of known values for the given attribute.
     * @param tuples The set of tuples to check.
     * @param attributeKey The key of the attribute to get the known values for.
     * @return The set of all distinct values found in the set of tuples for the given attribute.
     */
    private Set<String> getKnownValues(Set<DataTuple> tuples, String attributeKey) {
        Set<String> knownValues = new HashSet();

        for (DataTuple tuple : tuples) {
            String attributeValue = tuple.get(attributeKey);

            if (!knownValues.contains(attributeValue)) {
                knownValues.add(attributeValue);
            }
        }

        return knownValues;
    }
}
