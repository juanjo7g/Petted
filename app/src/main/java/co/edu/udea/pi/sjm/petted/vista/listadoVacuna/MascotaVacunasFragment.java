package co.edu.udea.pi.sjm.petted.vista.listadoVacuna;

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
import android.widget.ImageView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.VacunaDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.VacunaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;
import co.edu.udea.pi.sjm.petted.util.Utility;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;

/**
 * Created by Juan on 20/09/2015.
 */
public class MascotaVacunasFragment extends Fragment {

    private ListView lvVacunas;
    private ImageButton ibtnNuevaVacuna;
    private VacunaDAO daoV;
    private List<Vacuna> listaVacunas;
    private VacunaCustomAdapter customAdapter;
    private MascotaActivity ma;

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

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
        ma = (MascotaActivity) getActivity();

        lvVacunas = (ListView) rootView.findViewById(R.id.lvListaVacunas);
        ibtnNuevaVacuna = (ImageButton) rootView.findViewById(R.id.ibtnNuevaVacuna);

        listaVacunas = new ArrayList<>();

        daoV = new VacunaDAOImpl();

        lvVacunas.setEmptyView(rootView.findViewById(R.id.llNoVacunas));
        onResume();

        lvVacunas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Vacuna v = customAdapter.getItem(position);
                // TODO: Dialog mostrando información de la vacuna compatible con api 15
                AlertDialog a = null;
                String fechaS;
                String fechaProximaS;
                if (v.getFechaProxima() != null) {
                    fechaS = "Fecha en que se aplicó la vacuna: " + formatoFecha.format(v.getFecha());
                } else {
                    fechaS = "";
                }
                if (v.getFechaProxima() != null) {
                    fechaProximaS = "Fecha para aplicar la próxima vacuna: " + formatoFecha.format(v.getFechaProxima());
                } else {
                    fechaProximaS = "";
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    a = new AlertDialog.Builder(ma)
                            .setTitle("Vacuna " + v.getNombre())
                            .setMessage(fechaS + "\n" + fechaProximaS + "\n\nValidación: ")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setNeutralButton("Editar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(ma, VacunaFormularioActivity.class);
                                    i.putExtra("vacunaId", v.getId());
                                    startActivity(i);
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new AlertDialog.Builder(ma)
                                            .setTitle("Eliminar Vacuna")
                                            .setMessage("¿Desea eliminar a " + v.getNombre() + "?")
                                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    daoV.eliminarVacuna(v);
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
                            .setView(R.layout.custom_dialog)
                            .show();
                }
                ImageView image = (ImageView) a.findViewById(R.id.ivImagen);
                image.setImageBitmap(Utility.getFoto(v.getValidacion()));
            }
        });
        ibtnNuevaVacuna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActividadVacunaNueva();
            }
        });

        return rootView;
    }

    private void iniciarActividadVacunaNueva() {
        Intent i = new Intent(ma, VacunaFormularioActivity.class);
        i.putExtra("mascotaId", ma.getMascota().getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        super.onResume();
        listaVacunas = daoV.obtenerVacunas(ma.getMascota().getId());
        customAdapter = new VacunaCustomAdapter(ma, listaVacunas);
        lvVacunas.setAdapter(customAdapter);
    }
}
