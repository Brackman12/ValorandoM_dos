package com.example.valorandome.model;

public class Usuario {

    private final String nombre;
    private final String correo;
    private final String quiensoy;
    private final String valores;


    public Usuario(String nombre, String correo, String quienSoy, String valores) {
        this.nombre = nombre;
        this.correo = correo;
        this.quiensoy = quienSoy;
        this.valores = valores;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getQuienSoy() {
        return quiensoy;
    }

    public String getValores() {
        return valores;
    }
}
