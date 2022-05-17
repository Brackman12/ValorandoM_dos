package com.example.valorandome.model;

public class Contenido {
    private final String links;
    private final String lecturas;
    private final String examenes;
    private final String practicas;
    private final Integer contador;

    public Contenido(String links, String lecturas, String examenes, String practicas, Integer contador) {
        this.links = links;
        this.lecturas = lecturas;
        this.examenes = examenes;
        this.practicas = practicas;
        this.contador = contador;
    }

    public String getLinks() {
        return links;
    }

    public String getLecturas() {
        return lecturas;
    }

    public String getExamenes() {
        return examenes;
    }

    public String getPracticas() {
        return practicas;
    }

    public Integer getContador() {
        return contador;
    }
}
