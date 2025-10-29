package com.example.guiasolucion_app;

public class Usuario {
    private String nombre;
    private String cargo; // Fiscal Superior, Jefe de TI, etc.
    private String email;
    private String password;

    public Usuario(String nombre, String cargo, String email, String password) {
        this.nombre = nombre;
        this.cargo = cargo;
        this.email = email;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}