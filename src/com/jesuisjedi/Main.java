package com.jesuisjedi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.print("Digite o caminho do arquivo a ser analisado: ");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        Path filePath = Paths.get(reader.readLine());
        boolean tryAgain = true;
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

        System.out.println(filePath);


    }
}