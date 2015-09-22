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
 * Created by Juan on 20/09/2015.
 */
public class MascotaCitasFragment extends Fragment {

    Button ClickMe;
    TextView tv;

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
        ClickMe = (Button) rootView.findViewById(R.id.button);
        tv = (TextView) rootView.findViewById(R.id.textView2);
        ClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv.getText().toString().contains("Hello")) {
                    tv.setText("Hi");
                } else tv.setText("Hello");
            }
        });
        return rootView;
    }
}
