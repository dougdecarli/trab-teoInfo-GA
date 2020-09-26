package com.trabgateoria;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static File fileAlice29 = new File("src/com/trabgateoria/alice29.txt");
    static File fileSum = new File("path");

    public static void main(String[] args) throws IOException {

        while(true) {
            System.out.println("Digite 1 para codificar um arquivo \n" +
                                "Digite 2 para sair");

            int option = scanner.nextInt();

            if (option == 2) {
                System.out.println("Saindo...");
                exit(0);
            } else if (option == 1) {
                onEncodeOptionSelected();
            }
        }
    }

    static void onEncodeOptionSelected() throws IOException {
        File fileChosen = chooseFile();
        if ((fileChosen != null)) {
            runEncode(fileChosen);
        } else {
            return;
        }
    }

    static void runEncode(File fileChosen) throws IOException {
        System.out.println("Digite 1 para codificar em Golomb\n" +
                "Digite 2 para codificar em Elias-Gamma\n" +
                "Digite 3 para codificar em Fibonacci\n" +
                "Digite 4 para codificar em Unaria \n" +
                "Digite 5 para codificar em Delta");

        int codingType = scanner.nextInt();

        switch (codingType) {
            case 1:
                break;
            case 2:
                EliasGammaCoding eliasGammaCoding = new EliasGammaCoding(fileChosen);
                eliasGammaCoding.encode();
                eliasGammaCoding.decode();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
    }

    static File chooseFile() {
        System.out.println("Digite 1 para codificar o arquivo alice29.txt \n" +
                "Digite 2 para codificar o arquivo sum.txt");
        int fileToEncode = scanner.nextInt();

        switch (fileToEncode) {
            case 1:
                return fileAlice29;
            case 2:
                return fileSum;
            default:
                System.out.println("Opçao invalida!");
                return null;
        }
    }

}
