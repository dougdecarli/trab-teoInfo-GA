package com.trabgateoria.codings.golomb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.stream.Stream;

public class ManipulacaoArquivo {


    private String diretorio;


    public ManipulacaoArquivo(String diretorio) {
        this.diretorio = diretorio;
    }


    public ManipulacaoArquivo() {
    }

    //Faz a leitura de um arquivo de texto comum
    public String leArquivo() throws IOException {
        Path diretorioAbsoluto = Paths.get(new File(this.diretorio).getAbsolutePath());
        Stream<String> linhasArquivo = Files.lines(diretorioAbsoluto);
        String dadosArquivo = "";
        for (Object linha : linhasArquivo.toArray()) {
            dadosArquivo += linha.toString();
        }
        linhasArquivo.close();
        return dadosArquivo;
    }

    //Faz a leitura de um arquivo codificado em Golumb
    public BitSet leArquivoCodificado() throws IOException {
        File arquivo = new File(this.diretorio);
        byte[] binario = Files.readAllBytes(Paths.get(arquivo.getAbsolutePath()));
        BitSet bites = BitSet.valueOf(binario);
        return bites;
    }

    //Escreve um BitSet codificado em Golumb
    public void escreveArquivo(BitSet bites) throws IOException {
        File outFile = new File(diretorio);
        FileOutputStream fos = new FileOutputStream(outFile);
        fos.write(bites.toByteArray());
        fos.close();
    }
    
    public void diretorioParaEncodeFiles() {
        new File("C:" + File.separator + "Golomb").mkdir();
    }
}
