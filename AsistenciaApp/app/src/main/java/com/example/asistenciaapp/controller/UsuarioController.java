package com.example.asistenciaapp.controller;

import com.example.asistenciaapp.model.ApiServices;
import com.example.asistenciaapp.model.Usuario;

import retrofit2.Call;
import retrofit2.Callback;

public class UsuarioController {
    private ApiServices apiService;

    public UsuarioController(ApiServices apiService) {
        this.apiService = apiService;
    }

    public void registrarUsuario(Usuario usuario, Callback<Usuario> callback) {
        Call<Usuario> call = apiService.registrarUsuario(usuario);
        call.enqueue(callback);
    }
}
