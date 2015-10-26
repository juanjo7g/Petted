package co.edu.udea.pi.sjm.petted.vista.listadoCita;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dto.Cita;

public class CitaFormularioActivity extends AppCompatActivity {

    private DatePickerDialog electorDeFechaDialogo;
    private SimpleDateFormat formateadorDeFecha;
    private TimePickerDialog electorDeHoraDialogo;
    private SimpleDateFormat formateadorDeHora;

    private TextView tvMascotaId;
    private String mascotaId;
    private EditText etNombre;
    private EditText etDescripcion;
    private Spinner spinnerTipo;
    private EditText etFecha;
    private ImageButton ibtnFecha;
    private EditText etHora;
    private ImageButton ibtnHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cita);

        // Obtener instancia de la action bar
        ActionBar actionBar = ((AppCompatActivity) this)
                .getSupportActionBar();

        if (actionBar != null) {
            // Habilitar el Up Button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Cambiar icono del Up Button
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_close_white);
        }

        tvMascotaId = (TextView) findViewById(R.id.tvMascotaId);
        tvMascotaId.setText(this.getIntent().getExtras().getString("mascotaId"));
        mascotaId = this.getIntent().getExtras().getString("mascotaId");

        etNombre = (EditText) findViewById(R.id.etNombreCita);
        etDescripcion = (EditText) findViewById(R.id.etDescripcionCita);
        spinnerTipo = (Spinner) findViewById(R.id.spinnerTipoCita);
        etFecha = (EditText) findViewById(R.id.etFechaCita);
        ibtnFecha = (ImageButton) findViewById(R.id.ibtnFechaCita);
        etHora = (EditText) findViewById(R.id.etHoraCita);
        ibtnHora = (ImageButton) findViewById(R.id.ibtnHoraCita);

        formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        formateadorDeHora = new SimpleDateFormat("hh:mm a", Locale.US);

        mostrarFecha();
        mostrarHora();

        inicializarSpinner();

        if (this.getIntent().getExtras().getSerializable("cita") != null) {
            inicializarFormulario((Cita) this.getIntent().getExtras().getSerializable("cita"));
            super.setTitle("Editar Cita");
        } else {
            super.setTitle("Nueva Cita");
        }

    }

    private void inicializarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TiposDeCitas,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipo.setAdapter(adapter);
    }

    // TODO: INICIALIZAR FORMULARIO
    private void inicializarFormulario(Cita cita) {
    }

    private void mostrarFecha() {

        ibtnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electorDeFechaDialogo.show();
            }
        });

        Calendar calendario = Calendar.getInstance();
        electorDeFechaDialogo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int año, int mes, int dia) {
                Calendar nuevaFecha = Calendar.getInstance();
                nuevaFecha.set(año, mes, dia);
                etFecha.setText(formateadorDeFecha.format(nuevaFecha.getTime()));
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
    }

    private void mostrarHora() {
        ibtnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electorDeHoraDialogo.show();
            }
        });
        Calendar calendario = Calendar.getInstance();
        electorDeHoraDialogo = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Calendar nuevaHora = Calendar.getInstance();
                //Toast.makeText(CitaFormularioActivity.this, nuevaHora.get(Calendar.AM_PM), Toast.LENGTH_SHORT).show();
                nuevaHora.set(Calendar.HOUR, selectedHour);
                nuevaHora.set(Calendar.MINUTE, selectedMinute);
                if (Calendar.AM_PM == 0) {
                    nuevaHora.set(Calendar.AM_PM, 1);
                }
                nuevaHora.set(Calendar.AM_PM, 0);
                etHora.setText(formateadorDeHora.format(nuevaHora.getTime()));
            }
        }, calendario.get(Calendar.HOUR), calendario.get(Calendar.MINUTE), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario_cita, menu);
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
                Toast.makeText(CitaFormularioActivity.this, "Guardar", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
