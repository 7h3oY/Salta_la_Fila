package com.example.saltalafila;

import android.os.Parcel;
import android.os.Parcelable;

public class pedidoUsuario implements Parcelable {
    private String name;
    private double price;
    private int quantity;

    public pedidoUsuario(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Métodos getter y setter para acceder a los atributos
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Método para calcular el subtotal del producto (precio * cantidad)
    public double getSubtotal() {
        return price * quantity;
    }

    // Implementación de Parcelable

    protected pedidoUsuario(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<pedidoUsuario> CREATOR = new Creator<pedidoUsuario>() {
        @Override
        public pedidoUsuario createFromParcel(Parcel in) {
            return new pedidoUsuario(in);
        }

        @Override
        public pedidoUsuario[] newArray(int size) {
            return new pedidoUsuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);
    }

    public void setIdPedido(int idPedido) {
    }

    public void setNombreUsuario(String nombreUsuario) {
    }

    public String getNombreProducto() {
        return null;
    }

    public String getCantidadProducto() {
        return null;
    }

    public String getPrecioProducto() {
        return null;
    }
}

