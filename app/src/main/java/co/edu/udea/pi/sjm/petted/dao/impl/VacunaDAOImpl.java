package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.VacunaDAO;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;

/**
 * Created by Juan on 27/10/2015.
 */
public class VacunaDAOImpl implements VacunaDAO {
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    public void insertarVacuna(Vacuna vacuna, Context context) {
        PettedDataBaseHelper helper;
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            helper.insertarVacuna(vacuna);
        } catch (Exception e) {
        }
    }

    @Override
    public Vacuna obtenerVacuna(String id, Context context) {
        PettedDataBaseHelper helper;
        Cursor c;
        Vacuna vacuna = new Vacuna();
        MascotaDAO dao = new MascotaDAOImpl();
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            c = helper.obtenerCita(id);
            if (!c.moveToFirst()) {
                return null;
            }
            vacuna.setId(c.getString(0));
            vacuna.setMascota(dao.obtenerMascota(c.getString(1), context));
            vacuna.setNombre(c.getString(2));

            if (c.getString(3) != null) {
                vacuna.setFecha(formatoFecha.parse(c.getString(3)));
            }
            if (c.getString(4) != null) {
                vacuna.setFechaProxima(formatoFecha.parse(c.getString(4)));
            }

            if (c.getBlob(5) != null) {
                vacuna.setValidacion(c.getBlob(5));
            }

            vacuna.setEstado(c.getString(6));

        } catch (Exception e) {
            // Error
        }
        return vacuna;
    }

    @Override
    public void actualizarCita(Vacuna vacuna, Context context) {
        PettedDataBaseHelper helper;
        helper = PettedDataBaseHelper.getInstance(context);
        helper.actualizarVacuna(vacuna);
    }

    @Override
    public void eliminarVacuna(Vacuna vacuna, Context context) {
        PettedDataBaseHelper helper;
        String id = vacuna.getId();
        helper = PettedDataBaseHelper.getInstance(context);
        helper.eliminarVacuna(id);
    }

    @Override
    public List<Vacuna> obtenerVacunas(Mascota mascota, Context context) {
        PettedDataBaseHelper helper;
        List<Vacuna> listaVacunas = new ArrayList<>();
        Cursor c;
        helper = PettedDataBaseHelper.getInstance(context);
        c = helper.obtenerVacunas(mascota);
        Vacuna vacuna;
        MascotaDAO dao = new MascotaDAOImpl();
        try {
            while (c.moveToNext()) {
                vacuna = new Vacuna();

                vacuna.setId(c.getString(0));
                vacuna.setMascota(dao.obtenerMascota(c.getString(1), context));
                vacuna.setNombre(c.getString(2));

                if (c.getString(3) != null) {
                    vacuna.setFecha(formatoFecha.parse(c.getString(3)));
                }
                if (c.getString(4) != null) {
                    vacuna.setFechaProxima(formatoFecha.parse(c.getString(4)));
                }

                if (c.getBlob(5) != null) {
                    vacuna.setValidacion(c.getBlob(5));
                }

                vacuna.setEstado(c.getString(6));

                listaVacunas.add(vacuna);
            }
        } catch (Exception e) {
            // Error
        }
        return listaVacunas;
    }

    @Override
    public List<Vacuna> obtenerVacunas(Context context) {
        return null;
    }
}
