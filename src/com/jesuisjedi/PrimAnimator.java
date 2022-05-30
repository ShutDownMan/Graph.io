package com.jesuisjedi;

import org.graphstream.algorithm.util.FibonacciHeap;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.LinkedList;

public class PrimAnimator {
    public static void animate(GraphHandler gh) {
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

        try {
            computeTree(gh);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void computeTree(GraphHandler gh) throws InterruptedException {
        LinkedList<Edge> treeEdges = new LinkedList<>();

        int n = gh.graph.getNodeCount();
        PrimAnimator.Data[] data = new PrimAnimator.Data[n];
        FibonacciHeap<Double, Node> heap = new FibonacciHeap<>();

        for(int i = 0; i < n; ++i) {
            data[i] = new PrimAnimator.Data();
            data[i].edgeToTree = null;
            data[i].fn = heap.add(Double.POSITIVE_INFINITY, gh.graph.getNode(i));
        }

        double treeWeight = 0.0;
        while(!heap.isEmpty()) {
            Node u = heap.extractMin();
            PrimAnimator.Data dataU = data[u.getIndex()];
            data[u.getIndex()] = null;
            if (dataU.edgeToTree != null) {
                treeEdges.add(dataU.edgeToTree);
                dataU.edgeToTree.setAttribute("ui.class", "tag1");
                Thread.sleep(1000);
                treeWeight += dataU.fn.getKey();
                dataU.edgeToTree = null;
            }

            dataU.fn = null;
            u.edges().filter((e) -> data[e.getOpposite(u).getIndex()] != null).forEach((e) -> {
                Node v = e.getOpposite(u);
                PrimAnimator.Data dataV = data[v.getIndex()];
                double w = getWeight(e);
                if (w < dataV.fn.getKey()) {
                    heap.decreaseKey(dataV.fn, w);
                    dataV.edgeToTree = e;
                }
            });
        }

        System.out.println(treeEdges);
        System.out.println(treeWeight);
    }

    private static double getWeight(Edge edge) {
        double w = edge.getNumber("weight");
        return Double.isNaN(w) ? 1.0 : w;
    }

    protected static class Data {
        Edge edgeToTree;
        FibonacciHeap<Double, Node>.Node fn;

        protected Data() {
        }
    }

}
