package edu.fiu.cap4770.models;

public interface TreeComponent {
    void print();

    void print(int indentLevel);

    String getClassLabel(DataTuple tuple);
}
