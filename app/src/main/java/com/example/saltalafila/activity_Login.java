package com.example.saltalafila;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_Login extends AppCompatActivity {

    // Datos hardcodeados para demostración
    private static final String USUARIO_TIPO_A = "barra";
    private static final String USUARIO_TIPO_B = "usuario";
    private static final String CONTRASENA_TIPO_A = "barra";
    private static final String CONTRASENA_TIPO_B = "usuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Layout por defecto para el login

        // Evento del botón de inicio de sesión
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del usuario (nombre de usuario y contraseña)
                EditText editTextUsername = findViewById(R.id.editTextUsername);
                EditText editTextPassword = findViewById(R.id.editTextPassword);
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Verificar el tipo de usuario (hardcodeado para demostración)
                if (username.equals(USUARIO_TIPO_A) && password.equals(CONTRASENA_TIPO_A)) {
                    // Mostrar el layout del usuario tipo "A"
                    Intent intent = new Intent(activity_Login.this, activity_Barra.class);
                    startActivity(intent);
                } else if (username.equals(USUARIO_TIPO_B) && password.equals(CONTRASENA_TIPO_B)) {
                    // Mostrar el layout del usuario tipo "B"
                    Intent intent = new Intent(activity_Login.this, activity_Usuario.class);
                    startActivity(intent);
                } else {
                    // Mostrar un mensaje de error o alguna acción adicional si los datos son incorrectos
                    Toast.makeText(activity_Login.this, "Usuario/Contraseña Incorrecto", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
