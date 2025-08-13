package com.example.asistenciaapp.api;

import com.example.asistenciaapp.model.LoginResponse;
import com.example.asistenciaapp.model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/registro")
    Call<Void> registrarUsuario(@Body Usuario usuario);

    @POST("/api/login")
    Call<LoginResponse> login(@Body Usuario usuario);
}
