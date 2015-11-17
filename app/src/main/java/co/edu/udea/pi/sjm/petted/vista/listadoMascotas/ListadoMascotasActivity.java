package co.edu.udea.pi.sjm.petted.vista.listadoMascotas;


import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.MascotaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.util.Utility;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaFormularioActivity;

public class ListadoMascotasActivity extends AppCompatActivity {


    private List<Mascota> listaMascotas;
    private ListView lvMascotas;
    private MascotaCustomAdapter customAdapter;
    private ImageButton ibtnNuevaMacota;
    private MascotaDAO daoM;

    private NfcAdapter myNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        myNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        lvMascotas = (ListView) this.findViewById(R.id.lvListaMascotas);
        ibtnNuevaMacota = (ImageButton) this.findViewById(R.id.ibtnNuevaMascota);

        lvMascotas.setEmptyView(findViewById(R.id.llNoMascotas));

        onResume();

        onNewIntent(getIntent());

        lvMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mascota m = customAdapter.getItem(position);
                iniciarActividadMascota(m.getId());
            }
        });
        ibtnNuevaMacota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarActividadMascotaNueva();
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        daoM = new MascotaDAOImpl();
//        listaMascotas = daoM.obtenerMascotas(this);
//        customAdapter = new MascotaCustomAdapter(this, listaMascotas);
//        lvMascotas.setAdapter(customAdapter);
//    }

    public void iniciarActividadMascota(String mascotaId) {
        Intent i = new Intent(this, MascotaActivity.class);
        i.putExtra("mascotaId", mascotaId);
        startActivity(i);
    }

    public void iniciarActividadMascotaNueva() {
        Intent i = new Intent(this, MascotaFormularioActivity.class);
        i.putExtra("extra", "");
        startActivity(i);
    }

    @Deprecated
    public void crearMascotas() {
        listaMascotas = new ArrayList<Mascota>();

        Mascota m = new Mascota();

        m.setNombre("Toby");
        m.setTipo("Perro");
        m.setRaza("Salchicha");

        listaMascotas.add(m);

        m = new Mascota();

        m.setNombre("Uran");
        m.setTipo("Gato");
        m.setRaza("Scholtes");

        listaMascotas.add(m);
        m = new Mascota();

        m.setNombre("Malvin");
        m.setTipo("Perro");
        m.setRaza("Pastor Aleman");

        listaMascotas.add(m);
        m = new Mascota();

        m.setNombre("Socrates");
        m.setTipo("Pajaro");
        m.setRaza("Giuyha");

        listaMascotas.add(m);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listado_mascotas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_ver_perfil:
                Toast.makeText(ListadoMascotasActivity.this, "Ver perfil", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_cerrar_sesion:
                Toast.makeText(ListadoMascotasActivity.this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
                cerrarSesion();
                break;
            case R.id.action_settings:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Cerrando sesión...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(ListadoMascotasActivity.this, "Desconectado con éxito",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ListadoMascotasActivity.this, "Error cerrando sesión",
                            Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            }
        });
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (myNfcAdapter != null) {
            if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
                Tag myTagId = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                String idTag = Utility.bytesToHexString(myTagId.getId());
                Toast.makeText(ListadoMascotasActivity.this, "Taggggggg " + idTag, Toast.LENGTH_SHORT).show();
                mostrarMascota(idTag);
            } else {
            }
        }
    }

    private void mostrarMascota(String idTag) {
        Mascota mascotaNoMia;
        if (ParseUser.getCurrentUser() != null) {
            daoM = new MascotaDAOImpl();
            String idTemp = daoM.obtenerMascotaId(idTag);
            Toast.makeText(ListadoMascotasActivity.this, "Id: " + idTemp, Toast.LENGTH_SHORT).show();
            if (!idTemp.equals("")) { // Esta local
                if (daoM.obtenerMascota(idTemp, ListadoMascotasActivity.this).getPropietario()
                        .equals(ParseUser.getCurrentUser().getObjectId())) { // Es del usuario actual
                    Intent i = new Intent(this, MascotaActivity.class);
                    i.putExtra("mascotaId", idTemp);
                    startActivity(i);
                } else {
                    Toast.makeText(ListadoMascotasActivity.this, "Esta mascota es de otro " +
                            "usuario que uso este dispositivo", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(this)
                            .setTitle("Mascota")
                            .setMessage("Esta mascota es de otro usuario que uso este dispositivo, id: " + idTemp)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            } else { // Esta remota -> TODO: Necesita internet!!!
                Toast.makeText(ListadoMascotasActivity.this, "Esta mascota no esta acá por lo " +
                        "tanto no le pertenece  a ud, se va a buscar en la base de datos remota " +
                        "para ver si existe", Toast.LENGTH_LONG).show();
//                mascotaNoMia = daoM.obtenerMascotaConIdTag(idTag);
//                if (mascotaNoMia == null) {
//                    Toast.makeText(ListadoMascotasActivity.this, "El tag no esta asociado a " +
//                            "ninguna mascota", Toast.LENGTH_SHORT).show();
//                } else { //Mostrar mascota que no es mia
//                    Toast.makeText(ListadoMascotasActivity.this, "Nombre: " + mascotaNoMia.getNombre(),
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Alerta")
                    .setMessage("No ha iniciado sesión")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        daoM = new MascotaDAOImpl();
        listaMascotas = daoM.obtenerMascotas(this);
        customAdapter = new MascotaCustomAdapter(this, listaMascotas);
        lvMascotas.setAdapter(customAdapter);
    }
}
