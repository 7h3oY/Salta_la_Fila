package com.example.saltalafila;
import android.view.LayoutInflater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {

    private List<PedidoUsuarioGroup> pedidosGroupList;

    public PedidosAdapter(List<PedidoUsuarioGroup> pedidosGroupList) {
        this.pedidosGroupList = pedidosGroupList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PedidoUsuarioGroup pedidoGroup = pedidosGroupList.get(position);
        holder.bind(pedidoGroup);
    }

    @Override
    public int getItemCount() {
        return pedidosGroupList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPedidoId;
        TextView textViewEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPedidoId = itemView.findViewById(R.id.textViewPedidoId);
            textViewEstado = itemView.findViewById(R.id.textViewEstado);
        }

        public void bind(PedidoUsuarioGroup pedidoGroup) {
            // Aquí accedes a la lista de pedidos del grupo y muestras los detalles de cada pedido
            List<pedidoUsuario> pedidos = pedidoGroup.getPedidos();
            StringBuilder stringBuilder = new StringBuilder();
            for (pedidoUsuario pedido : pedidos) {
                stringBuilder.append("Producto: ").append(pedido.getNombreProducto()).append(", Cantidad: ").append(pedido.getCantidadProducto()).append(", Precio: ").append(pedido.getPrecioProducto()).append("\n");
            }
            textViewPedidoId.setText("ID Pedido: " + pedidoGroup.getIdPedido());
            textViewEstado.setText("Estado: " + pedidoGroup.getEstado() + "\n" + stringBuilder.toString());
        }
    }
}



