/*
 * Copyright (c) 2020. Firmansyah Nuralif Rohman (18102050)
 */

package com.mendoanjoe.unguided;

public class SgtSamaSisi {
    private double sisiSGT;
    private Titik titik;
    private Titik titik2;

    /**
     * Sisi segitiga sama sisi = jarak 2 titik
     * inputan berupa titik1, dan titik 2
     */
    public SgtSamaSisi(double x1, double y1, double x2, double y2) {
        this.titik = new Titik(x1, y1);
        this.titik2 = new Titik(x2, y2);
        this.sisiSGT = titik.hitungJarak(titik2);
    }

    /**
     * rumus modul: 0.5 * sisiSGT * akar 3
     */
    public double hitungLuas() {
//        return 0.5 * sisiSGT * (sisiSGT * 0.5 * Math.sqrt(3)); // 1/2 * alas * tinggi (alas * 1/2 * akar3)
        return 0.5 * sisiSGT * Math.sqrt(3); // menyesuaikan module
    }

    /**
     * Menampilkan point 2 titik sebagai kelas induk dan
     * menampilkan sisi segitiga serta luas segitiga
     */
    public void tampil() {
        System.out.println("== DATA Segitiga ==");
        System.out.println("Point \t\t\t\t: ");
        titik.tampil();
        titik2.tampil();
        System.out.println("Sisi Segitiga \t\t: " + sisiSGT);
        System.out.println("Luas Segitiga \t\t: " + this.hitungLuas());
        System.out.println();
    }
}
