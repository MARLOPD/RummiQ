package com.rummyq.model;

/**
 * Modelo que representa un usuario registrado en el sistema.
 * Patrón: Plain Old Java Object (POJO)
 */
public class Usuario {

    private int    id;
    private String correo;
    private String contrasena;       // guardada como hash SHA-256
    private String preguntaSeguridad;
    private String respuestaSeguridad; // guardada como hash SHA-256
    private String nombre;           // parte del correo o alias

    // ── Constructores ────────────────────────────────────────────────────────

    public Usuario() {}

    public Usuario(String correo, String contrasena,
                   String preguntaSeguridad, String respuestaSeguridad) {
        this.correo              = correo;
        this.contrasena          = contrasena;
        this.preguntaSeguridad   = preguntaSeguridad;
        this.respuestaSeguridad  = respuestaSeguridad;
        this.nombre              = correo.split("@")[0]; // nombre por defecto
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public int    getId()                    { return id; }
    public void   setId(int id)              { this.id = id; }

    public String getCorreo()                { return correo; }
    public void   setCorreo(String correo)   { this.correo = correo; }

    public String getContrasena()            { return contrasena; }
    public void   setContrasena(String c)    { this.contrasena = c; }

    public String getPreguntaSeguridad()     { return preguntaSeguridad; }
    public void   setPreguntaSeguridad(String p) { this.preguntaSeguridad = p; }

    public String getRespuestaSeguridad()    { return respuestaSeguridad; }
    public void   setRespuestaSeguridad(String r) { this.respuestaSeguridad = r; }

    public String getNombre()                { return nombre; }
    public void   setNombre(String nombre)   { this.nombre = nombre; }

    @Override
    public String toString() {
        return "Usuario{id=" + id + ", correo='" + correo + "', nombre='" + nombre + "'}";
    }
}
