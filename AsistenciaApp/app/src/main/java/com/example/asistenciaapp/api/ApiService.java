package com.example.asistenciaapp.api;

import com.example.asistenciaapp.model.LoginResponse;
import com.example.asistenciaapp.model.Usuario;
import com.example.asistenciaapp.model.Asistencia;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    // 📌 Usuarios
    @POST("/api/registro")
    Call<Void> registrarUsuario(@Body Usuario usuario);

    @POST("/api/login")
    Call<LoginResponse> login(@Body Usuario usuario);

    @GET("/api/asistencias")
    Call<List<Asistencia>> getAsistencias();

    // 📌 Empleados - Reconocimiento Facial

    // Registrar foto de referencia
    @Multipart
    @POST("/api/empleados/registro/{id}")
    Call<ResponseBody> registrarEmpleadoFoto(
            @Path("id") String empleadoId,
            @Part MultipartBody.Part foto
    );

    // Validar empleado con foto
    @Multipart
    @POST("/api/empleados/validar/{id}")
    Call<ResponseBody> validarEmpleado(
            @Path("id") String empleadoId,
            @Part MultipartBody.Part foto
    );
}
