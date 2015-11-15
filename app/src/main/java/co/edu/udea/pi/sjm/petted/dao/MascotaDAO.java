package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public interface MascotaDAO {
    void insertarMascota(Mascota mascota, Context context);

    Mascota obtenerMascota(String id, Context context);

    String obtenerMascotaId(String idTag);

    void actualizarMascota(Mascota mascota, Context context);

    void eliminarMascota(Mascota mascota, Context context);

    List<Mascota> obtenerMascotas(Context context);

    Mascota obtenerMascotaConIdTag(String idTag);
}
