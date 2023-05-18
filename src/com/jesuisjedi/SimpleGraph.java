package com.jesuisjedi;

import java.util.ArrayList;

public class SimpleGraph {
    ArrayList<SimpleNode> nodes;
    ArrayList<SimpleEdge> edges;
    boolean isDirected = false;

    public SimpleGraph() {
        this.isDirected = false;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public SimpleGraph(boolean isDirected) {
        this.isDirected = isDirected;
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
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
        if(this.nodes == null) {
            System.out.println("NODES NULL");
            return null;
        }
        if(this.nodes.stream().anyMatch(node -> node.ID.equals(nodeID)))
            return this.nodes.stream().filter(node -> node.ID.equals(nodeID)).findFirst().get();

        return null;
    }

    public SimpleEdge getEdge(SimpleNode n0, SimpleNode n1) {
        if(this.edges == null) {
            System.out.println("EDGES NULL");
            return null;
        }
        if(this.edges.stream().anyMatch(edge -> edge.source.equals(n0) && edge.target.equals(n1)))
            return this.edges.stream().filter(edge -> edge.source.equals(n0) && edge.target.equals(n1)).findFirst().get();

        return null;
    }

    public SimpleNode addNode(String nodeID) {
        SimpleNode newNode = new SimpleNode(nodeID);
        this.nodes.add(newNode);
        return newNode;
    }

}
