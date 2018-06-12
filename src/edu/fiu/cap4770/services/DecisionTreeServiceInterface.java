package edu.fiu.cap4770.services;

import edu.fiu.cap4770.models.DataTuple;

import java.util.Map;
import java.util.Set;

public interface DecisionTreeServiceInterface {
    /**
     * Determines if the all the tuples from the given set have the same class label.
     * @param tuples The set of tuples to check.
     * @param classLabelKey The class label value to check for.
     * @return True if all the tuples share the same class label.
     */
    boolean allTuplesOfSameClass(Set<DataTuple> tuples, String classLabelKey);

    /**
     * Retrieves the class label value with the most occurrences in the given tuple set.
     * @param tuples The set of tuples to check.
     * @param classLabelKey The class label attribute key.
     * @return The class label value that was repeated the most in the set of tuples.
     */
    String getMajorityClass(Set<DataTuple> tuples, String classLabelKey);

    /**
     * Retrieves the "best" attribute to be used when splitting a node into multiple branches.
     * @param tuples The set of tuples to check.
     * @param attributes The set of candidate attributes to consider.
     * @param classLabelKey The class label attribute key.
     * @return The attribute key with the best chance to return a "pure" data set.
     */
    String getSplittingAttribute(Set<DataTuple> tuples, Set<String> attributes, String classLabelKey);

    /**
     * Gets all the tuples from the given set that match the given attribute value.
     * @param tuples The set of tuples to check.
     * @param attributeKey The key of the attribute.
     * @param attributeValue The value of the attribute to check for.
     * @return The subset of all tuples which have the given attribute value.
     */
    Set<DataTuple> getMatchingTuples(Set<DataTuple> tuples, String attributeKey, String attributeValue);
}
