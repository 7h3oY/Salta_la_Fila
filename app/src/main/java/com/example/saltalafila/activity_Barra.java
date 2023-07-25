package com.example.saltalafila;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class activity_Barra extends AppCompatActivity {

    private List<PedidoUsuarioGroup> listaPedidos = new ArrayList<>();
    private PedidosAdapter pedidosAdapter;
    private RecyclerView recyclerViewPedidos;
    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barra);
        recyclerViewPedidos = findViewById(R.id.recyclerViewPedidos);

        // Configurar el RecyclerView con un LinearLayoutManager
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(this));

        // Crear el Adapter y asignarlo al RecyclerView
        pedidosAdapter = new PedidosAdapter(listaPedidos);
        recyclerViewPedidos.setAdapter(pedidosAdapter);

        // Programar la actualización automática cada 1 minuto
        runnable = new Runnable() {
            @Override
            public void run() {
                cargarListaPedidos();
                handler.postDelayed(this, 60000); // Programar la actualización cada 1 minuto (60000 milisegundos)
            }
        };
        handler.postDelayed(runnable, 60000); // Iniciar la actualización inmediatamente al iniciar la actividad
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener la actualización periódica al destruir la actividad
        handler.removeCallbacks(runnable);
    }

    private void cargarListaPedidos() {
        // Aquí deberías realizar una consulta a la base de datos para obtener los pedidos con estado "pagado"
        // y asignarlos a listaPedidos. Por ejemplo:

        // Supongamos que obtienes los datos de la base de datos y los almacenas en una lista de pedidos
        List<PedidoUsuarioGroup> pedidosDesdeBD = obtenerPedidosDesdeBD();

        // Luego, limpias la lista actual y agregas los pedidos obtenidos desde la base de datos
        listaPedidos.clear();
        listaPedidos.addAll(pedidosDesdeBD);

        // Finalmente, notificas al adaptador que los datos han cambiado para que se refresque la vista
        pedidosAdapter.notifyDataSetChanged();
    }

    private List<PedidoUsuarioGroup> obtenerPedidosDesdeBD() {
        // URL de tu servicio backend que obtiene los pedidos desde la base de datos
        String url = "https://mokups.000webhostapp.com/php/recupar.datos.php";
        List<PedidoUsuarioGroup> pedidos = new ArrayList<>();

        // Crear la solicitud HTTP usando Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Crear una solicitud de tipo JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Procesar la respuesta del servidor
                        // Aquí conviertes el JSONArray a una lista de objetos PedidoUsuarioGroup
                        pedidos.addAll(parsearPedidosDesdeJson(response));

                        // Notificar al adaptador que los datos han cambiado para que se refresque la vista
                        pedidosAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error en caso de que la solicitud falle
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(activity_Barra.this, "Error al obtener los pedidos desde la base de datos", Toast.LENGTH_SHORT).show();
                    }
                });

        // Agregar la solicitud a la cola de solicitudes
        requestQueue.add(jsonArrayRequest);

        return pedidos; // Devolver la lista de pedidos (aún estará vacía, pero se llenará cuando se reciba la respuesta del servidor)
    }

    private List<PedidoUsuarioGroup> parsearPedidosDesdeJson(JSONArray jsonArray) {
        List<PedidoUsuarioGroup> pedidos = new ArrayList<>();

        try {
            // Recorrer el JSONArray para obtener los datos de cada pedido
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPedido = jsonArray.getJSONObject(i);

                // Obtener los datos del pedido desde el objeto JSON
                int idPedido = jsonPedido.getInt("idPedido");
                String nombreUsuario = jsonPedido.getString("nombreUsuario");
                String nombreProducto = jsonPedido.getString("nombreProducto");
                int cantidadProducto = jsonPedido.getInt("cantidadProducto");
                double precioProducto = jsonPedido.getDouble("precioProducto");

                // Crear un objeto pedidoUsuario con los datos obtenidos
                pedidoUsuario pedido = new pedidoUsuario(nombreProducto, precioProducto, cantidadProducto);
                pedido.setIdPedido(idPedido);
                pedido.setNombreUsuario(nombreUsuario);

                // Verificar si ya existe un PedidoUsuarioGroup para el nombre de usuario
                PedidoUsuarioGroup pedidoGroup = null;
                for (PedidoUsuarioGroup group : pedidos) {
                    if (group.getNombreUsuario().equals(nombreUsuario)) {
                        pedidoGroup = group;
                        break;
                    }
                }

                // Si no existe un PedidoUsuarioGroup para el nombre de usuario, lo creamos
                if (pedidoGroup == null) {
                    pedidoGroup = new PedidoUsuarioGroup(nombreUsuario, new ArrayList<pedidoUsuario>());
                    pedidos.add(pedidoGroup);
                }

                // Agregar el pedidoUsuario al PedidoUsuarioGroup correspondiente
                pedidoGroup.getPedidos().add(pedido);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pedidos;
    }
}
