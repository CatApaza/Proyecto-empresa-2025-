package com.example.asistenciaapp;



import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {

    EditText etNombre, etCorreo, etContrasena, etRol, etCodigoJefe;
    Button btnRegistrar;

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

        btnRegistrar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String correo = etCorreo.getText().toString();
            String contrasena = etContrasena.getText().toString();
            String rol = etRol.getText().toString().toLowerCase();
            String codigoJefe = etCodigoJefe.getText().toString();

            if (rol.equals("jefe")) {
                if (codigoJefe.equals("ECOLIM2025")) {
                    Toast.makeText(this, "Registrado como jefe", Toast.LENGTH_SHORT).show();
                    finish(); // volver al login
                } else {
                    Toast.makeText(this, "Código de jefe incorrecto", Toast.LENGTH_SHORT).show();
                }
            } else if (rol.equals("trabajador")) {
                Toast.makeText(this, "Registrado como trabajador", Toast.LENGTH_SHORT).show();
                finish(); // volver al login
            } else {
                Toast.makeText(this, "Rol inválido. Usa 'jefe' o 'trabajador'", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
