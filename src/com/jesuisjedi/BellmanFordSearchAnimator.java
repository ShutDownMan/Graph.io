package com.jesuisjedi;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.ArrayList;

public class BellmanFordSearchAnimator {
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

        BellmanFordSearchAnimator.searchGraph(gh, source);
    }

    private static void searchGraph(GraphHandler gh, String source) {
        System.out.print("origem:");
        System.out.println(source);

        prepareGraph(gh, gh.graph.getNode(source));
        gh.graph.nodes().forEach(currentNode -> {
            gh.clearClasses();
            Path shortestPath = null;
            try {
                shortestPath = getShortestPath(gh, gh.graph.getNode(source), currentNode);

                System.out.print(" destino: ");
                System.out.print(currentNode.getId());
                System.out.print(" dist.: ");
                System.out.print(shortestPath.getPathWeight("weight"));
                System.out.print(" caminho: ");
                System.out.println(shortestPath);

                Thread.sleep(1000);
                gh.clearClasses();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        });
    }

    private static Path getShortestPath(GraphHandler gh, Node source, Node target) throws InterruptedException {
        Path p = new Path();

        String identifier = "id";
        if (target == source) {
            return p;
        } else {
            boolean noPath = false;
            Node v = target;

            while (v != source && !noPath) {
                ArrayList<? extends Edge> list = (ArrayList) v.getAttribute(identifier + ".predecessors");
                if (list == null) {
                    noPath = true;
                } else {
                    Edge parentEdge = list.get(0);
                    p.add(v, parentEdge);

                    v.setAttribute("ui.class", "tag1");
                    parentEdge.setAttribute("ui.class", "tag1");

                    Thread.sleep(1000);

                    v = parentEdge.getOpposite(v);
                }
            }

            return p;
        }
    }

    private static void prepareGraph(GraphHandler gh, Node source) {
        String identifier = "id";

        gh.graph.nodes().forEach((n) -> {
            if (n == source) {
                n.setAttribute(identifier + ".distance", 0.0);
            } else {
                n.setAttribute(identifier + ".distance", Double.POSITIVE_INFINITY);
            }

        });
        gh.graph.nodes().forEach((n) -> {
            gh.graph.edges().forEach((e) -> {
                Node n0 = e.getNode0();
                Node n1 = e.getNode1();
                Double d0 = (Double)n0.getAttribute(identifier + ".distance");
                Double d1 = (Double)n1.getAttribute(identifier + ".distance");
                Double we = (Double)e.getAttribute("weight");

                if (we != null) {
                    if (d0 != null && (d1 == null || d1 >= d0 + we)) {
                        n1.setAttribute(identifier + ".distance", d0 + we);
                        ArrayList<Edge> predecessors = (ArrayList<Edge>)n1.getAttribute(identifier + ".predecessors");
                        if (d1 != null && d1 == d0 + we) {
                            if (predecessors == null) {
                                predecessors = new ArrayList<>();
                            }
                        } else {
                            predecessors = new ArrayList<>();
                        }

                        if (!predecessors.contains(e)) {
                            predecessors.add(e);
                        }

                        n1.setAttribute(identifier + ".predecessors", predecessors);
                    }
                }

            });
        });

        gh.graph.edges().forEach((e) -> {
            Node n0 = e.getNode0();
            Node n1 = e.getNode1();
            Double d0 = (Double)n0.getAttribute(identifier + ".distance");
            Double d1 = (Double)n1.getAttribute(identifier + ".distance");
            Double we = (Double)e.getAttribute("weight");

            if (d1 > d0 + we) {
                throw new NumberFormatException(String.format("Problema: peso negativo, ciclo detectado na aresta \"%s\"", e.getId()));
            }
        });
    }
}
