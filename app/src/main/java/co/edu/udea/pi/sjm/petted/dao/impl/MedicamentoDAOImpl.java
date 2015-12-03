package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.dao.MedicamentoDAO;
import co.edu.udea.pi.sjm.petted.dto.Medicamento;

/**
 * Created by Juan on 29/11/2015.
 */
public class MedicamentoDAOImpl implements MedicamentoDAO {
    @Override
    public void insertarMedicamento(Medicamento medicamento) {
        ParseObject m;
        ParseQuery<ParseObject> query;
        try {
            m = new ParseObject("Medicamento");

            query = ParseQuery.getQuery("Mascota");
            query.fromLocalDatastore();
            query.whereEqualTo("id", medicamento.getMascota());

            m.put("id", medicamento.getId());
            m.put("mascota", query.find().get(0));
            m.put("nombre", medicamento.getNombre());
            m.put("viaSuministro", medicamento.getViaSuministro());
            m.put("cantidadDosis", medicamento.getCantidadDosis());
            m.put("pesoDosis", medicamento.getPesoDosis());
            m.put("intervaloDosis", medicamento.getIntervaloDosis());
            if (medicamento.getFechaHoraInicio() != null) {
                m.put("fechaHoraInicio", medicamento.getFechaHoraInicio());
            }
            if (medicamento.getFechaHoraFin() != null) {
                m.put("fechaHoraFin", medicamento.getFechaHoraFin());
            }
            m.pin();
            m.saveEventually();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Medicamento obtenerMedicamento(String id) {
        Medicamento m = new Medicamento();
        ParseQuery<ParseObject> query;
        List<ParseObject> list;
        try {
            query = ParseQuery.getQuery("Medicamento");

            query.fromLocalDatastore();
            query.whereEqualTo("id", id);

            list = query.find();

            if (list.size() == 0) {
                return null;
            }

            m.setId(list.get(0).getString("id"));
            m.setMascota(list.get(0).getParseObject("mascota").getObjectId());
            m.setNombre(list.get(0).getString("nombre"));
            m.setViaSuministro(list.get(0).getString("viaSuministro"));
            m.setCantidadDosis(list.get(0).getInt("cantidadDosis"));
            m.setPesoDosis(list.get(0).getDouble("pesoDosis"));
            m.setIntervaloDosis(list.get(0).getInt("intervaloDosis"));

            if (list.get(0).getDate("fechaHoraInicio") != null) {
                m.setFechaHoraInicio(list.get(0).getDate("fechaHoraInicio"));
            }
            if (list.get(0).getDate("fechaHoraFin") != null) {
                m.setFechaHoraFin(list.get(0).getDate("fechaHoraFin"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return m;

    }

    @Override
    public void actualizarMedicamento(Medicamento medicamento) {
        ParseObject m;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Medicamento");

            query.fromLocalDatastore();
            query.whereEqualTo("id", medicamento.getId());

            m = query.find().get(0);

            m.put("nombre", medicamento.getNombre());
            m.put("viaSuministro", medicamento.getViaSuministro());
            m.put("cantidadDosis", medicamento.getCantidadDosis());
            m.put("pesoDosis", medicamento.getPesoDosis());
            m.put("intervaloDosis", medicamento.getIntervaloDosis());
            if (medicamento.getFechaHoraInicio() != null) {
                m.put("fechaHoraInicio", medicamento.getFechaHoraInicio());
            }
            if (medicamento.getFechaHoraFin() != null) {
                m.put("fechaHoraFin", medicamento.getFechaHoraFin());
            }
            m.pin();
            m.saveEventually();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarMedicamento(Medicamento medicamento) {
        ParseObject m;
        ParseQuery<ParseObject> query;
        try {
            query = ParseQuery.getQuery("Medicamento");
            query.fromLocalDatastore();
            query.whereEqualTo("id", medicamento.getId());

            m = query.find().get(0);

            m.unpin();
            m.deleteEventually();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Medicamento> obtenerMedicamentos(String mascotaId) {
        List<Medicamento> listaMedicamentos = new ArrayList<>();
        Medicamento m;
        ParseQuery<ParseObject> queryMed;
        ParseQuery<ParseObject> queryM;
        List<ParseObject> list;
        ParseObject mascota;
        try {
            queryMed = ParseQuery.getQuery("Medicamento");
            queryMed.fromLocalDatastore();

            queryM = ParseQuery.getQuery("Mascota");
            queryM.fromLocalDatastore();
            queryM.whereEqualTo("id", mascotaId);

            mascota = queryM.find().get(0);
            queryMed.whereEqualTo("mascota", mascota);

            list = queryMed.find();

            for (int i = 0; i < list.size(); i++) {

                m = new Medicamento();

                m.setId(list.get(i).getString("id"));
                m.setMascota(list.get(i).getParseObject("mascota").getObjectId());
                m.setNombre(list.get(i).getString("nombre"));
                m.setViaSuministro(list.get(i).getString("viaSuministro"));
                m.setCantidadDosis(list.get(i).getInt("cantidadDosis"));
                m.setPesoDosis(list.get(i).getDouble("pesoDosis"));
                m.setIntervaloDosis(list.get(i).getInt("intervaloDosis"));
                m.setFechaHoraInicio(list.get(i).getDate("fechaHoraInicio"));
                m.setFechaHoraFin(list.get(i).getDate("fechaHoraFin"));

                listaMedicamentos.add(m);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return listaMedicamentos;
    }

    @Override
    public List<Medicamento> obtenerMedicamentos() {
        return null;
    }
}
