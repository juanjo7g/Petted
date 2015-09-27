package co.edu.udea.pi.sjm.petted.vista.mascota;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.vista.AsociarTagNFCActivity;

/**
 * Created by Juan on 20/09/2015.
 */
public class MascotaInformacionFragment extends Fragment {

    TextView tvNombre;
    TextView tvTipo;
    TextView tvRaza;
    Button btnAsociarTagNFC;
    List<String> listaProximosEventos;
    ListView lvProximosEventos;

    public MascotaInformacionFragment(){
    }

    public static MascotaInformacionFragment newInstance() {
        MascotaInformacionFragment fragment = new MascotaInformacionFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mascota_informacion, container, false);
        tvNombre = (TextView) rootView.findViewById(R.id.tvNombre);
        tvTipo = (TextView) rootView.findViewById(R.id.tvTipo);
        tvRaza = (TextView) rootView.findViewById(R.id.tvRaza);
        btnAsociarTagNFC = (Button) rootView.findViewById(R.id.btnAsociarTagNFC);
        lvProximosEventos = (ListView) rootView.findViewById(R.id.lvProximosEventos);

        MascotaActivity ma = (MascotaActivity) getActivity();
        tvNombre.setText(ma.getMascota().getNombre());
        tvTipo.setText(ma.getMascota().getTipo());
        tvRaza.setText(ma.getMascota().getRaza());
        btnAsociarTagNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AsociarTagNFCActivity.class);
                startActivity(i);
            }
        });
        listaProximosEventos = new ArrayList<>();
        listaProximosEventos.add("Vacuna 1. 23/09/16");
        listaProximosEventos.add("Cita veterinario. 23/07/15");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, listaProximosEventos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lvProximosEventos.setAdapter(adapter);
        return rootView;
    }


}
