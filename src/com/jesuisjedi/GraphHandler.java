package com.jesuisjedi;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

public class GraphHandler {
    boolean isSimpleGraph;
    public Graph graph;
    public SimpleGraph simpleGraph;
    public boolean isDirected;
    public int numOfVertices;
    public Viewer viewer;

    public GraphHandler(Graph graph) {
        this.graph = graph;
        this.isDirected = false;
        this.isSimpleGraph = false;
    }

    public GraphHandler(SimpleGraph graph) {
        this.simpleGraph = graph;
        this.isDirected = false;
        this.isSimpleGraph = true;
    }

    public void addVertex(String source, String destination, double weight) {
        if(this.isSimpleGraph) {
            this.addVertexSimple(source, destination, weight);
            return;
        }

        if(this.graph.getNode(source) == null) {
            Node newNode = this.graph.addNode(source);
            newNode.setAttribute("ui.label", source);
        }
        if(this.graph.getNode(destination) == null) {
            Node newNode = this.graph.addNode(destination);
            newNode.setAttribute("ui.label", destination);
        }

        Edge newEdge = this.graph.addEdge("id_" + source + "_" + destination, source, destination, isDirected);
        newEdge.setAttribute("weight", weight);
        newEdge.setAttribute("ui.label", weight);
    }

    public void addVertexSimple(String source, String destination, double weight) {
        if(this.simpleGraph.getNode(source) == null) {
            SimpleNode newNode = this.simpleGraph.addNode(source);
        }
        if(this.simpleGraph.getNode(destination) == null) {
            SimpleNode newNode = this.simpleGraph.addNode(destination);
        }

        SimpleEdge newEdge = this.simpleGraph.addEdge("id_" + source + "_" + destination, source, destination, weight, isDirected);
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
