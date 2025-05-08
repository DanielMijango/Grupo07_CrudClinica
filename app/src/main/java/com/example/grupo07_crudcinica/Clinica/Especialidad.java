package com.example.grupo07_crudcinica.Clinica;

public class Especialidad {
    private String id;
    private String nombre;

    public Especialidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}