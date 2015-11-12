package co.edu.udea.pi.sjm.petted.vista.mascota;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.util.Utility;

/**
 * Created by Juan on 20/09/2015.
 */
public class MascotaInformacionFragment extends Fragment {

    private TextView tvNombre;
    private TextView tvTipo;
    private TextView tvRaza;
    private Button btnAsociarTagNFC;
    private List<String> listaProximosEventos;
    private ListView lvProximosEventos;
    private ImageView ivFoto;

    private MascotaActivity ma;

    public MascotaInformacionFragment() {
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
        ivFoto = (ImageView) rootView.findViewById(R.id.ivFoto);

        ma = (MascotaActivity) getActivity();

        tvNombre.setText(ma.getMascota().getNombre());
        tvTipo.setText(ma.getMascota().getTipo());
        tvRaza.setText(ma.getMascota().getRaza());
        ivFoto.setImageBitmap(Utility.getCircleBitmap(Utility.getFoto(ma.getMascota().getFoto())));

        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarFoto(ma.getMascota().getFoto(), ma.getMascota().getNombre());
            }
        });

        btnAsociarTagNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TagNFCFormularioActivity.class);
                i.putExtra("mascotaId", ma.getMascota().getId());
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

    private void mostrarFoto(byte[] foto, String titulo) {
        Dialog dialog = new Dialog(ma);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(titulo);
        ImageView image = (ImageView) dialog.findViewById(R.id.ivImagen);
        image.setImageBitmap(Utility.getFoto(foto));
        dialog.show();
    }


}
