package com.mendoanjoe.module6;

public class DemoKomposisi {
    public static void main(String[] args) {
        Komputer komputer = new Komputer(new CPU("Intel 9", 900), new Monitor("LG 19", "23"), new Mouse("Razer", 52000));
        komputer.showData();
    }
}
