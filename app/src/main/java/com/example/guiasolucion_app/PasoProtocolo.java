package com.example.guiasolucion_app;

// Simplemente añade "public" aquí
public class PasoProtocolo {
    private String titulo;
    private String descripcion;

    public PasoProtocolo(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}