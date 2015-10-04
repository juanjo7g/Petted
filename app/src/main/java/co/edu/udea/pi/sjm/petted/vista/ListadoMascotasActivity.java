package co.edu.udea.pi.sjm.petted.vista;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.MascotaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;
import co.edu.udea.pi.sjm.petted.vista.mascota_nueva.MascotaNuevaActivity;

public class ListadoMascotasActivity extends AppCompatActivity {


    private List<Mascota> listaMascotas;
    private ListView lvMascotas;
    private CustomAdapter customAdapter;
    private ImageButton ibtnNuevaMacota;
    private MascotaDAO dao;
    private Usuario usuarioActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_mascotas);

        usuarioActual = (Usuario) this.getIntent().getSerializableExtra("usuario");
        Toast.makeText(ListadoMascotasActivity.this, "BIENVENIDO " + usuarioActual.getNombre(), Toast.LENGTH_LONG).show();

        lvMascotas = (ListView) this.findViewById(R.id.lvListaMascotas);
        ibtnNuevaMacota = (ImageButton) this.findViewById(R.id.ibtnNuevaMascota);

        //crearMascotas();
        dao = new MascotaDAOImpl();
        listaMascotas = dao.obtenerMascotas(usuarioActual, this);
        customAdapter = new CustomAdapter(this, listaMascotas);
        lvMascotas.setAdapter(customAdapter);

        lvMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mascota m = customAdapter.getItem(position);
                iniciarActividadMascota(m);
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
        dao = new MascotaDAOImpl();
        listaMascotas = dao.obtenerMascotas(usuarioActual, this);
        customAdapter = new CustomAdapter(this, listaMascotas);
        lvMascotas.setAdapter(customAdapter);
    }

    public void iniciarActividadMascota(Mascota m) {
        Intent i = new Intent(this, MascotaActivity.class);
        i.putExtra("id", m.getId() + "");
        startActivity(i);
    }

    public void iniciarActividadMascotaNueva() {
        Intent i = new Intent(this, MascotaNuevaActivity.class);
        i.putExtra("propietario", usuarioActual);
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

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
