package com.example.saltalafila;

import java.util.List;

public class PedidoUsuarioGroup {
    private String nombreUsuario;
    private List<pedidoUsuario> pedidos;
    private String estadoPedido;
    private int idPedido;

    public PedidoUsuarioGroup(String nombreUsuario, List<pedidoUsuario> pedidos) {
        this.nombreUsuario = nombreUsuario;
        this.pedidos = pedidos;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public String getEstado() {
        return estadoPedido;
    }


    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public List<pedidoUsuario> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<pedidoUsuario> pedidos) {
        this.pedidos = pedidos;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
}
