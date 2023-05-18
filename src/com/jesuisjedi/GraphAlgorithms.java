package com.jesuisjedi;

import org.graphstream.graph.Node;
import org.graphstream.graph.Path;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class GraphAlgorithms {
    public static void DFS(GraphHandler gh, String source) {
        SimpleNode sourceNode = gh.simpleGraph.getNode(source);
        GraphAlgorithms.DFSRec(sourceNode);
    }

    private static void DFSRec(SimpleNode currentNode) {
        currentNode.tag = "tag1";
        System.out.print(currentNode.getId() + "-");

        currentNode.leavingEdges.forEach(edge -> {
            SimpleNode nextNode = (edge.getTargetNode().getId().equals(currentNode.getId())) ? edge.getSourceNode() : edge.getTargetNode();
            if(nextNode.tag == null) {
                edge.tag = "tag1";
                GraphAlgorithms.DFSRec(nextNode);
                edge.tag = "tag2";
            }
        });

        currentNode.tag = "tag2";
    }

    public static void BFS(GraphHandler gh, String sourceNode) {
        Queue<SimpleNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(gh.simpleGraph.getNode(sourceNode));

        SimpleNode currentNode = null;
        while (nodeQueue.size() > 0) {
            currentNode = nodeQueue.peek();
            nodeQueue.remove();

            System.out.print(currentNode.getId() + "-");

            currentNode.tag = "tag1";

            SimpleNode finalCurrentNode = currentNode;
            currentNode.leavingEdges.forEach(edge -> {
                SimpleNode nextNode = (edge.getTargetNode().getId().equals(finalCurrentNode.getId())) ? edge.getSourceNode() : edge.getTargetNode();
                if (nextNode.tag == null) {
                    nextNode.tag = "tag1";

                    nodeQueue.add(nextNode);
                }
                edge.tag = "tag1";
            });

            currentNode.tag = "tag2";
        }
    }


    public static void BellmanFord(GraphHandler gh, String sourceNode) {
        System.out.print("origem:");
        System.out.println(sourceNode);

        prepareGraphBF(gh, gh.simpleGraph.getNode(sourceNode));
        gh.simpleGraph.nodes.forEach(currentNode -> {
            gh.clearClasses();
            SimplePath shortestPath = null;
            try {
                shortestPath = getShortestPathBF(gh.simpleGraph.getNode(sourceNode), currentNode);

                System.out.print(" destino: ");
                System.out.print(currentNode.getId());
                System.out.print(" dist.: ");
                System.out.print(shortestPath.getPathWeight());
                System.out.print(" caminho: ");
                System.out.println(shortestPath);

                Thread.sleep(1000);
                gh.clearClasses();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static SimplePath getShortestPathBF(SimpleNode source, SimpleNode target) throws InterruptedException {
        SimplePath path = new SimplePath();

        if (Objects.equals(target, source)) {
            return path;
        } else {
            boolean noPath = false;
            SimpleNode vertex = target;

            while (vertex != source && !noPath) {
                ArrayList<SimpleEdge> list = vertex.predecessors;
                if (list == null) {
                    noPath = true;
                } else {
                    SimpleEdge parentEdge = list.get(0);
                    path.add(vertex, parentEdge);

                    vertex.tag = "tag1";
                    vertex = parentEdge.getOpposite(vertex);
                }
            }

            return path;
        }
    }

    private static void prepareGraphBF(GraphHandler gh, SimpleNode source) {
        gh.simpleGraph.nodes.forEach((n) -> {
            if (Objects.equals(n, source)) {
                n.distance = 0.0;
            } else {
                n.distance = Double.POSITIVE_INFINITY;
            }
        });
        gh.simpleGraph.nodes.forEach((n) -> {
            gh.simpleGraph.edges.forEach((e) -> {
                SimpleNode n0 = e.getSourceNode();
                SimpleNode n1 = e.getTargetNode();
                double d0 = n0.distance;
                double d1 = n1.distance;
                double we = e.weight;

                if (d1 >= d0 + we) {
                    n1.distance = d0 + we;
                    ArrayList<SimpleEdge> predecessors = n1.predecessors;
                    if (d1 == d0 + we) {
                        if (predecessors == null) {
                            predecessors = new ArrayList<>();
                        }
                    } else {
                        predecessors = new ArrayList<>();
                    }

                    if (!predecessors.contains(e)) {
                        predecessors.add(e);
                    }

                    n1.predecessors = predecessors;
                }
            });
        });

        gh.simpleGraph.edges.forEach((e) -> {
            SimpleNode n0 = e.getSourceNode();
            SimpleNode n1 = e.getTargetNode();
            double d0 = n0.distance;
            double d1 = n1.distance;
            double we = e.weight;

            if (d1 > d0 + we) {
                throw new NumberFormatException(String.format("Problema: peso negativo, ciclo detectado na aresta \"%s\"", e.getId()));
            }
        });
    }


    public static void Prim(GraphHandler gh) {
//        LinkedList<SimpleEdge> treeEdges = new LinkedList<>();
//
//        int n = gh.graph.getNodeCount();
//        PrimAnimator.Data[] data = new PrimAnimator.Data[n];
//        FibonacciHeap<Double, SimpleNode> heap = new FibonacciHeap<>();
//
//        for(int i = 0; i < n; ++i) {
//            data[i] = new PrimAnimator.Data();
//            data[i].edgeToTree = null;
//            data[i].fn = heap.add(Double.POSITIVE_INFINITY, gh.simpleGraph.getNode(i));
//        }
//
//        double treeWeight = 0.0;
//        while(!heap.isEmpty()) {
//            SimpleNode u = heap.extractMin();
//            PrimAnimator.Data dataU = data[u.getIndex()];
//            data[u.getIndex()] = null;
//            if (dataU.edgeToTree != null) {
//                treeEdges.add(dataU.edgeToTree);
//                dataU.edgeToTree.setAttribute("ui.class", "tag1");
//                treeWeight += dataU.fn.getKey();
//                dataU.edgeToTree = null;
//            }
//
//            dataU.fn = null;
//            u.edges().filter((e) -> data[e.getOpposite(u).getIndex()] != null).forEach((e) -> {
//                SimpleNode v = e.getOpposite(u);
//                PrimAnimator.Data dataV = data[v.getIndex()];
//                double w = getWeight(e);
//                if (w < dataV.fn.getKey()) {
//                    heap.decreaseKey(dataV.fn, w);
//                    dataV.edgeToTree = e;
//                }
//            });
//        }
//
//        System.out.println(treeEdges);
//        System.out.println(treeWeight);
    }

    public static void Dijsktra(GraphHandler gh, String sourceNode) {
        System.out.print("origem:");
        System.out.println(sourceNode);

        prepareGraphDijkstra(gh, gh.simpleGraph.getNode(sourceNode));
        gh.simpleGraph.nodes.forEach(currentNode -> {
            gh.clearClasses();
            SimplePath shortestPath = null;
            try {
                shortestPath = getShortestPathDijkstra(gh.simpleGraph.getNode(sourceNode), currentNode);

                System.out.print(" destino: ");
                System.out.print(currentNode.getId());
                System.out.print(" dist.: ");
                System.out.print(shortestPath.getPathWeight());
                System.out.print(" caminho: ");
                System.out.print(shortestPath);
                System.out.println(" <- " + sourceNode);

                Thread.sleep(1000);
                gh.clearClasses();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static SimplePath getShortestPathDijkstra(SimpleNode source, SimpleNode target) throws InterruptedException {
        SimplePath path = new SimplePath();

        if (Objects.equals(target, source)) {
            return path;
        } else {
            boolean noPath = false;
            SimpleNode vertex = target;

            while (vertex != source && !noPath) {
                ArrayList<SimpleEdge> list = vertex.predecessors;
                if (list == null) {
                    noPath = true;
                } else {
                    SimpleEdge parentEdge = list.get(0);
                    path.add(vertex, parentEdge);

                    vertex.tag = "tag1";
                    vertex = parentEdge.getOpposite(vertex);
                }
            }

            if (noPath) {
                System.out.println("No path found");
            }

            return path;
        }
    }

    private static void prepareGraphDijkstra(GraphHandler gh, SimpleNode source) {
        gh.simpleGraph.nodes.forEach((n) -> {
            if (Objects.equals(n, source)) {
                n.distance = 0.0;
            } else {
                n.distance = Double.POSITIVE_INFINITY;
            }
        });
        gh.simpleGraph.nodes.forEach((n) -> {
            gh.simpleGraph.edges.forEach((e) -> {
                SimpleNode n0 = e.getSourceNode();
                SimpleNode n1 = e.getTargetNode();
                double d0 = n0.distance;
                double d1 = n1.distance;
                double we = e.weight;

                if (d1 >= d0 + we) {
                    n1.distance = d0 + we;
                    ArrayList<SimpleEdge> predecessors = n1.predecessors;
                    if (d1 == d0 + we) {
                        if (predecessors == null) {
                            predecessors = new ArrayList<>();
                        }
                    } else {
                        predecessors = new ArrayList<>();
                    }

                    if (!predecessors.contains(e)) {
                        predecessors.add(e);
                    }

                    n1.predecessors = predecessors;
                }
            });
        });

        gh.simpleGraph.edges.forEach((e) -> {
            SimpleNode n0 = e.getSourceNode();
            SimpleNode n1 = e.getTargetNode();
            double d0 = n0.distance;
            double d1 = n1.distance;
            double we = e.weight;

            if (d1 > d0 + we) {
                throw new NumberFormatException(String.format("Problema: peso negativo, ciclo detectado na aresta \"%s\"", e.getId()));
            }
        });
    }

    public static void FloydWarshall(GraphHandler gh) {
        // compute all pairs shortest paths
        int n = gh.simpleGraph.nodes.size();
        double[][] dist = new double[n][n];
        int[][] next = new int[n][n];

        for(int i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                dist[i][j] = Double.POSITIVE_INFINITY;
                next[i][j] = -1;
            }
        }

        gh.simpleGraph.edges.forEach((e) -> {
            SimpleNode n0 = e.getSourceNode();
            SimpleNode n1 = e.getTargetNode();
            int i = n0.getIndex();
            int j = n1.getIndex();
            double we = e.weight;
            if (we < dist[i][j]) {
                dist[i][j] = we;
                next[i][j] = j;
            }
        });

        for(int k = 0; k < n; ++k) {
            for(int i = 0; i < n; ++i) {
                if (dist[i][k] < Double.POSITIVE_INFINITY) {
                    for(int j = 0; j < n; ++j) {
                        if (dist[k][j] < Double.POSITIVE_INFINITY && dist[i][j] > dist[i][k] + dist[k][j]) {
                            dist[i][j] = dist[i][k] + dist[k][j];
                            next[i][j] = next[i][k];
                        }
                    }
                }
            }
        }

        gh.simpleGraph.nodes.forEach((n0) -> {
            gh.simpleGraph.nodes.forEach((n1) -> {
                if (n0 != n1) {
                    int i = n0.getIndex();
                    int j = n1.getIndex();
                    if (dist[i][j] < Double.POSITIVE_INFINITY) {
                        System.out.print("origem: ");
                        System.out.print(n0.getId());
                        System.out.print(" destino: ");
                        System.out.print(n1.getId());
                        SimplePath path = getPath(gh, n0, n1, next);
                        if (path.nodes.size() == 1) {
                            System.out.print(" dist.: inf");
                            System.out.print(" caminho: ");
                            System.out.println("---");
                        } else {
                            System.out.print(" dist.: ");
                            System.out.print(dist[i][j]);
                            System.out.print(" caminho: ");
                            System.out.println(path);
                        }
                    }
                }
            });
        });
    }

    private static SimplePath getPath(GraphHandler gh, SimpleNode n0, SimpleNode n1, int[][] next) {
        SimplePath path = new SimplePath();
        int i = n0.getIndex();
        int j = n1.getIndex();
        if (next[i][j] == -1) {
            return path;
        } else {
            path.add(n0, gh.simpleGraph.getEdge(n0, gh.simpleGraph.getNode(String.valueOf(next[i][j]))));
            while (next[i][j] != j) {
                i = next[i][j];
                path.add(gh.simpleGraph.getNode(String.valueOf(i)), gh.simpleGraph.getEdge(gh.simpleGraph.getNode(String.valueOf(i)), gh.simpleGraph.getNode(String.valueOf(next[i][j]))));
            }

            return path;
        }
    }

}
