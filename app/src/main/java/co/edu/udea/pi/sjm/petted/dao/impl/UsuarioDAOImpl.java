package co.edu.udea.pi.sjm.petted.dao.impl;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;
import co.edu.udea.pi.sjm.petted.vista.MainActivity;

/**
 * Created by Juan on 02/10/2015.
 */
public class UsuarioDAOImpl implements UsuarioDAO {


    @Override
    public void insertarUsuario(Usuario u, final Context context) {
        ParseUser usuario = new ParseUser();
        usuario.setUsername(u.getNombre());
        usuario.setPassword(u.getContraseña());
        usuario.setEmail(u.getCorreo());

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(context, "Usuario creado con exito",
                            Toast.LENGTH_SHORT).show();
                    ((Activity) context).finish();
                    ParseUser.logOut();
                } else {
                    Toast.makeText(context, "Error creando usuario",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Deprecated
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
            u.setLogueado(c.getString(3));
        } catch (Exception e) {
            // Error
        }
        return u;
    }

    @Deprecated
    @Override
    public Usuario obtenerUsuarioLogueado(Context context) {
        PettedDataBaseHelper helper;
        Cursor c;
        Usuario u = null;
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            c = helper.obtenerUsuarioLogueado();
            u = new Usuario();
            if (!c.moveToFirst()) {
                return null;
            }
            u.setCorreo(c.getString(0));
            u.setNombre(c.getString(1));
            u.setContraseña(c.getString(2));
            u.setLogueado(c.getString(3));
        } catch (Exception e) {
            // Error
        }
        return u;
    }

    @Deprecated
    @Override
    public void actualizarUsuario(Usuario usuario, Context context) {
        PettedDataBaseHelper helper;
        helper = PettedDataBaseHelper.getInstance(context);
        helper.actualizarUsuario(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario, Context context) {

    }

    @Override
    public List<Usuario> obtener(Context context) {
        return null;
    }
}
