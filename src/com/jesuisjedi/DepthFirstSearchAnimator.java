package com.jesuisjedi;

import org.graphstream.algorithm.BellmanFord;
import org.graphstream.graph.Node;

import java.io.IOException;
import java.util.Objects;

public class DepthFirstSearchAnimator {
    public static void animate(GraphHandler gh, String source) {
        if(gh.graph.getNode(source) == null) {
            System.out.println("Origem invalida!");
            return;
        }

//        gh.graph.stepBegins(0);
//
//        gh.graph.setAttribute("ui.quality");
//        gh.graph.setAttribute("ui.antialias");
//        gh.graph.setAttribute("ui.stylesheet", "url(data/style.css);");
//
//        gh.graph.getNode(source).setAttribute("ui.class", "source");
//
//        gh.graph.stepBegins(1);
//
//        gh.viewer = gh.graph.display();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        DepthFirstSearchAnimator.searchGraph(gh, source);

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
            DepthFirstSearchAnimator.searchGraphRec(gh, sourceNode);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void searchGraphRec(GraphHandler gh, Node currentNode) throws InterruptedException {
        double currentStep = gh.graph.getStep() + 1;
        gh.graph.stepBegins("teste", 0, currentStep);

        currentNode.setAttribute("ui.class", "tag1");
        currentNode.setAttribute("tag", "tag1");

        Thread.sleep(1000);

//        System.out.println("CALLED ME!");
//        System.out.println(currentNode.edges().count());
        System.out.print(currentNode.getId() + "-");

        currentNode.leavingEdges().forEach(edge -> {
            try {
                Node nextNode = (edge.getTargetNode().getId().equals(currentNode.getId())) ? edge.getSourceNode() : edge.getTargetNode();

//                System.out.println(nextNode.getId());

                if(nextNode.getAttribute("tag") == null) {
                    edge.setAttribute("ui.class", "tag1");
                    edge.setAttribute("tag", "tag1");

//                    Thread.sleep(1000);

                    DepthFirstSearchAnimator.searchGraphRec(gh, nextNode);

                    edge.setAttribute("ui.class", "tag2");
                    edge.setAttribute("tag", "tag2");

//                    Thread.sleep(1000);
                }

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        currentNode.setAttribute("ui.class", "tag2");
        currentNode.setAttribute("tag", "tag2");

        Thread.sleep(1000);

    }
}
