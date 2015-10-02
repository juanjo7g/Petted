package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public class UsuarioDAOImpl implements UsuarioDAO {


    @Override
    public void insertarUsuario(Usuario usuario, Context context) {
        PettedDataBaseHelper helper;
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            helper.insertarUsuario(usuario);
        } catch (Exception e) {
        }
    }

    @Override
    public Usuario obtenerUsuario(String correo, Context context) {
        PettedDataBaseHelper helper;
        Cursor c;
        Usuario u = null;
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            c = helper.obtenerUsuario(correo);
            u = new Usuario();
            if (!c.moveToFirst()) {
                return null;
            }
            u.setCorreo(c.getString(0));
            u.setNombre(c.getString(1));
            u.setContraseña(c.getString(2));
        } catch (Exception e) {
            // Error
        }
        return u;
    }

    @Override
    public void actualizarUsuario(Usuario usuario, Context context) {

    }

    @Override
    public void eliminarUsuario(Usuario usuario, Context context) {

    }

    @Override
    public List<Usuario> obtener(Context context) {
        return null;
    }
}
