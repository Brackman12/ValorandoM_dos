package com.example.valorandome.model;

public class Semana {
    private final String descripcion;
    private final String valor;
    private final Integer contador;

    public Semana(String descripcion, String valor, Integer contador) {
        this.descripcion = descripcion;
        this.valor = valor;
        this.contador = contador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getValor() {
        return valor;
    }

    public Integer getContador() {
        return contador;
    }
}
