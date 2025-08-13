package com.example.asistenciaapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asistenciaapp.R;
import com.example.asistenciaapp.api.ApiService;
import com.example.asistenciaapp.api.RetrofitClien;
import com.example.asistenciaapp.model.LoginResponse;
import com.example.asistenciaapp.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etCorreo, etContrasena;
    Button btnLogin, btnIrRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etUser);
        etContrasena = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnIrRegistro = findViewById(R.id.btnIrRegistro);

        btnLogin.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUsuario(correo, contrasena);
        });

        btnIrRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    private void loginUsuario(String correo, String contrasena) {
        ApiService apiService = RetrofitClien.getRetrofitInstance().create(ApiService.class);

        Usuario usuarioLogin = new Usuario(correo, contrasena);

        Call<LoginResponse> call = apiService.login(usuarioLogin);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getUsuario() != null) {
                    Usuario usuario = response.body().getUsuario();
                    Toast.makeText(LoginActivity.this, "Bienvenido " + usuario.getNombre(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("usuario_nombre", usuario.getNombre());
                    intent.putExtra("usuario_rol", usuario.getRol());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
