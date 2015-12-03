package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.dao.VacunaDAO;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;

/**
 * Created by Juan on 27/10/2015.
 */
public class VacunaDAOImpl implements VacunaDAO {

    @Override
    public void insertarVacuna(Vacuna vacuna) {
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
    public Vacuna obtenerVacuna(String id) {
        Vacuna v = new Vacuna();
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("Vacuna");

            query.fromLocalDatastore();
            query.whereEqualTo("id", id);

            list = query.find();

            if (list.size() == 0) {
                return null;
            }

            v.setId(list.get(0).getString("id"));
            v.setMascota(list.get(0).getParseObject("mascota").getObjectId());
            v.setNombre(list.get(0).getString("nombre"));

            if (list.get(0).getDate("fecha") != null) {
                v.setFecha(list.get(0).getDate("fecha"));
            }
            if (list.get(0).getDate("fechaProxima") != null) {
                v.setFechaProxima(list.get(0).getDate("fechaProxima"));
            }
            if (list.get(0).getBytes("validacion") != null) {
                v.setValidacion(list.get(0).getBytes("validacion"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void actualizarVacuna(Vacuna vacuna) {
        ParseObject v;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Vacuna");

            query.fromLocalDatastore();
            query.whereEqualTo("id", vacuna.getId());

            v = query.find().get(0);

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
    public void eliminarVacuna(Vacuna vacuna) {
        ParseObject v;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Vacuna");
            query.fromLocalDatastore();
            query.whereEqualTo("id", vacuna.getId());

            v = query.find().get(0);

            v.unpin();
            v.deleteEventually();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vacuna> obtenerVacunas(String mascotaId) {
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
    public List<Vacuna> obtenerVacunas() {
        return null;
    }
}
