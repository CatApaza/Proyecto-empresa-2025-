package com.example.asistenciaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asistenciaapp.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private RadioButton rbTrabajador, rbJefe;
    private EditText etCodigo, etUser, etPassword;
    private Button btnLogin, btnRegister;

    private static final String CODIGO_JEFE = "12345"; // código fijo para jefe
    private static final String URL_LOGIN = "http://10.0.2.2:3000/api/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rbTrabajador = findViewById(R.id.rbTrabajador);
        rbJefe = findViewById(R.id.rbJefe);
        etCodigo = findViewById(R.id.etCodigo);
        etUser = findViewById(R.id.editUser);
        etPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Mostrar/ocultar campo código
        rbJefe.setOnClickListener(v -> {
            etCodigo.setText(""); // limpiar
            etCodigo.setVisibility(android.view.View.VISIBLE);
        });
        rbTrabajador.setOnClickListener(v -> {
            etCodigo.setText(""); // limpiar
            etCodigo.setVisibility(android.view.View.GONE);
        });

        // Botón Login
        btnLogin.setOnClickListener(v -> loginUsuario());

        // Botón Registrarse
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void loginUsuario() {
        String correo = etUser.getText().toString().trim();
        String contrasena = etPassword.getText().toString().trim();
        String rol = rbTrabajador.isChecked() ? "trabajador" :
                rbJefe.isChecked() ? "jefe" : "";

        if (correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rol.equals("jefe")) {
            String codigo = etCodigo.getText().toString().trim();
            if (codigo.isEmpty()) {
                Toast.makeText(this, "Ingresa el código de jefe", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!codigo.equals(CODIGO_JEFE)) {
                Toast.makeText(this, "❌ Código de jefe incorrecto", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // JSON para login
        JSONObject datos = new JSONObject();
        try {
            datos.put("correo", correo);
            datos.put("contrasena", contrasena);
            datos.put("rol", rol);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Petición al backend
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL_LOGIN,
                datos,
                response -> {
                    Log.d("Login", "Respuesta: " + response.toString());
                    Toast.makeText(this, "Login exitoso ✅", Toast.LENGTH_SHORT).show();

                    if (rol.equals("trabajador")) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, JefeActivity.class));
                    }
                },
                error -> {
                    String mensajeError = "Error en login ❌";
                    if (error.networkResponse != null && error.networkResponse.data != null) {
                        mensajeError += " - " + new String(error.networkResponse.data);
                    }
                    Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
                    Log.e("Login", "Error: " + error.toString());
                }
        );

        queue.add(request);
    }
}
