/*
 * Copyright (c) 2020. Firmansyah Nuralif Rohman (18102050)
 */

package com.mendoanjoe.unguided;

public class Persegi {
    private double sisiPSG;
    private Titik titik;
    private Titik titik2;

    public Persegi(double x1, double y1, double x2, double y2) {
        this.titik = new Titik(x1, y1);
        this.titik2 = new Titik(x2, y2);
        this.sisiPSG = titik.hitungJarak(titik2);
    }

    public double hitungLuas() {
        return sisiPSG * sisiPSG;
    }

    public void tampil() {
        System.out.println("== DATA Segitiga ==");
        System.out.println("Point \t\t\t\t: ");
        titik.tampil();
        titik2.tampil();
        System.out.println("Sisi Persegi \t\t: " + sisiPSG);
        System.out.println("Luas Persegi \t\t: " + hitungLuas());
        System.out.println();
    }
}
