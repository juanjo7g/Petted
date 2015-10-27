package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.CitaDAO;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.dto.Mascota;

/**
 * Created by Juan on 25/10/2015.
 */
public class CitaDAOImpl implements CitaDAO {
    @Override
    public void insertarCita(Cita cita, Context context) {
        PettedDataBaseHelper helper;
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            helper.insertarCita(cita);
        } catch (Exception e) {
        }
    }

    @Override
    public Cita obtenerCita(String id, Context context) {
        PettedDataBaseHelper helper;
        Cursor c;
        Cita cita = new Cita();
        MascotaDAO dao = new MascotaDAOImpl();
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            c = helper.obtenerCita(id);
            if (!c.moveToFirst()) {
                return null;
            }
            cita.setId(c.getString(0));
            cita.setMascota(dao.obtenerMascota(c.getString(1), context));
            cita.setNombre(c.getString(2));
            cita.setDescripcion(c.getString(3));
            cita.setTipo(c.getString(4));

            if (c.getString(5) != null) {
                SimpleDateFormat formateadorDeFecha;
                formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                cita.setFechaHora(formateadorDeFecha.parse(c.getString(5)));
            }
            cita.setEstado(c.getString(6));

        } catch (Exception e) {
            // Error
        }
        return cita;
    }

    @Override
    public void actualizarCita(Cita cita, Context context) {
        PettedDataBaseHelper helper;
        helper = PettedDataBaseHelper.getInstance(context);
        helper.actualizarCita(cita);
    }

    @Override
    public void eliminarCita(Cita cita, Context context) {
        PettedDataBaseHelper helper;
        String id = cita.getId();
        helper = PettedDataBaseHelper.getInstance(context);
        helper.eliminarMascota(id);
    }

    @Override
    public List<Cita> obtenerCitas(Mascota mascota, Context context) {
        PettedDataBaseHelper helper;
        List<Cita> listaCitas = new ArrayList<>();
        Cursor c;
        helper = PettedDataBaseHelper.getInstance(context);
        c = helper.obtenerCitas(mascota);
        Cita cita;
        Mascota m;
        MascotaDAO dao = new MascotaDAOImpl();
        try {
            while (c.moveToNext()) {
                cita = new Cita();
                m = new Mascota();
                m = dao.obtenerMascota(c.getString(1), context);
                cita.setId(c.getString(0));
                cita.setMascota(m);
                cita.setNombre(c.getString(2));
                cita.setDescripcion(c.getString(3));
                cita.setTipo(c.getString(4));

                if (c.getString(5) != null) {
                    SimpleDateFormat formateadorDeFecha;
                    formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
                    cita.setFechaHora(formateadorDeFecha.parse(c.getString(5)));
                }
                cita.setEstado(c.getString(6));

                listaCitas.add(cita);
            }
        } catch (Exception e) {
            // Error
        }
        return listaCitas;
    }

    @Override
    public List<Cita> obtenerCitas(Context context) {
        return null;
    }
}
