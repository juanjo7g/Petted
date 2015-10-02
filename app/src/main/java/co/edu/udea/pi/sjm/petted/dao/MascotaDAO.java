package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public interface MascotaDAO {
    void insertarUsuario(Mascota mascota, Context context);

    Mascota obtenerUsuario(String id, Context context);

    void actualizarUsuario(Mascota mascota, Context context);

    void eliminarUsuario(Mascota mascota, Context context);

    List<Mascota> obtenerMasctoras(Usuario usuario, Context context);

    List<Mascota> obtener(Context context);
}
