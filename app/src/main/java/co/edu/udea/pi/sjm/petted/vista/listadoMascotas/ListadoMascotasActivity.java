package co.edu.udea.pi.sjm.petted.vista.listadoMascotas;


import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.MascotaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaFormularioActivity;

public class ListadoMascotasActivity extends AppCompatActivity {


    private List<Mascota> listaMascotas;
    private ListView lvMascotas;
    private MascotaCustomAdapter customAdapter;
    private ImageButton ibtnNuevaMacota;
    private MascotaDAO daoM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        lvMascotas = (ListView) this.findViewById(R.id.lvListaMascotas);
        ibtnNuevaMacota = (ImageButton) this.findViewById(R.id.ibtnNuevaMascota);

        daoM = new MascotaDAOImpl();
        listaMascotas = daoM.obtenerMascotas(this);

        customAdapter = new MascotaCustomAdapter(this, listaMascotas);
        lvMascotas.setAdapter(customAdapter);

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

    @Override
    protected void onResume() {
        super.onResume();
        daoM = new MascotaDAOImpl();
        listaMascotas = daoM.obtenerMascotas(this);
        customAdapter = new MascotaCustomAdapter(this, listaMascotas);
        lvMascotas.setAdapter(customAdapter);
    }

    public void iniciarActividadMascota(String mascotaId) {
        Intent i = new Intent(this, MascotaActivity.class);
        i.putExtra("mascotaId", mascotaId);
        startActivity(i);
    }

    public void iniciarActividadMascotaNueva() {
        Intent i = new Intent(this, MascotaFormularioActivity.class);
        i.putExtra("mascotaId", "");
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
        }

        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
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
            }
        });
        finish();
//        System.exit(0);
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
    }
}
