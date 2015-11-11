package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
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
    public void insertarCita(Cita cita, Context context) {
        ParseObject c;
        ParseObject mascota;
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
//            cita.setMascota(dao.obtenerMascota(c.getString(1), context));
            cita.setNombre(c.getString(2));
            cita.setDescripcion(c.getString(3));
            cita.setTipo(c.getString(4));

            if (c.getString(5) != null) {
                cita.setFechaHora(formatoFechaHora.parse(c.getString(5)));
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
        helper.eliminarCita(id);
    }

    @Override
    public List<Cita> obtenerCitas(String mascotaId, Context context) {
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

            if (list.size() == 0) {
                Toast.makeText(context, "No hay citas todavia", Toast.LENGTH_SHORT).show();
            }
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
    public List<Cita> obtenerCitas(Context context) {
        return null;
    }
}
