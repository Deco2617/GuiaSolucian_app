package com.example.guiasolucion_app;
// Singleton para almacenar el usuario que ha iniciado sesión
public class CurrentUser {
    private static CurrentUser instance;
    private Usuario usuario;

    private CurrentUser() {} // Constructor privado

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}