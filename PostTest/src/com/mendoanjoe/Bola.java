package com.mendoanjoe;

public class Bola {
    private double jariBola;

    public Bola(Titik titik1, Titik titik2) {
        this.jariBola = titik1.hitungJarak(titik2);
    }

    public double volumeBola() {
        return 4/3 * Math.PI * jariBola * jariBola * jariBola;
    }

    public double luasBola() {
        return 4/3 * Math.PI * jariBola * jariBola;
    }

    public void cetakBola() {
        System.out.println("Volume Bola \t\t: " + volumeBola());
        System.out.println("Luas Bola \t\t: " + luasBola());
        System.out.println();
    }
}
