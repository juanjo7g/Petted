package co.edu.udea.pi.sjm.petted.dto;

/**
 * Created by Juan on 27/09/2015.
 */
public class Usuario {
    private String nombre;
    private String correo; // PK
    private String contraseña;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}
