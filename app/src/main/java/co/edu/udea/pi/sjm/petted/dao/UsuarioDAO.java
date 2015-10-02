package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public interface UsuarioDAO {
    void insertarUsuario(Usuario usuario, Context context);

    Usuario obtenerUsuario(String correo, Context context);

    void actualizarUsuario(Usuario usuario, Context context);

    void eliminarUsuario(Usuario usuario, Context context);

    List<Usuario> obtener(Context context);
}
