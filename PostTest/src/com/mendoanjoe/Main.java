package com.mendoanjoe;

public class Main {
    public static void main(String[] args) {
        System.out.println(" == Data Titik == ");
        Titik P = new Titik(5, 5);
        Titik A = new Titik(9, 8);
        P.cetakTitik();
        A.cetakTitik();
        System.out.println(" ");
        System.out.println(" == Data Segilima == ");
        Segilima segilima1 = new Segilima(P, A);
        segilima1.cetakSegilima();
        System.out.println(" ");
        System.out.println(" == Data Prisma == ");
        PrismaSegilima prisma1 = new PrismaSegilima(segilima1, 10);
        prisma1.cetakPrisma();
        System.out.println(" ");
        System.out.println(" == Data Limas == ");
        LimasSegilima limas1 = new LimasSegilima(segilima1, 10);
        limas1.cetakLimas();
        System.out.println(" ");
        System.out.println(" == Data Bola == ");
        Bola bola1 = new Bola(P, A);
        bola1.cetakBola();
    }
}
