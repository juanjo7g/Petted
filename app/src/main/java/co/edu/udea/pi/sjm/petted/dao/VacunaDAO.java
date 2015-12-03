package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;

/**
 * Created by Juan on 27/10/2015.
 */
public interface VacunaDAO {
    void insertarVacuna(Vacuna vacuna);

    Vacuna obtenerVacuna(String id);

    void actualizarVacuna(Vacuna vacuna);

    void eliminarVacuna(Vacuna vacuna);

    List<Vacuna> obtenerVacunas(String mascotaId);

    List<Vacuna> obtenerVacunas();
}
