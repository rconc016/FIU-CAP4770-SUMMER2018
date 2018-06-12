package edu.fiu.cap4770.models;

/**
 * Represents all types of decision tree nodes.
 */
public interface Node {
    /**
     * The types of decision tree nodes available.
     */
    enum NodeType {
        Internal,
        Leaf
    }

    /**
     * Gets the type of this node.
     * @return The type of node.
     */
    NodeType getType();
}
