package edu.fiu.cap4770.models;

import javax.xml.crypto.Data;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a decision tree's internal node. It will perform a test
 * on an tuple attribute and generate branches to other nodes.
 */
public class InternalNode extends BaseComponent implements Node {
    private List<Branch> branches;

    /**
     * Creates a new internal node with the given label
     * and list of branches.
     *
     * @param label    The label to be used for this node.
     * @param branches The list of outgoing branches to other nodes.
     */
    public InternalNode(String label, List<Branch> branches) {
        super(label);

        if (branches == null || branches.size() == 0) {
            throw new InvalidParameterException("Cannot create internal node with no branches.");
        }

        this.branches = branches;
    }

    /**
     * Gets the branches of this internal node.
     *
     * @return Outgoing branches to other nodes.
     */
    public List<Branch> getBranches() {
        return branches;
    }

    /**
     * Sets the internal node's branches to other nodes.
     *
     * @param branches The list of outgoing branches to other nodes.
     */
    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    /**
     * Adds a new branch to this node.
     *
     * @param branch The branch to be added.
     */
    public void addBranch(Branch branch) {
        branches.add(branch);
    }

    @Override
    public NodeType getType() {
        return NodeType.Internal;
    }

    @Override
    public Iterator<? extends TreeComponent> iterator() {
        return branches.iterator();
    }

    @Override
    public String getClassLabel(DataTuple tuple) {
        String value = tuple.get(getLabel());

        for(Branch branch : branches) {
            if (branch.getLabel().equals(value)) {
                return branch.getClassLabel(tuple);
            }
        }

        return "Not Found";
    }
}
