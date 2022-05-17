package com.example.valorandome.model;

public class Register {
    private final String nombre;
    private final String correo;
    private final String pass;

    public Register(String nombre, String correo, String pass) {
        this.nombre = nombre;
        this.correo = correo;
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPass() {
        return pass;
    }
}
