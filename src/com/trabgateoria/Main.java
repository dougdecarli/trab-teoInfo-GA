package com.trabgateoria;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static File fileAlice29 = new File("src/com/trabgateoria/alice29.txt");
    static File fileSum = new File("path");
    String pathEncodedFile;
    String pathDecodedFile;

    public static void main(String[] args) throws IOException {

        while(true) {
            System.out.println("Digite 1 para codificar um arquivo");
            System.out.println("Digite 2 para decodificar um arquivo");
            System.out.println("Digite 3 para sair");

            int option = scanner.nextInt();

            if (option == 3) {
                System.out.println("Saindo...");
                exit(0);
            } else if (option == 1) {
                //encode
                runEncode();

            } else if (option == 2) {
                //decode
            }
        }
    }

    static void runEncode() throws IOException {
        File fileChosen = new File("");
        System.out.println("Digite 1 para codificar o arquivo alice29.txt \n" +
                "Digite 2 para codificar o arquivo sum.txt");
        int fileToEncode = scanner.nextInt();

        switch (fileToEncode) {
            case 1:
                fileChosen = fileAlice29;
                break;
            case 2:
                fileChosen = fileSum;
                break;
        }

        System.out.println("Digite 1 para codificar em Golomb\n" +
                "Digite 2 para codificar em Elias-Gamma\n" +
                "Digite 3 para codificar em Fibonacci\n" +
                "Digite 4 para codificar em Unaria \n" +
                "Digite 5 para codificar em Delta");

        int codingType = scanner.nextInt();

        switch (codingType) {
            case 1:
                EliasGammaCoding eliasGammaCoding = new EliasGammaCoding(fileChosen);
                eliasGammaCoding.encode();
                break;
            case 2:
                return;
            case 3:
                return;
            case 4:
                return;
            case 5:
                return;
        }
    }

}
