package com.mendoanjoe.module6;

import java.util.ArrayList;
import java.util.List;

public class DemoAgregasi {
    public static void main(String[] args) {
        Jurusan jurusan = new Jurusan("JRS001", "Computer Science");

        // Cara 1
        jurusan.addMahasiswa(new Mahasiswa("Mamang", "171"));

        // Cara 2
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        mahasiswaList.add(new Mahasiswa("Bejo", "172"));
        mahasiswaList.add(new Mahasiswa("Lukman", "173"));

        for (int i = 0; i < mahasiswaList.size(); i++) {
            jurusan.addMahasiswa(mahasiswaList.get(i));
        }

        jurusan.showData();
    }
}
