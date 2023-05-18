package com.jesuisjedi;

import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.LinkedList;

public class DijkstraAnimator {
    public static void animate(GraphHandler gh, String source) {
        gh.graph.stepBegins(0);

        gh.graph.setAttribute("ui.quality");
        gh.graph.setAttribute("ui.antialias");
        gh.graph.setAttribute("ui.stylesheet", "url(data/style.css);");

        gh.graph.stepBegins(1);

        gh.viewer = gh.graph.display();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // try {
        //     DijkstraAnimator.searchGraph(gh, source);
        // } catch (InterruptedException e) {
        //     throw new RuntimeException(e);
        // }
    }

    // private static void searchGraph(GraphHandler gh, String source) throws InterruptedException {
    //     LinkedList<Edge> treeEdges = new LinkedList<>();

    //     int n = gh.graph.getNodeCount();
    //     DijkstraAnimator.Data[] data = new DijkstraAnimator.Data[n];
    //     FibonacciHeap<Double, Node> heap = new FibonacciHeap<>();

    //     for(int i = 0; i < n; ++i) {
    //         data[i] = new DijkstraAnimator.Data();
    //         data[i].edgeToTree = null;
    //         data[i].fn = heap.add(Double.POSITIVE_INFINITY, gh.graph.getNode(i));
    //     }

    //     double treeWeight = 0.0;
    //     while(!heap.isEmpty()) {
    //         Node u = heap.extractMin();
    //         DijkstraAnimator.Data dataU = data[u.getIndex()];
    //         data[u.getIndex()] = null;
    //         if (dataU.edgeToTree != null) {
    //             treeEdges.add(dataU.edgeToTree);
    //             dataU.edgeToTree.setAttribute("ui.class", "tag1");
    //             Thread.sleep(1000);
    //             treeWeight += dataU.fn.getKey();
    //             dataU.edgeToTree = null;
    //         }

    //         dataU.fn = null;

    //         for(Edge e : u.getEachEdge()) {
    //             Node v = e.getOpposite(u);
    //             DijkstraAnimator.Data dataV = data[v.getIndex()];
    //             if (dataV != null) {
    //                 double w = e.getNumber("weight");
    //                 if (w < dataV.fn.getKey()) {
    //                     heap.decreaseKey(dataV.fn, w);
    //                     dataV.edgeToTree = e;
    //                 }
    //             }
    //         }
    //     }

    //     System.out.println("Peso da arvore: " + treeWeight);
    // }
}

