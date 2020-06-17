package com.mendoanjoe.module6;

public class Komputer {
    private CPU cpu;
    private Monitor monitor;
    private  Mouse mouse;

    public Komputer(CPU cpu, Monitor monitor, Mouse mouse) {
        this.cpu = cpu;
        this.monitor = monitor;
        this.mouse = mouse;
    }

    public void showData() {
        System.out.println("Komputer ini dibangun dengan:");
        System.out.println("CPU:");
        this.cpu.showData();
        System.out.println("Monitor:");
        this.monitor.showData();
        System.out.println("Mouse:");
        this.mouse.showData();
    }
}
