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

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etContrasena;
    private RadioButton rbTrabajador, rbJefe;
    private Button btnRegistrar;

    private static final String URL_REGISTRO = "http://10.0.2.2:3000/api/registro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        rbTrabajador = findViewById(R.id.rbTrabajador);
        rbJefe = findViewById(R.id.rbJefe);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        String rol = rbTrabajador.isChecked() ? "trabajador" :
                rbJefe.isChecked() ? "jefe" : "";

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject usuario = new JSONObject();
        try {
            usuario.put("nombre", nombre);
            usuario.put("correo", correo);
            usuario.put("contrasena", contrasena);
            usuario.put("rol", rol);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                URL_REGISTRO,
                usuario,
                response -> {
                    Toast.makeText(this, "Usuario registrado ✅", Toast.LENGTH_SHORT).show();
                    Log.d("Registro", "Respuesta: " + response.toString());
                    startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                    finish();
                },
                error -> {
                    Toast.makeText(this, "Error al registrar ❌", Toast.LENGTH_SHORT).show();
                    Log.e("Registro", "Error: " + error.toString());
                }
        );

        queue.add(request);
    }
}

