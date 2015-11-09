package co.edu.udea.pi.sjm.petted.dao.impl;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public class MascotaDAOImpl implements MascotaDAO {
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    @Override
    public void insertarMascota(Mascota mascota, final Context context) {
        final ParseObject m = new ParseObject("Mascota");
        try {
            m.put("id", mascota.getId());
            m.put("nombre", mascota.getNombre());
            ParseObject propietario = ParseObject.createWithoutData("_User", mascota.getPropietario());
            m.put("propietario", propietario);
            if (mascota.getFechaNacimiento() != null) {
                m.put("fechaNacimiento", mascota.getFechaNacimiento());
            }
            m.put("tipo", mascota.getTipo());
            m.put("raza", mascota.getRaza());
            m.put("genero", mascota.getGenero());
            if (mascota.getFoto() != null) {
                m.put("foto", mascota.getFoto());
            }
            m.put("notificaciones", mascota.getNotificaciones());
            m.pinInBackground(new SaveCallback() {
                @Override
                public void done(com.parse.ParseException e) {
                    if (e == null) {
                        Toast.makeText(context, "Mascota guardada " +
                                        "localmente, intentando almacenar remoto...",
                                Toast.LENGTH_SHORT).show();
                        m.saveEventually(new SaveCallback() {
                            @Override
                            public void done(com.parse.ParseException e) {
                                if (e == null) {
                                    Toast.makeText(context, "Mascota " +
                                            "guardada remotamente", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        ((Activity) context).finish();
                    } else {
                        Toast.makeText(context, "Error guardado mascotaParseO",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mascota obtenerMascota(String id, Context context) {
        Mascota m = new Mascota();
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("id", id);
            List<ParseObject> list = query.find();

            m.setId(list.get(0).getString("id"));
            m.setPropietario(list.get(0).getParseUser("propietario").getObjectId());
            m.setNombre(list.get(0).getString("nombre"));
            if (list.get(0).getDate("fechaNacimiento") != null) {
                m.setFechaNacimiento(list.get(0).getDate("fechaNacimiento"));
            }
            m.setTipo(list.get(0).getString("tipo"));
            m.setRaza(list.get(0).getString("raza"));
            m.setGenero(list.get(0).getString("genero"));
            m.setIdTag(list.get(0).getString("IdTag"));
            if (list.get(0).getBytes("foto") != null) {
                m.setFoto(list.get(0).getBytes("foto"));
            }
            m.setNotificaciones(list.get(0).getBoolean("notificaciones"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public void actualizarMascota(Mascota mascota, Context context) {
        ParseObject m;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mascota");
        query.fromLocalDatastore();
        query.whereEqualTo("id", mascota.getId());
        try {
            m = query.find().get(0);
            m.put("nombre", mascota.getNombre());
            if (mascota.getFechaNacimiento() != null) {
                m.put("fechaNacimiento", mascota.getFechaNacimiento());
            }
            m.put("tipo", mascota.getTipo());
            m.put("raza", mascota.getRaza());
            m.put("genero", mascota.getGenero());
            if (mascota.getFoto() != null) {
                m.put("foto", mascota.getFoto());
            }
            m.put("notificaciones", mascota.getNotificaciones());
            m.pinInBackground();
            m.saveEventually();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarMascota(Mascota mascota, Context context) {
        ParseObject m;
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Mascota");
        query.fromLocalDatastore();
        query.whereEqualTo("id", mascota.getId());
        try {
            m = query.find().get(0);
            m.unpin();
            m.deleteEventually();
            Toast.makeText(context, "Mascota eliminada", Toast.LENGTH_SHORT).show();
            ((Activity) context).finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Mascota> obtenerMascotas(Context context) {
        List<Mascota> listaMascotas = new ArrayList<>();
        Mascota m;
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("propietario", ParseUser.getCurrentUser());

            List<ParseObject> list = query.find();

            if (list.size() == 0) {
                Toast.makeText(context, "No hay mascotas todavia", Toast.LENGTH_SHORT).show();
            }

            for (int i = 0; i < list.size(); i++) {

                m = new Mascota();

                m.setId(list.get(i).getString("id"));
                m.setNombre(list.get(i).getString("nombre"));
                m.setPropietario(list.get(i).getParseUser("propietario").getObjectId());
                m.setTipo(list.get(i).getString("tipo"));
                m.setRaza(list.get(i).getString("raza"));
                m.setFoto(list.get(i).getBytes("foto"));
                m.setFechaNacimiento(list.get(i).getDate("fechaNacimiento"));
                m.setNotificaciones(list.get(i).getBoolean("notificaciones"));

                listaMascotas.add(m);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listaMascotas;
    }
}
