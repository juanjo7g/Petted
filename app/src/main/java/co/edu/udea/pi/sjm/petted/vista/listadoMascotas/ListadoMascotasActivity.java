package co.edu.udea.pi.sjm.petted.vista.listadoMascotas;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import co.edu.udea.pi.sjm.petted.vista.MainActivity;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaFormularioActivity;

public class ListadoMascotasActivity extends AppCompatActivity {


    private List<Mascota> listaMascotas;
    private ListView lvMascotas;
    private MascotaCustomAdapter customAdapter;
    private ImageButton ibtnNuevaMacota;
    private MascotaDAO daoM;

    private Mascota mascotaNoMia;

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
                cerrarSesion();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {

        ParseUser.logOut();
        Toast.makeText(ListadoMascotasActivity.this, "Desconectado con éxito.", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (myNfcAdapter != null) {
            if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
                Tag myTagId = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                String idTag = Utility.bytesToHexString(myTagId.getId());
                mostrarMascota(idTag);
            } else {
            }
        }
    }

    private void mostrarMascota(final String idTag) {
        if (ParseUser.getCurrentUser() != null) {
            daoM = new MascotaDAOImpl();
            String idTemp = daoM.obtenerMascotaId(idTag);

            if (!idTemp.equals("")) { // Esta local
                if (daoM.obtenerMascota(idTemp).getPropietario()
                        .equals(ParseUser.getCurrentUser().getObjectId())) { // Es del usuario actual
                    Intent i = new Intent(this, MascotaActivity.class);
                    i.putExtra("mascotaId", idTemp);
                    startActivity(i);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("Mascota")
                            .setMessage("Esta esta en el dispositivo pero no te pertenece, id: " + idTemp)
                            .setPositiveButton("Ver", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    new AlertDialog.Builder(ListadoMascotasActivity.this)
                                            .setTitle("Mascota")
                                            .setMessage("Nombre:  " + daoM.obtenerMascotaConIdTag(idTag).getNombre())
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .setNeutralButton("Encontré esta mascota", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_info)
                                            .show();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            } else { // Esta remota -> TODO: Necesita internet!!!

                new AlertDialog.Builder(this)
                        .setTitle("Mascota")
                        .setMessage("Esta mascota no se encontró en este dispositvo, se va a buscar " +
                                "en la base de datos remota para ver si existe.")
                        .setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (!Utility.isOnline()) {
                                    new AlertDialog.Builder(ListadoMascotasActivity.this)
                                            .setTitle("Sin conexión")
                                            .setMessage("Conexión a internet necesaria para realizar la busqueda.")
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                } else {
                                    mascotaNoMia = daoM.obtenerMascotaConIdTag(idTag);
                                    if (mascotaNoMia == null) {
                                        new AlertDialog.Builder(ListadoMascotasActivity.this)
                                                .setTitle("Mascota")
                                                .setMessage("El tag no esta asociado a ninguna mascota.")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    } else { //Mostrar mascota que no es mia
                                        new AlertDialog.Builder(ListadoMascotasActivity.this)
                                                .setTitle("Mascota encontrada")
                                                .setMessage("Nombre: " + mascotaNoMia.getNombre())
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .setNeutralButton("Encontré esta mascota", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_info)
                                                .show();
                                    }
                                }

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No ha iniciado sesión")
                    .setMessage("Es necesario iniciar sesión para acceder a esta funcionalidad.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(ListadoMascotasActivity.this, MainActivity.class);
                            finish();
                            startActivity(i);
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Intent i = new Intent(ListadoMascotasActivity.this, MainActivity.class);
                            finish();
                            startActivity(i);
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
        listaMascotas = daoM.obtenerMascotas();
        customAdapter = new MascotaCustomAdapter(this, listaMascotas);
        lvMascotas.setAdapter(customAdapter);
    }
}
