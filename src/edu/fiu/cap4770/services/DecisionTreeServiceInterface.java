package edu.fiu.cap4770.services;

import edu.fiu.cap4770.models.DataTuple;

import java.util.Map;
import java.util.Set;

public interface DecisionTreeServiceInterface {
    boolean allTuplesOfSameClass(Set<DataTuple> trainingTuples, String classLabelKey);

    String getMajorityClass(Set<DataTuple> trainingTuples, String classLabelKey);

    String getSplittingAttribute(Set<DataTuple> trainingTuples, Set<String> attributes, String classLabelKey);

    Set<DataTuple> getMatchingTuples(Set<DataTuple> trainingTuples, String attributeValue);
}
