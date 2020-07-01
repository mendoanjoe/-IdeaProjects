package com.mendoanjoe;

public class Titik {
    private double x;
    private double y;

    public Titik(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void cetakTitik() {
        System.out.println("Titik x \t: " + x);
        System.out.println("Titik y \t: " + y);
    }

    public double hitungJarak(Titik t2) {
        double hasil = Math.sqrt(Math.pow((t2.x - this.x), 2) + Math.pow((t2.y - this.y), 2));
        return hasil;
    }
}
