package com.jesuisjedi;

public class SimpleEdge {
    SimpleNode source;
    SimpleNode target;
    double weight;

    String tag;
    String ID;

    public SimpleEdge(String ID, SimpleNode source, SimpleNode target, double weight) {
        this.ID = ID;
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public SimpleNode getTargetNode() {
        return this.target;
    }

    public SimpleNode getSourceNode() {
        return this.source;
    }

    public SimpleNode getOpposite(SimpleNode vertex) {
        return this.getSourceNode().equals(vertex) ? this.getTargetNode() : this.getSourceNode();
    }

    public String getId() {
        return this.ID;
    }
}
