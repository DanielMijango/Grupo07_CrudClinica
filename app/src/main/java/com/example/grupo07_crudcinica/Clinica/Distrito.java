package com.example.grupo07_crudcinica.Clinica;

public class Distrito {
    private int id;
    private String nombre;

    public Distrito(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
}
