package co.edu.udea.pi.sjm.petted.dao;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Mascota;

/**
 * Created by Juan on 02/10/2015.
 */
public interface MascotaDAO {
    void insertarMascota(Mascota mascota);

    Mascota obtenerMascota(String id);

    String obtenerMascotaId(String idTag);

    void actualizarMascota(Mascota mascota);

    void actualizarMascotaPerdida(String id);

    void eliminarMascota(Mascota mascota);

    List<Mascota> obtenerMascotas();

    Mascota obtenerMascotaConIdTag(String idTag);
}
