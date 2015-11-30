package co.edu.udea.pi.sjm.petted.dao;

import android.content.Context;

import java.util.List;

import co.edu.udea.pi.sjm.petted.dto.Medicamento;

/**
 * Created by Juan on 29/11/2015.
 */
public interface MedicamentoDAO {
    void insertarMedicamento(Medicamento medicamento, Context context);

    Medicamento obtenerMedicamento(String id, Context context);

    void actualizarMedicamento(Medicamento medicamento, Context context);

    void eliminarMedicamento(Medicamento medicamento, Context context);

    List<Medicamento> obtenerMedicamentos(String mascotaId, Context context);

    List<Medicamento> obtenerMedicamentos(Context context);
}
