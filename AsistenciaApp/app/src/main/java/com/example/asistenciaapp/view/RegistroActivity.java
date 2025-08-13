package com.example.asistenciaapp.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asistenciaapp.R;
import com.example.asistenciaapp.api.ApiService;
import com.example.asistenciaapp.api.RetrofitClien; // CORREGIDO
import com.example.asistenciaapp.model.Usuario;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etContrasena, etRol, etCodigoJefe;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etRol = findViewById(R.id.etRol);
        etCodigoJefe = findViewById(R.id.etCodigoJefe);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String rol = etRol.getText().toString().trim();
        String codigoJefe = etCodigoJefe.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol.isEmpty()) {
            Toast.makeText(this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ajustar según el constructor real de Usuario
        Usuario usuario = new Usuario(0, nombre, correo, contrasena, rol, codigoJefe);

        ApiService apiService = RetrofitClien.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.registrarUsuario(usuario);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegistroActivity.this, "Registro exitoso ✅", Toast.LENGTH_SHORT).show();
                    Log.i("API_SUCCESS", "Usuario registrado correctamente");
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
                        Log.e("API_ERROR", "Código: " + response.code() + " - " + errorBody);
                        Toast.makeText(RegistroActivity.this, "Error: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Error leyendo el cuerpo de la respuesta", e);
                        Toast.makeText(RegistroActivity.this, "Error desconocido al registrar", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_FAIL", "Fallo de conexión", t);
                Toast.makeText(RegistroActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
