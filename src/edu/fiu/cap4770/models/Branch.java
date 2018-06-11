package edu.fiu.cap4770.models;

import java.security.InvalidParameterException;

/**
 * Represents a branch of a decision tree. The branch must
 * lead to a node.
 */
public class Branch extends BaseComponent {
    private Node node;

    /**
     * Creates a new branch with given label and node.
     * @param label The label to use for this branch.
     * @param node The node connected to this branch.
     */
    public Branch(String label, Node node) {
        super(label);

        if (node == null) {
            throw new InvalidParameterException("Cannot create branch with no node.");
        }

        this.node = node;
    }
}
