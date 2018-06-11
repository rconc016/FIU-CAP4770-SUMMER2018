package edu.fiu.cap4770.models;

/**
 * Represents a decision tree's leaf node. Leaf nodes do not
 * have branches and are the end result of decision tree's path.
 */
public class LeafNode extends BaseComponent implements Node {

    /**
     * Creates a tree component with the given label.
     * @param label The label to use for this component.
     */
    public LeafNode(String label) {
        super(label);
    }

    @Override
    public NodeType getType() {
        return NodeType.Leaf;
    }
}
