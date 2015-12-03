package co.edu.udea.pi.sjm.petted.dao.impl;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
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
import co.edu.udea.pi.sjm.petted.dao.CitaDAO;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.dto.Mascota;

/**
 * Created by Juan on 25/10/2015.
 */
public class CitaDAOImpl implements CitaDAO {
    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);

    @Override
    public void insertarCita(Cita cita) {
        ParseObject c;
        ParseQuery<ParseObject> query;
        try {
            c = new ParseObject("Cita");

            query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("id", cita.getMascota());

            c.put("id", cita.getId());
            c.put("mascota", query.find().get(0));
            c.put("nombre", cita.getNombre());
            c.put("descripcion", cita.getDescripcion());
            c.put("tipo", cita.getTipo());
            if (cita.getFechaHora() != null) {
                c.put("fechaHora", cita.getFechaHora());
            }
            c.pin();
            c.saveEventually();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cita obtenerCita(String id) {
        Cita c = new Cita();
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("Cita");

            query.fromLocalDatastore();
            query.whereEqualTo("id", id);

            list = query.find();

            if (list.size() == 0) {
                return null;
            }

            c.setId(list.get(0).getString("id"));
            c.setMascota(list.get(0).getParseObject("mascota").getObjectId());
            c.setNombre(list.get(0).getString("nombre"));
            c.setDescripcion(list.get(0).getString("descripcion"));
            c.setTipo(list.get(0).getString("tipo"));

            if (list.get(0).getDate("fechaHora") != null) {
                c.setFechaHora(list.get(0).getDate("fechaHora"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void actualizarCita(Cita cita) {
        ParseObject c;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Cita");

            query.fromLocalDatastore();
            query.whereEqualTo("id", cita.getId());

            c = query.find().get(0);

            c.put("nombre", cita.getNombre());
            c.put("descripcion", cita.getDescripcion());
            c.put("tipo", cita.getTipo());
            if (cita.getFechaHora() != null) {
                c.put("fechaHora", cita.getFechaHora());
            }
            c.pin();
            c.saveEventually();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarCita(Cita cita) {
        ParseObject c;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Cita");
            query.fromLocalDatastore();
            query.whereEqualTo("id", cita.getId());

            c = query.find().get(0);

            c.unpin();
            c.deleteEventually();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cita> obtenerCitas(String mascotaId) {
        List<Cita> listaCitas = new ArrayList<>();
        Cita c;
        ParseQuery<ParseObject> queryC;
        ParseQuery<ParseObject> queryM;
        List<ParseObject> list;
        ParseObject mascota;
        try {
            queryC = ParseQuery.getQuery("Cita");
            queryC.fromLocalDatastore();

            queryM = ParseQuery.getQuery("Mascota");
            queryM.fromLocalDatastore();
            queryM.whereEqualTo("id", mascotaId);

            mascota = queryM.find().get(0);
            queryC.whereEqualTo("mascota", mascota);

            list = queryC.find();

            for (int i = 0; i < list.size(); i++) {

                c = new Cita();

                c.setId(list.get(i).getString("id"));
                c.setMascota(list.get(i).getParseObject("mascota").getObjectId());
                c.setNombre(list.get(i).getString("nombre"));
                c.setDescripcion(list.get(i).getString("descripcion"));
                c.setTipo(list.get(i).getString("tipo"));
                c.setFechaHora(list.get(i).getDate("fechaHora"));

                listaCitas.add(c);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listaCitas;
    }

    @Override
    public List<Cita> obtenerCitas() {
        return null;
    }
}
