package com.example.saltalafila;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activity_Carro extends AppCompatActivity {

    private List<pedidoUsuario> cartItems = new ArrayList<>();
    private TextView tvCounter;
    private int itemCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        itemCount = intent.getIntExtra("itemCount", 0);
        tvCounter = (TextView) findViewById(R.id.tvCounter);
        tvCounter.setText(String.valueOf(itemCount));
        tvCounter.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Agregar flecha de retroceso
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // Obtener la lista de productos agregados al carrito desde el intent (puedes cambiar esto según tu implementación)
        cartItems = getIntent().getParcelableArrayListExtra("cartItems");

        // Configurar el RecyclerView para mostrar la lista de productos en el carrito
        RecyclerView recyclerViewCart = findViewById(R.id.recyclerViewCart);
        CartAdapter cartAdapter = new CartAdapter(this, cartItems);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCart.setAdapter(cartAdapter);

        // Configurar el botón "Vaciar Carrito"
        Button btnVaciarCarrito = findViewById(R.id.btnVaciarCarrito);
        btnVaciarCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartItems.clear();
                cartAdapter.notifyDataSetChanged();
                itemCount = 0;
                tvCounter.setVisibility(View.GONE);
            }
        });

        // Configurar el botón "Continuar Compra"
        Button btnContinuarCompra = findViewById(R.id.btnContinuarCompra);
        btnContinuarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarDatosCarritoAlServidor();
            }
        });
    }

    private void enviarDatosCarritoAlServidor() {
        // URL de tu servidor PHP
        String url = "https://mokups.000webhostapp.com/php/subir_archivo.php";

        // Crear la solicitud HTTP usando Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Crear una solicitud de tipo StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta del servidor si es necesario
                        // Aquí puedes mostrar un mensaje de éxito o realizar alguna otra acción
                        Log.d("Response", response);
                        Toast.makeText(activity_Carro.this, "Pedido enviado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_Carro.this, activity_Usuario.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error en caso de que la solicitud falle
                        Log.e("VolleyError", error.toString());
                        Toast.makeText(activity_Carro.this, "Error al enviar el pedido", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Crear un mapa con los datos del carrito en formato clave-valor
                Map<String, String> params = new HashMap<>();
                for (pedidoUsuario item : cartItems) {
                    params.put("usuario", "usuario"); // Cambiar "usuario" por el valor real del usuario
                    params.put("item", item.getName());
                    params.put("cantidad", String.valueOf(item.getQuantity())); // Obtener la cantidad actual
                    params.put("total", String.valueOf(item.getSubtotal()));
                    params.put("estado", "pagado");
                }

                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes
        requestQueue.add(stringRequest);
    }
}

