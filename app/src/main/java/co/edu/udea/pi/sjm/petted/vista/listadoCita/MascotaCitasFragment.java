package co.edu.udea.pi.sjm.petted.vista.listadoCita;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;

/**
 * Created by Juan on 20/09/2015.
 */
public class MascotaCitasFragment extends Fragment {

    ImageButton ibNuevaCita;

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
//        ClickMe = (Button) rootView.findViewById(R.id.button);
//        tv = (TextView) rootView.findViewById(R.id.textView2);
//        ClickMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (tv.getText().toString().contains("Hello")) {
//                    tv.setText("Hi");
//                } else tv.setText("Hello");
//            }
//        });
        ibNuevaCita = (ImageButton) rootView.findViewById(R.id.ibtnNuevaCita);
        ibNuevaCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActividadCitaNueva();
            }
        });
        return rootView;
    }

    private void iniciarActividadCitaNueva() {
        Intent i = new Intent(getActivity(), CitaFormularioActivity.class);
        i.putExtra("mascotaId",((MascotaActivity)getActivity()).getMascota().getId());
        startActivity(i);
    }
}
