package co.edu.udea.pi.sjm.petted.vista.listadoCita;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.CitaDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.CitaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;

/**
 * Created by Juan on 20/09/2015.
 */
public class MascotaCitasFragment extends Fragment {

    private ListView lvCitas;
    private ImageButton ibtnNuevaCita;
    private CitaDAO daoC;
    private List<Cita> listaCitas;
    private CitaCustomAdapter customAdapter;
    private MascotaActivity ma;

    public MascotaCitasFragment() {
    }

    public static MascotaCitasFragment newInstance() {
        MascotaCitasFragment fragment = new MascotaCitasFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mascota_citas, container, false);

        lvCitas = (ListView) rootView.findViewById(R.id.lvListaCitas);
        ibtnNuevaCita = (ImageButton) rootView.findViewById(R.id.ibtnNuevaCita);

        listaCitas = new ArrayList<>();

        ma = (MascotaActivity) getActivity();
        Toast.makeText(ma, ma.getMascota().getId(), Toast.LENGTH_SHORT).show();

        daoC = new CitaDAOImpl();
        listaCitas = daoC.obtenerCitas(ma.getMascota().getId(), ma);

        customAdapter = new CitaCustomAdapter(ma, listaCitas);
        lvCitas.setAdapter(customAdapter);

        lvCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cita c = customAdapter.getItem(position);
                Toast.makeText(view.getContext(), "Id cita: " + c.getId(), Toast.LENGTH_SHORT).show();
                // TODO: Dialog mostrando información de la cita
            }
        });
        ibtnNuevaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActividadCitaNueva();
            }
        });
        return rootView;
    }

    private void iniciarActividadCitaNueva() {
        Intent i = new Intent(getActivity(), CitaFormularioActivity.class);
        i.putExtra("mascotaId", ((MascotaActivity) getActivity()).getMascota().getId());
        i.putExtra("citaId", "");
        startActivity(i);
    }

    @Override
    public void onResume() {
        Toast.makeText(ma, "REINICIAR CITAS", Toast.LENGTH_SHORT).show();
        listaCitas = daoC.obtenerCitas(ma.getMascota().getId(), ma);
        customAdapter = new CitaCustomAdapter(ma, listaCitas);
        lvCitas.setAdapter(customAdapter);
        super.onResume();
    }
}
