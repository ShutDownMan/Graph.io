package com.jesuisjedi;

import java.util.ArrayList;

public class SimpleGraph {
    ArrayList<SimpleNode> nodes;
    ArrayList<SimpleEdge> edges;
    boolean isDirected = false;

    public SimpleGraph() {
        this.isDirected = false;
    }

    public SimpleGraph(boolean isDirected) {
        this.isDirected = isDirected;
    }

    public void addEdge(String edgeID, SimpleNode source, SimpleNode target, double weight) {
        SimpleEdge newEdge = new SimpleEdge(edgeID, source, target, weight);
        this.edges.add(newEdge);
        if(this.isDirected) {
            source.addLeavingEdge(newEdge);
            target.addEnteringEdge(newEdge);
        }
    }

    public SimpleEdge addEdge(String edgeID, String sourceID, String targetID, double weight, boolean isDirected) {
        SimpleNode source = this.getNode(sourceID);
        SimpleNode target = this.getNode(targetID);
        SimpleEdge newEdge = new SimpleEdge(edgeID, source, target, weight);
        this.edges.add(newEdge);
        if(isDirected) {
            source.addLeavingEdge(newEdge);
            target.addEnteringEdge(newEdge);
        } else {
            source.addLeavingEdge(newEdge);
            source.addEnteringEdge(newEdge);
            target.addLeavingEdge(newEdge);
            target.addEnteringEdge(newEdge);
        }

        return newEdge;
    }

    public SimpleNode getNode(String nodeID) {
        return this.nodes.stream().filter(node -> node.ID.equals(nodeID)).findFirst().get();
    }

    public SimpleNode addNode(String nodeID) {
        SimpleNode newNode = new SimpleNode(nodeID);
        this.nodes.add(newNode);
        return newNode;
    }

}
