/*
 * Copyright (c) 2020. 18102050
 */

package com.mendoanjoe;

public class LimasSegilima {
    private double tinggiLimas;
    private Segilima segilima;

    public LimasSegilima(Segilima segilima, int tinggiLimas) {
        this.tinggiLimas = tinggiLimas;
        this.segilima = segilima;
    }

    public double tinggiMiring() {
        return Math.sqrt(Math.pow(tinggiLimas, 2) + Math.pow(segilima.sisiSegilima(), 2));
    }

    public double volumeLimas() {
        return 0.33 * segilima.luasSegilima() * tinggiLimas;
    }

    public double luasLimas() {
        return segilima.luasSegilima() + (5 * (0.5 * segilima.sisiSegilima() * tinggiMiring()));
    }

    public void cetakLimas() {
        segilima.cetakSegilima();
        System.out.println("Volume Limas \t\t: " + volumeLimas());
        System.out.println("Luas Limas \t\t: " + luasLimas());
        System.out.println();
    }
}
