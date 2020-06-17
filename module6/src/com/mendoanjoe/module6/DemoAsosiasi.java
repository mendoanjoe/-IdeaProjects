package com.mendoanjoe.module6;

import java.util.ArrayList;
import java.util.List;

public class DemoAsosiasi {
     public static void main(String[] args) {
         List<Mahasiswa> mahasiswaList = new ArrayList<>();

         mahasiswaList.add(new Mahasiswa("Bambang", "171"));
         mahasiswaList.add(new Mahasiswa("Bejo", "172"));
         mahasiswaList.add(new Mahasiswa("Maman", "173"));
         mahasiswaList.add(new Mahasiswa("Joko", "174"));
         mahasiswaList.add(new Mahasiswa("Kirman", "175"));

         Dosen dosen = new Dosen();
         dosen.setKdDosen("DS001");

         for (int i = 0; i < mahasiswaList.size(); i++) {
             dosen.addNimMahasiswa(mahasiswaList.get(i).getNim());
         }

         String nim = dosen.getNimMahasiswa(1);
         for (int i = 0; i < mahasiswaList.size(); i++) {
             if (mahasiswaList.get(i).getNim().equals(nim)) {
                 System.out.println("Nama: " + mahasiswaList.get(i).getNama());
                 System.out.println("Nim: " + mahasiswaList.get(i).getNim());
             }
         }

         System.out.println("Kode dosen: " + dosen.getKdDosen());
         System.out.println("List mahasiswa: ");
         for (Mahasiswa mahasiswa: mahasiswaList) {
             System.out.println("Nama: " + mahasiswa.getNama());
             System.out.println("Nim: " + mahasiswa.getNim());
         }
     }
}
