package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;

/**
 * Created by Juan on 27/10/2015.
 */
public interface VacunaDAO {
    void insertarVacuna(Vacuna vacuna, Context context);

    Vacuna obtenerVacuna(String id, Context context);

    void actualizarCita(Vacuna vacuna, Context context);

    void eliminarVacuna(Vacuna vacuna, Context context);

    List<Vacuna> obtenerVacunas(Mascota mascota, Context context);

    List<Vacuna> obtenerVacunas(Context context);
}
