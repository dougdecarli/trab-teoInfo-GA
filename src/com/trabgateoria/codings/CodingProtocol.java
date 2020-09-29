package com.trabgateoria.codings;

import java.io.IOException;
import java.util.ArrayList;

public interface CodingProtocol {
    //os métodos encode e decode devem retonar as Strings com os paths dos arquivos gerados em cada processo
    public String encode() throws IOException;
    public String decode() throws IOException;

    // Antes de fazer seu encode e adicionar o conteúdo do arquivo codificado a lista, chamar o método
    // para adicionar o cabeçalho conforme enunciado do trabalho.
    // Exemplo para codificação Unária:
    // encodedFileBytes.add((byte) 3);
    // encodedFileBytes.add((byte) 0);
    public void addHeader(ArrayList<Byte> encodedFileBytes);
}
