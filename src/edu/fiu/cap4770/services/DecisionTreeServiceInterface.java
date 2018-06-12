package edu.fiu.cap4770.services;

import edu.fiu.cap4770.models.CandidateAttribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface DecisionTreeServiceInterface {
    boolean allTuplesOfSameClass(Set<Map<String, String>> trainingTuples, String classLabelKey);

    String getMajorityClass(Set<Map<String, String>> trainingTuples, String classLabelKey);

    String getSplittingAttribute(Set<Map<String, String>> trainingTuples, Set<String> attributes, String classLabelKey);

    Set<Map<String, String>> getMatchingTuples(Set<Map<String, String>> trainingTuples, String attributeValue);
}
