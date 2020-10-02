package com.trabgateoria.codings.golomb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Scanner;

public class GolombCoding {

    static final String ARQUIVO_CODIFICADO = "C://Golomb//arqcodificado";
    static String ARQUIVO_NAO_CODIFICADO = "";

    public static void main(String[] args) throws IOException {
        Codificador cod = new Codificador();
        Decodificador decod = new Decodificador();

        BitSet arqcodificado;

        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe o divisor: ");

        int div = entrada.nextInt();
        cod.setDivisor(div);
        decod.setDivisor(div);

        entrada = new Scanner(System.in);
        System.out.println("Favor informar o caminho do arquivo que deve ser codificado: ");
        ARQUIVO_NAO_CODIFICADO = entrada.nextLine();

        //Ler arquivo do caminho informado

        ManipulacaoArquivo maniArq = new ManipulacaoArquivo(ARQUIVO_NAO_CODIFICADO);
        String dadosArquivo = maniArq.leArquivo();

        //Cria um array de char e o converte em um arrayList de inteiros

        char[] caracteres = dadosArquivo.toCharArray();
        ArrayList<Integer> caracteresDec = new ArrayList<Integer>(caracteres.length);

        for (int i = 0; i < caracteres.length; i++) {
            caracteresDec.add(0);
        }

        for (int i = 0; i < caracteres.length; i++) {
            caracteresDec.set(i, new Integer((int) caracteres[i]));
        }

        //Codifica um arquivo, insere os dados em "arqcodificado"
        //decodifica o arquivo apresenta informações em tela
        arqcodificado = cod.inteiroParaColomb(caracteresDec);
        decod.colombParaInteiro(arqcodificado);

        //Escreve um arquivo em golomb no caminho informado para o arquivo codificado
        ManipulacaoArquivo MA = new ManipulacaoArquivo(ARQUIVO_CODIFICADO);
        MA.diretorioParaEncodeFiles();
        MA.escreveArquivo(arqcodificado);
        System.out.println("Foi criado o arquivo codificado no seguinte local: " + ARQUIVO_CODIFICADO +"\n");

        //Faz leitura do arquivo que foi arqcodificado em Colomb, decodifica e exibe

        BitSet todosBinarios = MA.leArquivoCodificado();
        //decod.colombParaInteiro(todosBinarios);
    }
}
