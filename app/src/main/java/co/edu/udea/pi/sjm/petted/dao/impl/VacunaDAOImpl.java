package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;

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
        ParseObject v;
        ParseQuery<ParseObject> query;
        try {
            v = new ParseObject("Vacuna");

            query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("id", vacuna.getMascota());

            v.put("id", vacuna.getId());
            v.put("mascota", query.find().get(0));
            v.put("nombre", vacuna.getNombre());
            if (vacuna.getFecha() != null) {
                v.put("fecha", vacuna.getFecha());
            }
            if (vacuna.getFechaProxima() != null) {
                v.put("fechaProxima", vacuna.getFechaProxima());
            }
            if (vacuna.getValidacion() != null) {
                v.put("validacion", vacuna.getValidacion());
            }
            v.pin();
            v.saveEventually();
        } catch (Exception e) {
            e.printStackTrace();
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
//            vacuna.setMascota(dao.obtenerMascota(c.getString(1), context));
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
    public void actualizarVacuna(Vacuna vacuna, Context context) {
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
    public List<Vacuna> obtenerVacunas(String mascotaId, Context context) {
        List<Vacuna> listaVacunas = new ArrayList<>();
        Vacuna v;
        ParseQuery<ParseObject> queryV;
        ParseQuery<ParseObject> queryM;
        List<ParseObject> list;
        ParseObject mascota;
        try {
            queryV = ParseQuery.getQuery("Vacuna");
            queryV.fromLocalDatastore();

            queryM = ParseQuery.getQuery("Mascota");
            queryM.fromLocalDatastore();
            queryM.whereEqualTo("id", mascotaId);

            mascota = queryM.find().get(0);
            queryV.whereEqualTo("mascota", mascota);

            list = queryV.find();

            if (list.size() == 0) {
                Toast.makeText(context, "No hay vacunas todavia", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < list.size(); i++) {
                v = new Vacuna();

                v.setId(list.get(i).getString("id"));
                v.setMascota(list.get(i).getParseObject("mascota").getObjectId());
                v.setNombre(list.get(i).getString("nombre"));
                v.setFecha(list.get(i).getDate("fecha"));
                v.setFechaProxima(list.get(i).getDate("fechaProxima"));
                v.setValidacion(list.get(i).getBytes("validacion"));

                listaVacunas.add(v);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listaVacunas;
    }

    @Override
    public List<Vacuna> obtenerVacunas(Context context) {
        return null;
    }
}
