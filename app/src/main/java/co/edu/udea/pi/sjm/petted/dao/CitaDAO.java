package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.dto.Mascota;

/**
 * Created by Juan on 25/10/2015.
 */
public interface CitaDAO {
    void insertarCita(Cita cita, Context context);

    Cita obtenerCita(String id, Context context);

    void actualizarCita(Cita cita, Context context);

    void eliminarCita(Cita cita, Context context);

    List<Cita> obtenerCitas(Mascota mascota, Context context);

    List<Cita> obtenerCitas(Context context);
}
