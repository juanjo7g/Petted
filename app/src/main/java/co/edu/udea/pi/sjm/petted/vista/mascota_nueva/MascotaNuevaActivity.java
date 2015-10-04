package co.edu.udea.pi.sjm.petted.vista.mascota_nueva;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.Validacion;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.Utility;
import co.edu.udea.pi.sjm.petted.dao.impl.MascotaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

public class MascotaNuevaActivity extends AppCompatActivity {


    private EditText etFechaNacimiento;
    private DatePickerDialog electorDeFechaDialogo;
    private SimpleDateFormat formateadorDeFecha;
    private Spinner spinnerTipoMascota;
    private Spinner spinnerRazaMascota;
    private Spinner spinnerGenero;
    private EditText etNombreMascota;
    private ImageButton ibFechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_nueva);

        // Obtener instancia de la action bar
        ActionBar actionBar = ((AppCompatActivity) this)
                .getSupportActionBar();

        if (actionBar != null) {
            // Habilitar el Up Button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Cambiar icono del Up Button
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_close_white);
        }


        etFechaNacimiento = (EditText) findViewById(R.id.etFechaNacimiento);
        spinnerTipoMascota = (Spinner) findViewById(R.id.spinnerTipoMascota);
        spinnerRazaMascota = (Spinner) findViewById(R.id.spinnerRazaMascota);
        spinnerGenero = (Spinner) findViewById(R.id.spinnerGenero);
        etNombreMascota = (EditText) findViewById(R.id.etNombreMascota);
        ibFechaNacimiento = (ImageButton)findViewById(R.id.ibFechaNacimiento);


        formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        etFechaNacimiento.setInputType(InputType.TYPE_NULL);
        etFechaNacimiento.requestFocus();

        mostrarFecha();

        inicializarSpinners();

    }

    @Deprecated
    public void onClickAñadirMascota(View view) {
        MascotaDAO dao = new MascotaDAOImpl();
        Mascota m;
        Usuario u;
        u = (Usuario) this.getIntent().getSerializableExtra("propietario");
        m = new Mascota();
        m.setNombre(etNombreMascota.getText().toString());
        m.setPropietario(u);
        m.setFoto(Utility.resizeImage(this, R.drawable.mascota1, 300, 300));

        switch (Validacion.validarMascota(m)) {
            case 0:
                dao.insertarMascota(m, this);
                Toast.makeText(MascotaNuevaActivity.this, m.getNombre(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case 1:
                break;
        }
    }

    public void onClickAñadirMascota() {
        MascotaDAO dao = new MascotaDAOImpl();
        Mascota m;
        Usuario u;
        u = (Usuario) this.getIntent().getSerializableExtra("propietario");
        m = new Mascota();
        m.setNombre(etNombreMascota.getText().toString());
        m.setPropietario(u);
        m.setFoto(Utility.resizeImage(this, R.drawable.mascota1, 300, 300));

        switch (Validacion.validarMascota(m)) {
            case 0:
                dao.insertarMascota(m, this);
                Toast.makeText(MascotaNuevaActivity.this, m.getNombre(), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case 1:
                break;
        }
    }

    private void mostrarFecha() {


        ibFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electorDeFechaDialogo.show();
            }
        });

        Calendar calendario = Calendar.getInstance();
        electorDeFechaDialogo = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int año, int mes, int dia) {
                Calendar nuevaFecha = Calendar.getInstance();
                nuevaFecha.set(año, mes, dia);
                etFechaNacimiento.setText(formateadorDeFecha.format(nuevaFecha.getTime()));
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
    }

    private void onSelectedItemTiposDeMascotas(View view, int posicion) {
        try {
            ArrayAdapter<CharSequence> adapter1 = null;
            switch (posicion) {
                case 0:
                    adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDeGatos,
                            android.R.layout.simple_dropdown_item_1line);
                    break;
                case 1:
                    adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDePerros,
                            android.R.layout.simple_dropdown_item_1line);
                    break;
                case 2:
                    adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDeAves,
                            android.R.layout.simple_dropdown_item_1line);
                    break;
                case 3:
                    adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDeReptiles,
                            android.R.layout.simple_dropdown_item_1line);
                    break;
                case 4:
                    adapter1 = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasOtro,
                            android.R.layout.simple_dropdown_item_1line);
                    break;
            }
            spinnerRazaMascota.setAdapter(adapter1);
        } catch (Exception e) {
        }
    }

    private void inicializarSpinners() {
        ArrayAdapter<CharSequence> adapter;

        adapter = ArrayAdapter.createFromResource(this, R.array.TiposDeMascotas,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipoMascota.setAdapter(adapter);
        spinnerTipoMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSelectedItemTiposDeMascotas(view, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        adapter = ArrayAdapter.createFromResource(this, R.array.Generos,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerGenero.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mascota_nueva, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_guardar:
                //TODO: Guardar mascota
                onClickAñadirMascota();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
