package co.edu.udea.pi.sjm.petted.dao.impl;

import android.app.Activity;
import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.util.Utility;

/**
 * Created by Juan on 02/10/2015.
 */
public class MascotaDAOImpl implements MascotaDAO {

    @Override
    public void insertarMascota(Mascota mascota) {
        final ParseObject m;
        ParseObject propietario;
        try {
            m = new ParseObject("Mascota");
            propietario = ParseObject.createWithoutData("_User", mascota.getPropietario());

            m.put("id", mascota.getId());
            m.put("nombre", mascota.getNombre());
            m.put("propietario", propietario);
            if (mascota.getFechaNacimiento() != null) {
                m.put("fechaNacimiento", mascota.getFechaNacimiento());
            }
            m.put("tipo", mascota.getTipo());
            m.put("raza", mascota.getRaza());
            m.put("genero", mascota.getGenero());
            if (mascota.getFoto() != null) {
                m.put("foto", Utility.compreesByteArray(mascota.getFoto()));
            }
            m.put("notificaciones", mascota.getNotificaciones());
            m.pin();
            m.saveEventually();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mascota obtenerMascota(String id) {
        Mascota m = new Mascota();
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("Mascota");

            query.fromLocalDatastore();
            query.whereEqualTo("id", id);

            list = query.find();

            if (list.size() == 0) {
                return null;
            }

            m.setId(list.get(0).getString("id"));
            m.setPropietario(list.get(0).getParseUser("propietario").getObjectId());
            m.setNombre(list.get(0).getString("nombre"));
            if (list.get(0).getDate("fechaNacimiento") != null) {
                m.setFechaNacimiento(list.get(0).getDate("fechaNacimiento"));
            }
            m.setTipo(list.get(0).getString("tipo"));
            m.setRaza(list.get(0).getString("raza"));
            m.setGenero(list.get(0).getString("genero"));
            m.setIdTag(list.get(0).getString("idTag"));
            if (list.get(0).getBytes("foto") != null) {
                m.setFoto(Utility.descompressByteArray(list.get(0).getBytes("foto")));
            }
            m.setNotificaciones(list.get(0).getBoolean("notificaciones"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public Mascota obtenerMascotaConIdTag(String idTag) {
        Mascota m = new Mascota();
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("Mascota");
            query.whereEqualTo("idTag", idTag);
            list = query.find();

            if (list.size() == 0) {
                return null;
            }
            m.setId(list.get(0).getString("id"));
            m.setPropietario(list.get(0).getParseUser("propietario").getObjectId());
            m.setNombre(list.get(0).getString("nombre"));
            if (list.get(0).getDate("fechaNacimiento") != null) {
                m.setFechaNacimiento(list.get(0).getDate("fechaNacimiento"));
            }
            m.setTipo(list.get(0).getString("tipo"));
            m.setRaza(list.get(0).getString("raza"));
            m.setGenero(list.get(0).getString("genero"));
            m.setIdTag(list.get(0).getString("idTag"));
            if (list.get(0).getBytes("foto") != null) {
                m.setFoto(Utility.descompressByteArray(list.get(0).getBytes("foto")));
            }
            m.setNotificaciones(list.get(0).getBoolean("notificaciones"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public String obtenerMascotaId(String idTag) {
        String id = "";
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("idTag", idTag);
            list = query.find();
            if (list.size() == 0) {
                return "";
            } else {
                id = list.get(0).getString("id");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void actualizarMascota(Mascota mascota) {
        ParseObject m;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Mascota");

            query.fromLocalDatastore();
            query.whereEqualTo("id", mascota.getId());

            m = query.find().get(0);

            m.put("nombre", mascota.getNombre());
            if (mascota.getFechaNacimiento() != null) {
                m.put("fechaNacimiento", mascota.getFechaNacimiento());
            }
            m.put("tipo", mascota.getTipo());
            m.put("raza", mascota.getRaza());
            m.put("genero", mascota.getGenero());
            if (mascota.getFoto() != null) {
                m.put("foto", Utility.compreesByteArray(mascota.getFoto()));
            }
            m.put("notificaciones", mascota.getNotificaciones());
            if (mascota.getIdTag() != null) {
                m.put("idTag", mascota.getIdTag());
            }
            m.pin();
            m.saveEventually();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarMascota(Mascota mascota) {
        ParseObject m;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("id", mascota.getId());

            m = query.find().get(0);

            m.unpin();
            m.deleteEventually();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Mascota> obtenerMascotas() {
        List<Mascota> listaMascotas = new ArrayList<>();
        Mascota m;
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("propietario", ParseUser.getCurrentUser());

            list = query.find();

            for (int i = 0; i < list.size(); i++) {

                m = new Mascota();

                m.setId(list.get(i).getString("id"));
                m.setNombre(list.get(i).getString("nombre"));
                m.setPropietario(list.get(i).getParseUser("propietario").getObjectId());
                m.setTipo(list.get(i).getString("tipo"));
                m.setRaza(list.get(i).getString("raza"));
                m.setFoto(Utility.descompressByteArray(list.get(i).getBytes("foto")));
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
