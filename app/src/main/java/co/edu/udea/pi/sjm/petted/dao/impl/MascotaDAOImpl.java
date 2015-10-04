package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dao.Utility;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public class MascotaDAOImpl implements MascotaDAO {
    @Override
    public void insertarMascota(Mascota mascota, Context context) {
        PettedDataBaseHelper helper;
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            helper.insertarMascota(mascota);
        } catch (Exception e) {
        }
    }

    @Override
    public Mascota obtenerMascota(int id, Context context) {
        PettedDataBaseHelper helper;
        Cursor c;
        Mascota m = new Mascota();
        UsuarioDAO dao = new UsuarioDAOImpl();
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            c = helper.obtenerMascota(id);
            if (!c.moveToFirst()) {
                return null;
            }
            m.setId(c.getInt(0));
            m.setPropietario(dao.obtenerUsuario(c.getString(1), context));
            m.setNombre(c.getString(2));

        } catch (Exception e) {
            // Error
        }
        return m;
    }

    @Override
    public void actualizarMascota(Mascota mascota, Context context) {

    }

    @Override
    public void eliminarMascota(Mascota mascota, Context context) {

    }

    @Override
    public List<Mascota> obtenerMascotas(Usuario usuario, Context context) {
        PettedDataBaseHelper helper;
        List<Mascota> listaMascotas = new ArrayList<>();
        Cursor c;
        helper = PettedDataBaseHelper.getInstance(context);
        c = helper.obtenerMascotas(usuario);
        Mascota m;
        UsuarioDAO dao = new UsuarioDAOImpl();

        while (c.moveToNext()) {
            m = new Mascota();
            m.setId(c.getInt(0));
            m.setPropietario(dao.obtenerUsuario(c.getString(1), context));
            m.setNombre(c.getString(2));
            if (c.getBlob(8) != null) {
                m.setFoto(Utility.getFoto(c.getBlob(8)));
            }
            listaMascotas.add(m);
        }
        return listaMascotas;
    }

    @Override
    public List<Mascota> obtenerMascotas(Context context) {
        PettedDataBaseHelper helper;
        List<Mascota> listaMascotas = new ArrayList<>();
        Cursor c;
        helper = PettedDataBaseHelper.getInstance(context);
        c = helper.obtenerMascotas();
        Mascota m = new Mascota();
        UsuarioDAO dao = new UsuarioDAOImpl();

        while (c.moveToNext()) {
            m.setId(c.getInt(0));
            m.setPropietario(dao.obtenerUsuario(c.getString(1), context));
            m.setNombre(c.getString(2));
            listaMascotas.add(m);
        }
        return listaMascotas;
    }
}
