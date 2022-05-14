package com.svarom.proyectofirebase;

public class Productos {
    String nombre;
    long precio;

    public Productos(){}

    public Productos(String nombre, long precio){
        this.nombre=nombre;
        this.precio=precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getPrecio() {
        return precio;
    }

    public void setPrecio(long precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {return nombre;}
}
