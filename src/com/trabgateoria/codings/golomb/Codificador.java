package com.trabgateoria.codings.golomb;

import java.util.ArrayList;
import java.util.BitSet;

public class Codificador extends Divisor {


    public Codificador() {
    }
    //Converte um array de inteiros (ASCII) para a Columb

    public BitSet inteiroParaColomb(ArrayList<Integer> decimal) {


        BitSet codigo = new BitSet();
        int posicao = 0;


        ArrayList<BitSet> ALBT = new ArrayList<BitSet>();
        int divisor = getDivisor();
        for (int i = 0; i < decimal.size(); i++) {

            int prefixo = decimal.get(i) / divisor;
            int sufixo = decimal.get(i) % divisor;
            int tamanhoBinario = prefixo + 4;


            codigo.set(posicao + 3);


            if (sufixo == 1 || sufixo == 3) {
                codigo.set(posicao + 1);
            }
            if (sufixo == 2 || sufixo == 3) {
                codigo.set(posicao + 2);
            }


            BitSet temp = new BitSet();
            temp.set(tamanhoBinario - 1);
            temp.set(3);


            if (sufixo == 1 || sufixo == 3) {
                temp.set(1);
            }
            if (sufixo == 2 || sufixo == 3) {
                temp.set(2);
            }
            ALBT.add(temp);
            posicao += tamanhoBinario - 1;

        }

        codigo.set(posicao);


        Bitset MB = new Bitset();


        for (int i = 0; i < ALBT.size(); i++) {
            int ASCII = decimal.get(i);
            BitSet temp = ALBT.get(i);


            char caractere = (char) ASCII;
            System.out.println("Foi lido o caractere " + caractere + " representado por " + decimal.get(i) );

        }

        return codigo;
    }

}
