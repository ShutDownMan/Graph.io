package com.jesuisjedi;

import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleNode {
    public String ID = "?ID?";
    public LinkedList<SimpleEdge> edges;
    public LinkedList<SimpleEdge> leavingEdges;
    public LinkedList<SimpleEdge> enteringEdges;
    public LinkedList<SimpleNode> nodes;
    public double distance;
    public ArrayList<SimpleEdge> predecessors;
    String tag;

    public SimpleNode(String ID) {
        this.ID = ID;
        this.edges = new LinkedList<>();
        this.leavingEdges = new LinkedList<>();
        this.enteringEdges = new LinkedList<>();
        this.nodes = new LinkedList<>();
    }

    public void addEdge(SimpleEdge edge) {
        this.edges.add(edge);
    }

    public void addLeavingEdge(SimpleEdge edge) {
        this.addEdge(edge);
        this.leavingEdges.add(edge);
        this.nodes.add(edge.source);
    }

    public void addEnteringEdge(SimpleEdge edge) {
        this.addEdge(edge);
        this.enteringEdges.add(edge);
        this.nodes.add(edge.target);
    }

    public String getId() {
        return this.ID;
    }

    public int getIndex() {
        return Integer.parseInt(this.ID);
    }
}
