package com.example.valorandome.model;

public class Login {
    private final String nombre;
    private final String pass;

    public Login(String nombre, String pass) {
        this.nombre = nombre;
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() {
        return pass;
    }
}
