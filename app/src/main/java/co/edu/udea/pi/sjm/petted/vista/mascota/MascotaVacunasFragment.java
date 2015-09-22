package co.edu.udea.pi.sjm.petted.vista.mascota;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import co.edu.udea.pi.sjm.petted.R;

/**
 * Created by Juan on 20/09/2015.
 */
public class MascotaVacunasFragment extends Fragment {

    public MascotaVacunasFragment() {
    }

    public static MascotaVacunasFragment newInstance() {
        MascotaVacunasFragment fragment = new MascotaVacunasFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mascota_vacunas, container, false);
        return rootView;
    }
}
