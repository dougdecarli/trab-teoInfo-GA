package com.trabgateoria.codings.golomb;

import java.util.BitSet;

public class Bitset {

    //Convert um BitSet para um String
    public String bitSetParaString(BitSet bit) {
        String binario = "";
        for (int i = bit.length() - 1; i > 0; i--) {
            if (bit.get(i) == true) {
                binario += 1;
            } else {
                binario += 0;
            }
        }
        return binario;
    }
}
