package com.mendoanjoe;

public class Segilima {
    private double jariLingkaranLuar;

    public Segilima(Titik titik1, Titik titik2) {
        this.jariLingkaranLuar = titik1.hitungJarak(titik2);
    }

    public double sisiSegilima() {
        return ((10 * jariLingkaranLuar) / (Math.sqrt(50 + (10 * Math.sqrt(5)))));
    }

    public double jariLingkaranDalam() {
        return sisiSegilima() * ((Math.sqrt(25 + (10 * Math.sqrt(5)))) / 10);
    }

    public double diagonalSisi() {
        return ((1 + Math.sqrt(5)) / 2) * sisiSegilima();
    }

    public double kelilingLingkaran() {
        return (4 * Math.PI * jariLingkaranDalam()) + (4 * Math.PI * jariLingkaranLuar);
    }

    public double kelilingSegilima() {
        return 5 * sisiSegilima();
    }

    public double luasSegilima() {
        return (Math.pow(sisiSegilima(), 2) * Math.sqrt(25 + (10 * Math.sqrt(5)))) / 4;
    }

    public void cetakSegilima() {
        System.out.println("Panjang Sisi Segilima \t\t: " + sisiSegilima());
        System.out.println("Jari- Jari Lingkaran Dalam \t\t: " + jariLingkaranDalam());
        System.out.println("Panjang Diagonal \t\t:" + diagonalSisi());
        System.out.println("Keliling Lingkaran \t\t:" + kelilingLingkaran());
        System.out.println("Keliling Segilima \t\t:" + kelilingSegilima());
        System.out.println("Luas Segilima \t\t:" + luasSegilima());
        System.out.println();
    }
}
