package co.edu.udea.pi.sjm.petted.vista.listadoVacuna;

import android.app.Dialog;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        lvVacunas = (ListView) rootView.findViewById(R.id.lvListaVacunas);
        ibtnNuevaVacuna = (ImageButton) rootView.findViewById(R.id.ibtnNuevaVacuna);

        listaVacunas = new ArrayList<>();

        daoV = new VacunaDAOImpl();
        ma = (MascotaActivity) getActivity();

        listaVacunas = daoV.obtenerVacunas(ma.getMascota().getId(), ma);

        customAdapter = new VacunaCustomAdapter(ma, listaVacunas);
        lvVacunas.setAdapter(customAdapter);

        lvVacunas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vacuna v = customAdapter.getItem(position);
                Toast.makeText(view.getContext(), "Id vacuna: " + v.getId(), Toast.LENGTH_SHORT).show();
                // TODO: Dialog mostrando información de la vacuna
                mostrarFoto(v.getValidacion());
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

    private void mostrarFoto(byte[] v) {
        Dialog dialog = new Dialog(ma);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Validación de la vacuna");
        ImageView image = (ImageView) dialog.findViewById(R.id.ivImagen);
        image.setImageBitmap(Utility.getFoto(v));
        dialog.show();
    }

    private void iniciarActividadVacunaNueva() {
        Intent i = new Intent(getActivity(), VacunaFormularioActivity.class);
        i.putExtra("mascotaId", ((MascotaActivity) getActivity()).getMascota().getId());
        startActivity(i);
    }

    @Override
    public void onResume() {
        Toast.makeText(ma, "REINICIAR VACUNAS", Toast.LENGTH_SHORT).show();
        listaVacunas = daoV.obtenerVacunas(ma.getMascota().getId(), ma);
        customAdapter = new VacunaCustomAdapter(ma, listaVacunas);
        lvVacunas.setAdapter(customAdapter);
        super.onResume();
    }
}
