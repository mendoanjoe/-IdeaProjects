/*
 * Copyright (c) 2020. Firmansyah Nuralif Rohman (18102050)
 */

package com.mendoanjoe.unguided;

public class Titik {
    private double x;
    private double y;

    /**
     * terdiri dari 2 point, yaitu x dan y
     */
    public Titik(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Output contoh: [1.0, 2.0]
     */
    public void tampil() {
        System.out.println("[" + x + ", " + y + "]");
    }

    /**
     * Rumus: akar dari (x2 - x1)^2 + (y2 - y1)^2
     */
    public double hitungJarak(Titik t2) {
        double hasil = Math.sqrt(Math.pow((t2.x - this.x), 2) + Math.pow((t2.y - this.y), 2));
        return hasil;
    }
}
