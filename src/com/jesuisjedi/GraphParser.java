package com.jesuisjedi;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphParser {
    public static GraphHandler ParseFile(Path filePath) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Graph.io");
        GraphHandler gh = new GraphHandler(graph);
        File file = new File(String.valueOf(filePath));

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    GraphParser.ParseLine(gh, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gh;
    }

    private static void ParseLine(GraphHandler gh, String line) {
        if(line.startsWith("orientado=")) {
            System.out.println("Orientado?");
            gh.isDirected = (Objects.equals(line.split("=")[1], "sim"));
            System.out.println(gh.isDirected);
        } else if(line.startsWith("V=")) {
            System.out.println("Numero de vertices?");
            gh.numOfVertices = Integer.parseUnsignedInt(line.split("=")[1]);
            System.out.println(gh.numOfVertices);
        } else if(line.startsWith("(")) {
            String pattern = "\\((?<source>\\d*),(?<destination>\\d*)\\):(?<weight>-?\\d*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(line);

            if (m.find()) {
                gh.addVertice(m.group("source"), m.group("destination"), m.group("weight"));
                System.out.println("Origem: " + m.group("source") );
                System.out.println("Destino: " + m.group("destination") );
                System.out.println("Peso: " + m.group("weight") );
            } else {
                System.out.println("Erro ao processar linha");
                System.out.println(line);
            }

        }

    }
}
