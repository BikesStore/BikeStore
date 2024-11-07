package com.mycompany.models;

public class Ventas {

    private int id_Venta;
    private float fecha;
    private double total;

    public int getId_Venta() {
        return id_Venta;
    }

    public void setId_Venta(int id_Venta) {
        this.id_Venta = id_Venta;
    }

    public float getFecha() {
        return fecha;
    }

    public void setFecha(float fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
