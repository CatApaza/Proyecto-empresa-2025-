package com.example.asistenciaapp.model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/registro")
    Call<Usuario> registrarUsuario(@Body Usuario usuario);

    @POST("/api/login")
    Call<Usuario> login(@Body Map<String, String> credentials);
}
