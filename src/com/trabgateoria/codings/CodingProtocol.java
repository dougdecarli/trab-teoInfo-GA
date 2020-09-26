package com.trabgateoria.codings;

import java.io.IOException;

public interface CodingProtocol {
    //os métodos encode e decode devem retonar as Strings com os paths dos arquivos gerados em cada processo
    public String encode() throws IOException;
    public String decode() throws IOException;
}
