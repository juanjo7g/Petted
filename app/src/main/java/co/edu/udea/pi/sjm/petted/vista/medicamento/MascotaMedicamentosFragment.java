package co.edu.udea.pi.sjm.petted.vista.medicamento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.MedicamentoDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.MedicamentoDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Medicamento;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;

/**
 * Created by Juan on 21/09/2015.
 */
public class MascotaMedicamentosFragment extends Fragment {

    private ListView lvMedicamentos;
    private ImageButton ibtnNuevoMedicamento;
    private MedicamentoDAO daoMe;
    private List<Medicamento> listaMediacamentos;
    private MedicamentoCustomAdapter customAdapter;
    private MascotaActivity ma;

    public MascotaMedicamentosFragment() {
    }

    public static MascotaMedicamentosFragment newInstance() {
        MascotaMedicamentosFragment fragment = new MascotaMedicamentosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mascota_medicamentos, container, false);
        ma = (MascotaActivity) getActivity();

        lvMedicamentos = (ListView) rootView.findViewById(R.id.lvListaMedicamentos);
        ibtnNuevoMedicamento = (ImageButton) rootView.findViewById(R.id.ibtnNuevoMedicamento);

        listaMediacamentos = new ArrayList<>();
        daoMe = new MedicamentoDAOImpl();

        lvMedicamentos.setEmptyView(rootView.findViewById(R.id.llNoMedicamentos));
        onResume();

        ibtnNuevoMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActividadMedicamentoNuevo();
            }
        });

        return rootView;
    }

    private void iniciarActividadMedicamentoNuevo() {
        Intent i = new Intent(ma, MedicamentoFormularioActivity.class);
        i.putExtra("mascotaId", ma.getMascota().getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        listaMediacamentos = daoMe.obtenerMedicamentos(ma.getMascota().getId(), ma);
        customAdapter = new MedicamentoCustomAdapter(ma, listaMediacamentos);
        lvMedicamentos.setAdapter(customAdapter);
    }
}