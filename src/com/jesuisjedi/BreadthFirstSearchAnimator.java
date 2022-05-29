package com.jesuisjedi;

import org.graphstream.graph.Node;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BreadthFirstSearchAnimator {
    public static void animate(GraphHandler gh, String source) {

        if(gh.graph.getNode(source) == null) {
            System.out.println("Origem invalida!");
            return;
        }

        gh.graph.stepBegins(0);

        gh.graph.setAttribute("ui.quality");
        gh.graph.setAttribute("ui.antialias");
        gh.graph.setAttribute("ui.stylesheet", "url(data/style.css);");

        gh.graph.getNode(source).setAttribute("ui.class", "source");

        gh.graph.stepBegins(1);

        gh.viewer = gh.graph.display();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        BreadthFirstSearchAnimator.searchGraph(gh, source);

//        BellmanFord bf = new BellmanFord("weight", source);
//        bf.init(gh.graph);
//        bf.compute();
//
//        System.out.println(bf.getShortestPath(gh.graph.getNode(destination)));
    }

    private static void searchGraph(GraphHandler gh, String source) {
        double currentStep = gh.graph.getStep() + 1;
        gh.graph.stepBegins(currentStep);

        Node sourceNode = gh.graph.getNode(source);
        try {
            BreadthFirstSearchAnimator.searchGraphIter(gh, sourceNode);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void searchGraphIter(GraphHandler gh, Node sourceNode) throws InterruptedException {
        double currentStep = gh.graph.getStep() + 1;
        gh.graph.stepBegins("teste", 0, currentStep);

        Queue<Node> nodeQueue = new LinkedList<>();

        nodeQueue.add(sourceNode);

        Node currentNode = null;
        while (nodeQueue.size() > 0) {
            currentNode = nodeQueue.peek();
            nodeQueue.remove();

            System.out.print(currentNode.getId() + "-");

            currentNode.setAttribute("ui.class", "tag1");
            currentNode.setAttribute("tag", "tag1");

            Thread.sleep(1000);

            Node finalCurrentNode = currentNode;
            currentNode.leavingEdges().forEach(edge -> {
                Node nextNode = (edge.getTargetNode().getId().equals(finalCurrentNode.getId())) ? edge.getSourceNode() : edge.getTargetNode();
                try {
                    if(nextNode.getAttribute("tag") == null) {
                        nextNode.setAttribute("ui.class", "tag1");
                        nextNode.setAttribute("tag", "tag1");

                        nodeQueue.add(nextNode);
                    }
                    edge.setAttribute("ui.class", "tag1");
                    edge.setAttribute("tag", "tag1");

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });

            currentNode.setAttribute("ui.class", "tag2");
            currentNode.setAttribute("tag", "tag2");

            Thread.sleep(1000);
        }
    }
}
