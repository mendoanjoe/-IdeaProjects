package com.mendoanjoe.module6;

public class Mouse {
    private String merek;
    private int dpi;

    public Mouse(String merek, int dpi) {
        this.merek = merek;
        this.dpi = dpi;
    }

    public void showData() {
        System.out.println("Merek: " + this.merek + ", DPI: " + this.dpi);
    }
}
