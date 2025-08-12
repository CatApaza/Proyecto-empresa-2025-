package com.example.asistenciaapp.controller;

import com.example.asistenciaapp.model.ApiService;
import com.example.asistenciaapp.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;

public class UsuarioController {
    private ApiService apiService;

    public UsuarioController(ApiService apiService) {
        this.apiService = apiService;
    }

    public void registrarUsuario(Usuario usuario, Callback<Usuario> callback) {
        Call<Usuario> call = apiService.registrarUsuario(usuario);
        call.enqueue(callback);
    }
}
