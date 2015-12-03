package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public class UsuarioDAOImpl implements UsuarioDAO {


    @Override
    public void insertarUsuario(Usuario u ) {
        ParseUser usuario = new ParseUser();
        try {
            usuario.setUsername(u.getNombre());
            usuario.setPassword(u.getContraseña());
            usuario.setEmail(u.getCorreo());
            usuario.signUp();

        } catch (ParseException e) {
            e.printStackTrace();

        }


//        usuario.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null) {
//
//                } else {
//                    Toast.makeText(context, "Error creando usuario",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

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
        Usuario u = null;
        return u;
    }

    @Deprecated
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

    @Override
    public Usuario obtenerUsuario(String username) {
        Usuario u = new Usuario();
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("_User");
            query.whereEqualTo("username", username);

            list = query.find();
            if (list.size() == 0) {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public Usuario obtenerUsuarioPorCorreo(String correo) {
        Usuario u = new Usuario();
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("_User");
            query.whereEqualTo("email", correo);

            list = query.find();
            if (list.size() == 0) {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return u;
    }


}
