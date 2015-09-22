package co.edu.udea.pi.sjm.petted.vista.mascota;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.edu.udea.pi.sjm.petted.R;

/**
 * Created by Juan on 21/09/2015.
 */
public class MascotaMedicamentosFragment extends Fragment {

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
        return rootView;
    }
}