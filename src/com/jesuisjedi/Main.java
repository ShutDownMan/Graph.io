package com.jesuisjedi;

import org.graphstream.graph.Graph;

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
                        gh = GraphParser.ParseFile(filePath);
                        System.out.println(filePath);
                    }
                    break;
                case "S":
                    if(gh.graph != null) {
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
                    if(gh.graph != null) {
                        Main.depthFirstSearch(reader, gh);
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "BL":
                    if(gh.graph != null) {
                        Main.breadthFirstSearch(reader, gh);
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "BF":
                    if(gh.graph != null) {
                        Main.bellmanFordSearch(reader, gh);
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "K":
                    if(gh.graph != null) {
                    } else {
                        System.out.println("Carregue um arquivo valido antes de executar este comando.");
                    }
                    break;
                case "P":
                    if(gh.graph != null) {
                    }
                    break;
                case "H":
                    System.out.println("Ajuda:");
                    System.out.println("(L) Carregar arquivo");
                    System.out.println("(S) Mostrar grafo");
                    System.out.println("(BP) Busca em Profundidade");
                    System.out.println("(BL) Busca em Largura");
                    System.out.println("(BF) Bellman-Ford");
                    System.out.println("(K) Kruskal");
                    System.out.println("(P) Prim");
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

    private static void bellmanFordSearch(BufferedReader reader, GraphHandler gh) {
        String source = null;

        try {
            System.out.print("Insira o nome do no de origem da busca: ");
            source = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BellmanFordSearchAnimator.animate(gh, source);
    }

    private static void breadthFirstSearch(BufferedReader reader, GraphHandler gh) {
        String source = null;

        try {
            System.out.print("Insira o nome do no de origem da busca: ");
            source = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BreadthFirstSearchAnimator.animate(gh, source);
    }

    private static void depthFirstSearch(BufferedReader reader, GraphHandler gh) {
        String source = null;

        try {
            System.out.print("Insira o nome do no de origem da busca: ");
            source = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DepthFirstSearchAnimator.animate(gh, source);
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