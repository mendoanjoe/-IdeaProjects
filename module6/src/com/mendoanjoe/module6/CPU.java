package com.mendoanjoe.module6;

public class CPU {
    private String merek;
    private int kecepatan;

    public CPU(String merek, int kecepatan) {
        this.merek = merek;
        this.kecepatan = kecepatan;
    }

    public void showData() {
        System.out.println("Merek: " + this.merek + ", Kecepatan: " + this.kecepatan);
    }
}
