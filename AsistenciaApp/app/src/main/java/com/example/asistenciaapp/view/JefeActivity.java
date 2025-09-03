package com.example.asistenciaapp.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asistenciaapp.R;
import com.example.asistenciaapp.model.Asistencia;
import com.example.asistenciaapp.api.ApiClient;
import com.example.asistenciaapp.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JefeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AsistenciaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jefe);

        recyclerView = findViewById(R.id.recyclerAsistencias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarAsistencias();
    }

    private void cargarAsistencias() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.getAsistencias().enqueue(new Callback<List<Asistencia>>() {
            @Override
            public void onResponse(Call<List<Asistencia>> call, Response<List<Asistencia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Asistencia> lista = response.body();

                    if (lista.isEmpty()) {
                        Toast.makeText(JefeActivity.this, "No hay asistencias registradas", Toast.LENGTH_SHORT).show();
                    }

                    adapter = new AsistenciaAdapter(lista);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(JefeActivity.this, "⚠ Error al obtener datos del servidor", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Respuesta vacía o incorrecta: " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<Asistencia>> call, Throwable t) {
                Log.e("API_ERROR", "Fallo en la API", t);
                Toast.makeText(JefeActivity.this, "❌ Fallo en la conexión con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
