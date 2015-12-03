package co.edu.udea.pi.sjm.petted.vista.medicamento;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.MedicamentoDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.CitaDAOImpl;
import co.edu.udea.pi.sjm.petted.dao.impl.MedicamentoDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Medicamento;
import co.edu.udea.pi.sjm.petted.util.Utility;
import co.edu.udea.pi.sjm.petted.util.Validacion;

public class MedicamentoFormularioActivity extends AppCompatActivity {

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm a", Locale.US);
    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);

    private DatePickerDialog electorDeFechaDialogo;
    private TimePickerDialog electorDeHoraDialogo;

    private String mascotaId;

    private EditText etNombre;
    private Spinner spinnerViaSuministracion;
    private EditText etCantidadDosis;
    private EditText etPesoDosis;
    private EditText etIntervaloDosis;
    private EditText etFechaInicio;
    private ImageButton ibtnFechaInicio;
    private EditText etHoraInicio;
    private ImageButton ibtnHoraInicio;

    private MedicamentoDAO daoMe;
    private Medicamento medicamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_medicamento);

        ActionBar actionBar = ((AppCompatActivity) this)
                .getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_close_white);
        }

        mascotaId = this.getIntent().getExtras().getString("mascotaId");

        etNombre = (EditText) findViewById(R.id.etNombreMedicamento);
        spinnerViaSuministracion = (Spinner) findViewById(R.id.spinnerViaSuministroMedicamento);
        etCantidadDosis = (EditText) findViewById(R.id.etCantidadDosisMedicamento);
        etPesoDosis = (EditText) findViewById(R.id.etPesoDosisMedicamento);
        etIntervaloDosis = (EditText) findViewById(R.id.etIntervaloDosisMedicamento);
        etFechaInicio = (EditText) findViewById(R.id.etFechaInicioMedicamento);
        ibtnFechaInicio = (ImageButton) findViewById(R.id.ibtnFechaInicioMedicamento);
        etHoraInicio = (EditText) findViewById(R.id.etHoraInicioMedicamento);
        ibtnHoraInicio = (ImageButton) findViewById(R.id.ibtnHoraInicioMedicamento);

        mostrarFecha();
        mostrarHora();

        if (this.getIntent().getExtras().getString("medicamentoId") != null) {
            daoMe = new MedicamentoDAOImpl();
            medicamento = daoMe.obtenerMedicamento(this.getIntent().getExtras().getString("medicamentoId"));
        }

        inicializarSpinner();

        if (this.getIntent().getExtras().getString("medicamentoId") == null) {
            super.setTitle("Nuevo Medicamento");
        } else {
            inicializarFormulario();
            super.setTitle("Editar Medicamento");
        }
    }

    private void inicializarFormulario() {
        etNombre.setText(medicamento.getNombre());
        spinnerViaSuministracion.setSelection(Utility.getIndex(spinnerViaSuministracion, medicamento.getViaSuministro()));
        etCantidadDosis.setText(medicamento.getCantidadDosis() + "");
        etPesoDosis.setText(medicamento.getPesoDosis() + "");
        etIntervaloDosis.setText(medicamento.getIntervaloDosis() + "");
        if (medicamento.getFechaHoraInicio() != null) {
            etFechaInicio.setText(formatoFecha.format(medicamento.getFechaHoraInicio()));
            etHoraInicio.setText(formatoHora.format(medicamento.getFechaHoraInicio()));
        }

    }

    public void onClickGuardarMedicamento() {

        verificarCamposRequeridos();

        Medicamento m;
        UUID uuid = UUID.randomUUID();
        m = new Medicamento();

        m.setId(uuid.toString());
        m.setMascota(mascotaId);
        m.setNombre(etNombre.getText().toString());
        m.setViaSuministro((String) spinnerViaSuministracion.getSelectedItem());
        if (!etCantidadDosis.getText().toString().equals("")) {
            m.setCantidadDosis(Integer.parseInt(etCantidadDosis.getText().toString()));
        }
        if (!etPesoDosis.getText().toString().equals("")) {
            m.setPesoDosis(Double.parseDouble(etPesoDosis.getText().toString()));
        }
        if (!etIntervaloDosis.getText().toString().equals("")) {
            m.setIntervaloDosis(Integer.parseInt(etIntervaloDosis.getText().toString()));
        }
        if (!etFechaInicio.getText().toString().equals("")) {
            try {
                m.setFechaHoraInicio(formatoFechaHora.parse(etFechaInicio.getText().toString() + " "
                        + etHoraInicio.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
                Log.e("Error en fecha", e.getMessage());
            }
        }

        switch (Validacion.validarMedicamento(m)) {
            case 0:
                if ((etNombre.getError() == null) && (etCantidadDosis.getError() == null) &&
                        (etPesoDosis.getError() == null) && (etIntervaloDosis.getError() == null)) {
                    if (this.getIntent().getExtras().getString("medicamentoId") == null) {
                        daoMe = new MedicamentoDAOImpl();
                        daoMe.insertarMedicamento(m);
                    } else {
                        daoMe = new MedicamentoDAOImpl();
                        m.setId(this.getIntent().getExtras().getString("medicamentoId"));
                        daoMe.actualizarMedicamento(m);
                        setResult(0);
                    }
                    finish();
                }
                break;
        }
    }

    private void verificarCamposRequeridos() {
        if (etNombre.getText().toString().equals("")) {
            etNombre.setError("Campo requerido.");
        }
        if (etCantidadDosis.getText().toString().equals("")) {
            etCantidadDosis.setError("Campo requerido.");
        }
        if (etPesoDosis.getText().toString().equals("")) {
            etPesoDosis.setError("Campo requerido.");
        }
        if (etIntervaloDosis.getText().toString().equals("")) {
            etIntervaloDosis.setError("Campo requerido.");
        }
    }

    private void mostrarFecha() {
        ibtnFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electorDeFechaDialogo.show();
            }
        });
        etFechaInicio.setOnClickListener(new View.OnClickListener() {
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
                etFechaInicio.setText(formatoFecha.format(nuevaFecha.getTime()));
                if (etHoraInicio.getText().toString().equals("")) {
                    etHoraInicio.setText(formatoHora.format(nuevaFecha.getTime()));
                }
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
    }

    private void mostrarHora() {
        ibtnHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electorDeHoraDialogo.show();
            }
        });
        etHoraInicio.setOnClickListener(new View.OnClickListener() {
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
                nuevaHora.set(Calendar.HOUR, selectedHour);
                nuevaHora.set(Calendar.MINUTE, selectedMinute);
                if (Calendar.AM_PM == 0) {
                    nuevaHora.set(Calendar.AM_PM, 1);
                }
                nuevaHora.set(Calendar.AM_PM, 0);
                etHoraInicio.setText(formatoHora.format(nuevaHora.getTime()));
            }
        }, calendario.get(Calendar.HOUR), calendario.get(Calendar.MINUTE), false);
    }


    private void inicializarSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.TiposDeViaDeSuministroMedicamento,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerViaSuministracion.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario_medicamento, menu);
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
                onClickGuardarMedicamento();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
