package com.company;

public class Pair {
    public int g1, g2;

    public Pair() {
        g1 = -1;
        g2 = -1;
    }

    public Pair(Pair p) {
        this.g1 = p.g1;
        this.g2 = p.g2;
    }
}
