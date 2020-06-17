package com.mendoanjoe.module6;

import java.util.ArrayList;
import java.util.List;

public class Dosen {
    private String kdDosen;
    private int jmlhMahasiswa = 0;
    private List<String> nimMahasiswa = new ArrayList<>();

    public String getKdDosen() {
        return kdDosen;
    }

    public void setKdDosen(String kdDosen) {
        this.kdDosen = kdDosen;
    }

    public int getJmlhMahasiswa() {
        return jmlhMahasiswa;
    }

    public void setJmlhMahasiswa(int jmlhMahasiswa) {
        this.jmlhMahasiswa = jmlhMahasiswa;
    }

    public String getNimMahasiswa(int index) {
        return this.nimMahasiswa.get(index);
    }

    public void addNimMahasiswa(String nimMahasiswa) {
        this.nimMahasiswa.add(nimMahasiswa);
    }
}
