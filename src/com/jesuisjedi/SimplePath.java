package com.jesuisjedi;

import java.util.ArrayList;

public class SimplePath {
    ArrayList<SimpleNode> nodes = new ArrayList<>();
    ArrayList<SimpleEdge> edges = new ArrayList<>();

    public void add(SimpleNode vertex, SimpleEdge edge) {
        this.nodes.add(vertex);
        this.edges.add(edge);
    }

    public double getPathWeight() {
        double weight = 0.0;
        if (edges == null) {
            return weight;
        }
        for (SimpleEdge edge : edges) {
            if(edge != null)
                weight += edge.weight;
        }

        return weight;
    }

    // to string method
    public String toString() {
        String result = "";
        if (nodes == null) {
            // result = "No path found";
            return result;
        }

        for (SimpleNode node : nodes) {
            result += node.ID;
            if (nodes.indexOf(node) != nodes.size() - 1) {
                result += " <- ";
            }
        }

        return result;
    }
}
