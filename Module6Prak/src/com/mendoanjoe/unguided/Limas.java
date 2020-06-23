/*
 * Copyright (c) 2020. Firmansyah Nuralif Rohman (18102050)
 */

package com.mendoanjoe.unguided;

public class Limas {
    private static final int MAX_SEGITIGA_SAMA_SISI = 4;
    private SgtSamaSisi sgtSamaSisi;
    private Persegi persegi;

    public Limas(double sx1, double sy1, double sx2, double sy2, double px1, double py1, double px2, double py2) {
        this.sgtSamaSisi = new SgtSamaSisi(sx1, sy1, sx2, sy2);
        this.persegi = new Persegi(px1, py1, px2, py2);
    }

    public double hitungLuas() {
        return (MAX_SEGITIGA_SAMA_SISI * this.sgtSamaSisi.hitungLuas()) + this.persegi.hitungLuas();
    }

    public void tampil() {
        this.sgtSamaSisi.tampil();
        this.persegi.tampil();
        System.out.println("Luas Limas \t\t\t: " + this.hitungLuas());
    }
}