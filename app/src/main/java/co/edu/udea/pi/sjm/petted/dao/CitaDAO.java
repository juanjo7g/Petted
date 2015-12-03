package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.dto.Mascota;

/**
 * Created by Juan on 25/10/2015.
 */
public interface CitaDAO {
    void insertarCita(Cita cita);

    Cita obtenerCita(String id);

    void actualizarCita(Cita cita);

    void eliminarCita(Cita cita);

    List<Cita> obtenerCitas(String mascotaId);

    List<Cita> obtenerCitas();
}
