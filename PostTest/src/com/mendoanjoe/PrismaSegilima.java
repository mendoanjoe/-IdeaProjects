/*
 * Copyright (c) 2020. 18102050
 */

package com.mendoanjoe;

public class PrismaSegilima {
    private double tinggiPrisma;
    private Segilima segilima;

    public PrismaSegilima(Segilima segilima, int tinggiPrisma) {
        this.tinggiPrisma = tinggiPrisma;
        this.segilima = segilima;
    }

    public double volumePrisma() {
        return segilima.luasSegilima() * tinggiPrisma;
    }

    public double luasPrisma() {
        return (2 * segilima.luasSegilima()) + (segilima.kelilingSegilima() * tinggiPrisma);
    }

    public void cetakPrisma() {
        segilima.cetakSegilima();
        System.out.println("Volume Prisma \t\t: " + volumePrisma());
        System.out.println("Luas Prisma \t\t: " + luasPrisma());
        System.out.println();
    }
}
