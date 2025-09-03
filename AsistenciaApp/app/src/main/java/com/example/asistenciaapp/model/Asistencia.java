package com.example.asistenciaapp.model;

public class Asistencia {
    private String nombre;   // nombre del trabajador
    private String fecha;    // fecha asistencia
    private String hora;     // hora asistencia

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
}
