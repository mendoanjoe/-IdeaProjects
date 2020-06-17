package com.mendoanjoe.module6;

public class Monitor {
    private String merek;
    private String ukuran;

    public Monitor(String merek, String ukuran) {
        this.merek = merek;
        this.ukuran = ukuran;
    }

    public void showData() {
        System.out.println("Merek: " + this.merek + ", Ukuran: " + this.ukuran);
    }
}
