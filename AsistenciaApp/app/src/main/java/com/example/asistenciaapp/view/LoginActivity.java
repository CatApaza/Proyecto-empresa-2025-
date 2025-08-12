package com.example.asistenciaapp.view;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asistenciaapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText etCorreo, etContrasena;
    Button btnLogin, btnIrRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin = findViewById(R.id.btnLogin);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);

        btnLogin.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString();
            String contrasena = etContrasena.getText().toString();

            if (correo.equals("admin@empresa.com") && contrasena.equals("123456")) {
                Toast.makeText(this, "Bienvenido jefe", Toast.LENGTH_SHORT).show();
                // Luego ir a FacialRegistroActivity
            } else if (correo.equals("trabajador@empresa.com") && contrasena.equals("123456")) {
                Toast.makeText(this, "Bienvenido trabajador", Toast.LENGTH_SHORT).show();
                // Luego ir a FacialRegistroActivity
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        btnIrRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }
}
