package com.jesuisjedi;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;

import java.io.Console;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("Demo2");
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.stylesheet", "url(/home/jedi/IdeaProjects/Graph.io/data/style.css);");

        graph.display(false);
        FileSource source = new FileSourceDGS();
        source.addSink( graph );
        source.begin("/home/jedi/IdeaProjects/Graph.io/data/demo2.dgs");

        while(source.nextStep()){
            System.out.println("TESTE!");
            Thread.sleep(2000);
        }
        source.end();
    }
}