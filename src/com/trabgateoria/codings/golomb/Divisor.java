package com.trabgateoria.codings.golomb;

public class Divisor {

    Integer b;

    public int getDivisor() {
        return b == null ? 4 : b;
    }

    public void setDivisor(Integer divisor) {
        this.b = divisor;
    }
}
