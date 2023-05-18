package com.jesuisjedi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        boolean tryAgain = true;
        Path filePath = null;
        GraphHandler gh = null;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {
            System.out.println("Digite H para ajuda");
            System.out.print("Digite um comando: ");
            String choice = reader.readLine();

            switch (choice) {
                case "L":
                    filePath = Main.loadFile(reader);
                    if(filePath != null) {
                        boolean loadSimpleGraph = true;

                        System.out.println("Carregar grafo simples? [S/n]");
                        String loadAsModelChoice = reader.readLine();

                        loadSimpleGraph = (!loadAsModelChoice.equalsIgnoreCase("N"));

                        gh = GraphParser.ParseFile(filePath, loadSimpleGraph);
                        System.out.println(filePath);
                    }
                    break;
                case "S":
                    if((gh.graph != null || gh.simpleGraph != null)) {
                        if(gh.viewer != null) {
                            gh.viewer.close();
                        }
                        gh.graph.setAttribute("ui.quality");
                        gh.graph.setAttribute("ui.antialias");
                        gh.graph.setAttribute("ui.stylesheet", "url(data/style.css);");

                        gh.viewer = gh.graph.display();
                        gh.viewer.enableAutoLayout();
                    }
                    break;
                case "BP":
                    if((gh.graph != null || gh.simpleGraph != null)) {
                        Main.depthFirstSearch(reader, gh);
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "BL":
                    if((gh.graph != null || gh.simpleGraph != null)) {
                        Main.breadthFirstSearch(reader, gh);
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "BF":
                    if((gh.graph != null || gh.simpleGraph != null)) {
                        Main.bellmanFordSearch(reader, gh);
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "D":
                    if(gh != null && (gh.graph != null || gh.simpleGraph != null)) {
                        Main.dijkstraSearch(reader, gh);
                    }
                    break;
                case "K":
                    if((gh.graph != null || gh.simpleGraph != null)) {
                        Main.kruskalTree(reader, gh);
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "P":
                    if((gh.graph != null || gh.simpleGraph != null)) {
                        Main.primTree(reader, gh);
                    }
                    break;
                case "FW":
                    if(gh != null && (gh.graph != null || gh.simpleGraph != null)) {
                        Main.floydWarshallTree(reader, gh);
                    }
                    break;
                case "H":
                    System.out.println("Ajuda:");
                    System.out.println("(L) Carregar arquivo");
                    System.out.println("(S) Mostrar grafo");
                    System.out.println("(BP) Busca em Profundidade");
                    System.out.println("(BL) Busca em Largura");
                    System.out.println("(BF) Bellman-Ford");
                    System.out.println("(D) Dijkstra");
                    System.out.println("(K) Kruskal");
                    System.out.println("(P) Prim");
                    System.out.println("(FW) Floyd-Warshall");
                    System.out.println("(Q) Sair");
                    System.out.println("(H) Ajuda");
                    break;
                case "Q":
                    System.out.println("Sair");
                    tryAgain = false;
                    break;
                default:
                    System.out.println("Comando invalido!");
                    System.out.println("Digite H para ajuda ou Q para sair do programa.");
                    break;
            }
        } while (tryAgain);

        System.exit(0);
    }

    private static void dijkstraSearch(BufferedReader reader, GraphHandler gh) {
        String source = null;

        try {
            System.out.print("Insira o nome do nó de origem da busca: ");
            source = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(gh.isSimpleGraph) {
            GraphAlgorithms.Dijsktra(gh, source);
        } else {
            gh.clearClasses();
            DijkstraAnimator.animate(gh, source);
        }
    }

    private static void floydWarshallTree(BufferedReader reader, GraphHandler gh) {
        if(gh.isSimpleGraph) {
            GraphAlgorithms.FloydWarshall(gh);
        } else {
            gh.clearClasses();
            // FloydWarshallAnimator.animate();
        }
    }

    private static void primTree(BufferedReader reader, GraphHandler gh) {
        if(!gh.isSimpleGraph) {
            gh.clearClasses();
            PrimAnimator.animate(gh);
        } else {
            GraphAlgorithms.Prim(gh);
        }
    }

    private static void kruskalTree(BufferedReader reader, GraphHandler gh) {
        if(!gh.isSimpleGraph) {
            gh.clearClasses();
            KruskalSearchAnimator.animate(gh);
        }
    }

    private static void bellmanFordSearch(BufferedReader reader, GraphHandler gh) {
        String source = null;

        try {
            System.out.print("Insira o nome do nó de origem da busca: ");
            source = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!gh.isSimpleGraph) {
            gh.clearClasses();
            BellmanFordSearchAnimator.animate(gh, source);
        } else {
            GraphAlgorithms.BellmanFord(gh, source);
        }
    }

    private static void breadthFirstSearch(BufferedReader reader, GraphHandler gh) {
        String source = null;

        try {
            System.out.print("Insira o nome do no de origem da busca: ");
            source = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!gh.isSimpleGraph) {
            gh.clearClasses();
            BreadthFirstSearchAnimator.animate(gh, source);
        } else {
            GraphAlgorithms.BFS(gh, source);
        }
    }

    private static void depthFirstSearch(BufferedReader reader, GraphHandler gh) {
        String source = null;

        try {
            System.out.print("Insira o nome do no de origem da busca: ");
            source = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(!gh.isSimpleGraph) {
            gh.clearClasses();
            DepthFirstSearchAnimator.animate(gh, source);
        } else {
            GraphAlgorithms.DFS(gh, source);
        }
    }

    private static Path loadFile(BufferedReader reader) {
        boolean tryAgain = true;
        Path filePath = null;
        try {
            System.out.print("Digite o caminho do arquivo a ser analisado: ");

            filePath = Paths.get(reader.readLine());
            while(tryAgain && Files.notExists(filePath)) {
                System.out.println("Este caminho n�o existe.");
                System.out.println("Tentar novamente? [S/n]");

                String choice = reader.readLine();

                tryAgain = (!choice.equalsIgnoreCase("N"));

                if(tryAgain) {
                    System.out.print("Escreva o caminho do arquivo novamente: ");
                    filePath = Paths.get(reader.readLine());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath;
    }
}