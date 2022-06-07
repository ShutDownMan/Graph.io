package com.jesuisjedi;

import org.graphstream.algorithm.BellmanFord;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.util.DisjointSets;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class KruskalSearchAnimator {
    public static void animate(GraphHandler gh) {
        gh.graph.stepBegins(0);

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

        List<Edge> sortedEdges = gh.graph.edges().sorted(new EdgeComparator()).collect(Collectors.toList());
        DisjointSets<Node> components = new DisjointSets<>(gh.graph.getNodeCount());
        Iterator iter = gh.graph.iterator();

        while(iter.hasNext()) {
            Node node = (Node)iter.next();
            components.add(node);
        }

        iter = sortedEdges.iterator();

        while(iter.hasNext()) {
            Edge edge = (Edge)iter.next();
            if (components.union(edge.getNode0(), edge.getNode1())) {
                treeEdges.add(edge);

                edge.setAttribute("ui.class", "tag1");
                Thread.sleep(1000);
                if (treeEdges.size() == gh.graph.getNodeCount() - 1) {
                    break;
                }
            }
        }

        sortedEdges.clear();
        components.clear();
    }

    private static double getWeight(@NotNull Edge edge) {
        double w = edge.getNumber("weight");
        return Double.isNaN(w) ? 1.0 : w;
    }

    protected static class EdgeComparator implements Comparator<Edge> {
        protected EdgeComparator() { }

        public int compare(Edge arg0, Edge arg1) {
            double w0 = KruskalSearchAnimator.getWeight(arg0);
            double w1 = KruskalSearchAnimator.getWeight(arg1);
            if (w0 < w1) {
                return -1;
            } else {
                return w0 > w1 ? 1 : 0;
            }
        }
    }

}
