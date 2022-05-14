package com.svarom.proyectofirebase;

public class Pedidos {
    String user,nombre_producto;
    long cantidad;

    public Pedidos(){}

    public Pedidos(String user, String nombre_producto, long cantidad) {
        this.user = user;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }
}
