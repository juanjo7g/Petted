package co.edu.udea.pi.sjm.petted.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Juan on 29/11/2015.
 */
public class Medicamento implements Serializable{
    private String id; // PK
    private String mascota;
    private String nombre;
    private String viaSuministro; // Oral, Cutanea, Inhalatoria, Intravenosa.
    private int cantidadDosis;
    private double pesoDosis; // Peso en gramos de cada dosis
    private int intervaloDosis; // Cada cuanto se suministra la dosis en horas.
    private Date fechaHoraInicio;
    private Date fechaHoraFin;

    public Medicamento() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMascota() {
        return mascota;
    }

    public void setMascota(String mascota) {
        this.mascota = mascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getViaSuministro() {
        return viaSuministro;
    }

    public void setViaSuministro(String viaSuministro) {
        this.viaSuministro = viaSuministro;
    }

    public int getCantidadDosis() {
        return cantidadDosis;
    }

    public void setCantidadDosis(int cantidadDosis) {
        this.cantidadDosis = cantidadDosis;
    }

    public double getPesoDosis() {
        return pesoDosis;
    }

    public void setPesoDosis(double pesoDosis) {
        this.pesoDosis = pesoDosis;
    }

    public int getIntervaloDosis() {
        return intervaloDosis;
    }

    public void setIntervaloDosis(int intervaloDosis) {
        this.intervaloDosis = intervaloDosis;
    }

    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(Date fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(Date fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
}
