package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.util.Utility;
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
    public Mascota obtenerMascota(String id, Context context) {
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
            m.setId(c.getString(0));
            m.setPropietario(dao.obtenerUsuario(c.getString(1), context));
            m.setNombre(c.getString(2));
            if (c.getString(3) != null) {
                SimpleDateFormat formateadorDeFecha;
                formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                m.setFechaNacimiento(formateadorDeFecha.parse(c.getString(3)));
            }
            m.setTipo(c.getString(4));
            m.setRaza(c.getString(5));
            m.setGenero(c.getString(6));
            m.setIdTag(c.getString(7));

            if (c.getBlob(8) != null) {
                m.setFoto(c.getBlob(8));
            }

            m.setNotificaciones(c.getString(9));
            m.setEstado(c.getString(10));

        } catch (Exception e) {
            // Error
        }
        return m;
    }

    @Override
    public void actualizarMascota(Mascota mascota, Context context) {
        PettedDataBaseHelper helper;
        helper = PettedDataBaseHelper.getInstance(context);
        helper.actualizarMascota(mascota);
    }

    @Override
    public void eliminarMascota(Mascota mascota, Context context) {
        PettedDataBaseHelper helper;
        String id = mascota.getId();
        helper = PettedDataBaseHelper.getInstance(context);
        helper.eliminarMascota(id);
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
        try {
            while (c.moveToNext()) {
                m = new Mascota();
                m.setId(c.getString(0));
                m.setPropietario(dao.obtenerUsuario(c.getString(1), context));
                m.setNombre(c.getString(2));
                if (c.getString(3) != null) {
                    SimpleDateFormat formateadorDeFecha;
                    formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    m.setFechaNacimiento(formateadorDeFecha.parse(c.getString(3)));
                }
                m.setTipo(c.getString(4));
                m.setRaza(c.getString(5));
                m.setGenero(c.getString(6));
                m.setIdTag(c.getString(7));

                if (c.getBlob(8) != null) {
                    m.setFoto(c.getBlob(8));
                }

                m.setNotificaciones(c.getString(9));
                m.setEstado(c.getString(10));
                listaMascotas.add(m);
            }
        } catch (Exception e) {
            // Error
        }
        return listaMascotas;
    }

    @Deprecated
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
            m.setId(c.getString(0));
            m.setPropietario(dao.obtenerUsuario(c.getString(1), context));
            m.setNombre(c.getString(2));
            listaMascotas.add(m);
        }
        return listaMascotas;
    }
}
