package co.edu.udea.pi.sjm.petted.vista.mascota;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.pi.sjm.petted.R;

/**
 * Created by Juan on 20/09/2015.
 */
public class MascotaInformacionFragment extends Fragment {

    TextView tvNombre;
    TextView tvTipo;
    TextView tvRaza;

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
        MascotaActivity ma = (MascotaActivity) getActivity();
        tvNombre.setText(ma.getMascota().getNombre());
        tvTipo.setText(ma.getMascota().getTipo());
        tvRaza.setText(ma.getMascota().getRaza());
        return rootView;
    }
}
