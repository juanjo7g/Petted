package co.edu.udea.pi.sjm.petted.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Juan on 27/10/2015.
 */
public class Vacuna implements Serializable{
    private String id; //pk autoincrementable
    private Mascota mascota;
    private String nombre;
    private Date fecha;
    private Date fechaProxima;
    private byte[] validacion;
    private String estado;

    public Vacuna() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaProxima() {
        return fechaProxima;
    }

    public void setFechaProxima(Date fechaProxima) {
        this.fechaProxima = fechaProxima;
    }

    public byte[] getValidacion() {
        return validacion;
    }

    public void setValidacion(byte[] validacion) {
        this.validacion = validacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
