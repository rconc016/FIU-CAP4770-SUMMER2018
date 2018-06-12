package edu.fiu.cap4770.models;

import java.util.Set;

public class CandidateAttribute {
    private String name;
    private Set<String> knownValues;

    public CandidateAttribute(String name, Set<String> knownValues) {
        this.name = name;
        this.knownValues = knownValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getKnownValues() {
        return knownValues;
    }

    public void setKnownValues(Set<String> knownValues) {
        this.knownValues = knownValues;
    }
}
