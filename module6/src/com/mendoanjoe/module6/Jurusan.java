package com.mendoanjoe.module6;

import java.util.ArrayList;
import java.util.List;

public class Jurusan {
    private String kdJurusan;
    private String namaJurusan;
    private List<Mahasiswa> mahasiswaList = new ArrayList<>(); // loaded from all mahasiswa class to jurusan class

    public Jurusan(String kdJurusan, String namaJurusan) {
        this.kdJurusan = kdJurusan;
        this.namaJurusan = namaJurusan;
    }

    public void addMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaList.add(mahasiswa);
    }

    public void showData() {
        System.out.println("Kode Jurusan: " + this.kdJurusan);
        System.out.println("Nama Jurusan: " + this.namaJurusan);
        System.out.println("Daftar Mahasiswa: ");
        for (Mahasiswa mahasiswa: mahasiswaList) {
            System.out.println("Nama: " + mahasiswa.getNama() + "\t" + "Nim: " + mahasiswa.getNim());
        }
    }
}
