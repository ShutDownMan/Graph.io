package com.jesuisjedi;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

public class GraphHandler {
    public Graph graph;
    public boolean isDirected;
    public int numOfVertices;
    public Viewer viewer;

    public GraphHandler(Graph graph) {
        this.graph = graph;
        this.isDirected = false;
    }

    public void addVertice(String source, String destination, String weight) {
        if(this.graph.getNode(source) == null) {
            Node newNode = this.graph.addNode(source);
            newNode.setAttribute("ui.label", source);
        }
        if(this.graph.getNode(destination) == null) {
            Node newNode = this.graph.addNode(destination);
            newNode.setAttribute("ui.label", destination);
        }

        Edge newEdge = this.graph.addEdge("id_" + source + "_" + destination, source, destination, isDirected);
        newEdge.setAttribute("weight", Double.parseDouble(weight));
        newEdge.setAttribute("ui.label", weight);
    }

    public void clearClasses() {
        if(this.graph != null) {
            this.graph.nodes().forEach(node -> {
                node.setAttribute("ui.class", (Object) null);
            });
            this.graph.edges().forEach(edge -> {
                edge.setAttribute("ui.class", (Object) null);
            });

        }
    }
}
