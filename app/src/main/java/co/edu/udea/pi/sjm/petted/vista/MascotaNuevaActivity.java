package co.edu.udea.pi.sjm.petted.vista;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;

public class MascotaNuevaActivity extends AppCompatActivity {


    private EditText etFechaNacimiento;
    private DatePickerDialog electorDeFechaDialogo;
    private SimpleDateFormat formateadorDeFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascota_nueva);

        etFechaNacimiento = (EditText) findViewById(R.id.etFechaNacimiento);

        formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        etFechaNacimiento.setInputType(InputType.TYPE_NULL);
        etFechaNacimiento.requestFocus();

        mostrarFecha();
    }

    private void findViewsById() {

    }

    private void mostrarFecha() {
        etFechaNacimiento.setOnClickListener(new View.OnClickListener() {
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
