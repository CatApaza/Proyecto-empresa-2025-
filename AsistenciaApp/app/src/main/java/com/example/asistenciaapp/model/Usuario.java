package com.example.asistenciaapp.model;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;
    private String codigoJefe; // 🔹 Nuevo campo

    // Constructor vacío (necesario para Retrofit/Gson)
    public Usuario() {
    }

    // Constructor completo SIN codigoJefe
    public Usuario(int id, String nombre, String correo, String contrasena, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
    }

    // Constructor rápido para login
    public Usuario(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // 🔹 Constructor con codigoJefe incluido
    public Usuario(int id, String nombre, String correo, String contrasena, String rol, String codigoJefe) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.rol = rol;
        this.codigoJefe = codigoJefe;
    }

    // ====== GETTERS ======
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getRol() { return rol; }
    public String getCodigoJefe() { return codigoJefe; }

    // ====== SETTERS ======
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public void setRol(String rol) { this.rol = rol; }
    public void setCodigoJefe(String codigoJefe) { this.codigoJefe = codigoJefe; }
}
