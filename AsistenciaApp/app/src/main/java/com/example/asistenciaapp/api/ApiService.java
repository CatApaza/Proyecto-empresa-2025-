package com.example.asistenciaapp.api;

import com.example.asistenciaapp.model.LoginResponse;
import com.example.asistenciaapp.model.Usuario;
import com.example.asistenciaapp.model.Asistencia; // ✅ tu modelo Asistencia

import java.util.List; // ✅ faltaba este import

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/registro")
    Call<Void> registrarUsuario(@Body Usuario usuario);

    @POST("/api/login")
    Call<LoginResponse> login(@Body Usuario usuario);

    @GET("/api/asistencias")
    Call<List<Asistencia>> getAsistencias(); // ✅ ahora sí reconoce List
}
