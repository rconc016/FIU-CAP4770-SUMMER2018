package edu.fiu.cap4770.models;

/**
 * Base decision tree component with a label.
 */
public class BaseComponent {
    public final String DEFAULT_LABEL = "unnamed";

    private String label;

    /**
     * Creates a tree component with the default label.
     */
    public BaseComponent() {
        label = DEFAULT_LABEL;
    }

    /**
     * Creates a tree component with the given label.
     * @param label The label to use for this component.
     */
    public BaseComponent(String label) {
        this.label = label;
    }

    /**
     * Gets the comoponent's label.
     * @return Component's label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the component's label.
     * @param label The label to use for this component.
     */
    public void setLabel(String label) {
        this.label = label;
    }
}
