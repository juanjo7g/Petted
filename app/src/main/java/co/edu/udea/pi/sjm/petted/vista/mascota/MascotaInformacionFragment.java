package co.edu.udea.pi.sjm.petted.vista.mascota;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    private TextView tvEdad;
    private TextView tvGenero;
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
        ivFoto = (ImageView) rootView.findViewById(R.id.ivFoto);
        tvEdad = (TextView) rootView.findViewById(R.id.tvEdad);
        tvGenero = (TextView) rootView.findViewById(R.id.tvGenero);

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
        if (ma.getMascota().getFechaNacimiento() != null) {
            int edad = Utility.getAge(ma.getMascota().getFechaNacimiento());
            int meses = Utility.getMonths(ma.getMascota().getFechaNacimiento());
            if (edad < 0) {
                tvEdad.setText(R.string.no_ha_nacido);
            } else if (edad == 0) {
                if (meses == 1) {
                    tvEdad.setText(R.string.un_mes);
                } else {
                    tvEdad.setText(meses + ma.getString(R.string._meses));
                }
            } else if (edad == 1) {
                tvEdad.setText(R.string.un_año);
            } else {
                tvEdad.setText(edad + ma.getString(R.string._años));
            }
        }
        tvGenero.setText(ma.getMascota().getGenero());

        btnAsociarTagNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickAsociarTagNFC();

            }
        });

        return rootView;
    }

    private void onclickAsociarTagNFC() {
        if (NfcAdapter.getDefaultAdapter(ma) == null) {
            new AlertDialog.Builder(ma)
                    .setTitle(R.string.no_nfc)
                    .setMessage(R.string.no_funcionalidad_nfc)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            Intent i = new Intent(getActivity(), TagNFCFormularioActivity.class);
            i.putExtra("mascotaId", ma.getMascota().getId());
            startActivity(i);
        }
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
