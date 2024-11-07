package com.mycompany.models;

public class Servicios {

    private int id_Servicios;
    private String nom_Servicio;
    private double precio;

    public int getId_Servicios() {
        return id_Servicios;
    }

    public void setId_Servicios(int id_Servicios) {
        this.id_Servicios = id_Servicios;
    }

    public String getNom_Servicio() {
        return nom_Servicio;
    }

    public void setNom_Servicio(String nom_Servicio) {
        this.nom_Servicio = nom_Servicio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
