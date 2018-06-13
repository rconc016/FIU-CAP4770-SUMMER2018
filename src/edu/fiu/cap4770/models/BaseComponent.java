package edu.fiu.cap4770.models;
import java.util.Iterator;

/**
 * Base decision tree component with a label.
 */
public abstract class BaseComponent implements TreeComponent, Iterable {
    public static final String DEFAULT_LABEL = "unnamed";
    public static final int INDENT_SPACES = 4;

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

    @Override
    public abstract Iterator<? extends TreeComponent> iterator();

    @Override
    public void print() {
        print(0);
    }

    @Override
    public void print(int indentLevel) {
        Iterator iterator = iterator();

        for (int i = 0; i < indentLevel; i++) {
            for (int j = 0; j < INDENT_SPACES; j++) {
                System.out.print(' ');
            }
        }

        System.out.println(getLabel());

        while(iterator.hasNext()) {
            TreeComponent component = (TreeComponent) iterator.next();
            component.print(indentLevel + 1);
        }
    }
}
