package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Medicamento;

/**
 * Created by Juan on 29/11/2015.
 */
public interface MedicamentoDAO {
    void insertarMedicamento(Medicamento medicamento);

    Medicamento obtenerMedicamento(String id);

    void actualizarMedicamento(Medicamento medicamento);

    void eliminarMedicamento(Medicamento medicamento);

    List<Medicamento> obtenerMedicamentos(String mascotaId);

    List<Medicamento> obtenerMedicamentos();
}
