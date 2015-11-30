package co.edu.udea.pi.sjm.petted.vista.listadoCita;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
        ma = (MascotaActivity) getActivity();

        lvCitas = (ListView) rootView.findViewById(R.id.lvListaCitas);
        ibtnNuevaCita = (ImageButton) rootView.findViewById(R.id.ibtnNuevaCita);

        listaCitas = new ArrayList<>();

        daoC = new CitaDAOImpl();

        lvCitas.setEmptyView(rootView.findViewById(R.id.llNoCitas));
        onResume();

        lvCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Cita c = customAdapter.getItem(position);
                Toast.makeText(view.getContext(), "Id cita: " + c.getId(), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(ma)
                        .setTitle("Cita " + c.getNombre())
                        .setMessage("Descrpción: " + c.getDescripcion() +
                                "\nTipo: " + c.getTipo() +
                                "\nFechaHora: ")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ma, "Cerrar cita", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("Editar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ma, "Editar cita", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(ma)
                                        .setTitle("Eliminar cita")
                                        .setMessage("¿Desea eliminar a " + c.getNombre() + "?")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                daoC.eliminarCita(c, ma);
                                                onResume();
                                            }
                                        })
                                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_delete)
                                        .show();

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
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
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        listaCitas = daoC.obtenerCitas(ma.getMascota().getId(), ma);
        customAdapter = new CitaCustomAdapter(ma, listaCitas);
        lvCitas.setAdapter(customAdapter);
    }
}
