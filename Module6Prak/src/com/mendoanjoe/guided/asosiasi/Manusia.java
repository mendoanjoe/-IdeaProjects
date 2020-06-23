/*
 * Copyright (c) 2020. Firmansyah Nuralif Rohman (18102050)
 */

package com.mendoanjoe.guided.asosiasi;

public class Manusia {
    private String nama;
    private int umur;
    private Manusia ibu;
    private Manusia anak;

    public Manusia() {

    }

    public Manusia(String nama, int umur) {
        this.nama = nama;
        this.umur = umur;
        ibu = new Manusia();
        anak = new Manusia();
        ibu = null;
        anak = null;
    }

    public Manusia (String nama, int umur, Manusia ibuAngkat) {
        this.nama = nama;
        this.umur = umur;
        ibu = new Manusia();
        anak = new Manusia();
        ibu = ibuAngkat;
        ibuAngkat.anak = this;
    }

    public void adopsi(Manusia anakAngkat) {
        anak = anakAngkat;
        anakAngkat.ibu = this;
    }

    public void cetak() {
        System.out.println("\n- Data Pribadi -");
        System.out.println("Nama : " + nama);
        System.out.println("Umur : " + umur);
        if (ibu != null)
            System.out.println("Nama ibu : " + ibu.nama);
        else if (anak != null)
            System.out.println("Nama anak : " + anak.nama);
    }
}
