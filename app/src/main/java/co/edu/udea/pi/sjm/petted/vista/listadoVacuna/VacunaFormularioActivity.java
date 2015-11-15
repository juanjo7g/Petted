package co.edu.udea.pi.sjm.petted.vista.listadoVacuna;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.VacunaDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.MascotaDAOImpl;
import co.edu.udea.pi.sjm.petted.dao.impl.VacunaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;
import co.edu.udea.pi.sjm.petted.util.Utility;
import co.edu.udea.pi.sjm.petted.util.Validacion;
import co.edu.udea.pi.sjm.petted.vista.listadoCita.CitaFormularioActivity;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;

public class VacunaFormularioActivity extends AppCompatActivity {

    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    private DatePickerDialog electorDeFechaDialogo;
    private DatePickerDialog electorDeFechaProximaDialogo;

    private TextView etNombre;
    private EditText etFecha;
    private ImageButton ibtnFecha;
    private EditText etFechaProxima;
    private ImageButton ibtnFechaProxima;
    private ImageView ivFotoPrevia;
    private Bitmap foto;

    private String mascotaId;

    private VacunaDAO daoV;

    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_vacuna);

        // Obtener instancia de la action bar
        ActionBar actionBar = ((AppCompatActivity) this)
                .getSupportActionBar();

        if (actionBar != null) {
            // Habilitar el Up Button
            actionBar.setDisplayHomeAsUpEnabled(true);
            // Cambiar icono del Up Button
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_close_white);
        }

        mascotaId = this.getIntent().getExtras().getString("mascotaId");

        etNombre = (EditText) findViewById(R.id.etNombreVacuna);
        etFecha = (EditText) findViewById(R.id.etFechaVacuna);
        ibtnFecha = (ImageButton) findViewById(R.id.ibtnFechaVacuna);
        etFechaProxima = (EditText) findViewById(R.id.etFechaProximaVacuna);
        ibtnFechaProxima = (ImageButton) findViewById(R.id.ibtnFechaProximaVacuna);
        ivFotoPrevia = (ImageView) findViewById(R.id.ivFotoPreviaValidacionVacuna);

        mostrarFecha();

        if (this.getIntent().getExtras().getString("vacunaId") == null) {
            super.setTitle("Nueva Vacuna");
        } else {
//            inicializarFormulario((Vacuna) this.getIntent().getExtras().getSerializable("vacuna"));
            super.setTitle("Editar Vacuna");
        }

    }

    private void onClickGuardarVacuna() {
        Vacuna v;
        UUID uuid = UUID.randomUUID();
        v = new Vacuna();

        v.setId(uuid.toString());
        v.setMascota(mascotaId);
        v.setNombre(etNombre.getText().toString());
        try {
            v.setFecha(formatoFecha.parse(etFecha.getText().toString()));
            v.setFechaProxima(formatoFecha.parse(etFechaProxima.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (foto != null) {
            v.setValidacion(Utility.getBytes(Utility.resizeImage(foto, 200, 200)));
        } else {
            v.setValidacion(Utility.getBytes(Utility.resizeImage(this, R.drawable.no_valido, 200, 200)));
        }

        switch (Validacion.validarVacuna(v)) {
            case 0:
                if (this.getIntent().getExtras().getSerializable("vacuna") == null) {
                    daoV = new VacunaDAOImpl();
                    daoV.insertarVacuna(v, this);
                    Intent i = new Intent(this, CitaFormularioActivity.class);
                    i.putExtra("mascotaId", mascotaId);
                    i.putExtra("vacuna", v);
                    startActivity(i);
                } else {
                    // TODO: EDITAR VACUNA
//                    v.setId(((Vacuna) this.getIntent().getExtras().getSerializable("vacuna")).getId());
//                    v.setEstado(((Vacuna) this.getIntent().getExtras().getSerializable("vacuna")).getEstado());

                    daoV.actualizarVacuna(v, this);

                    Toast.makeText(VacunaFormularioActivity.this, "Vacuna Editada", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
        }

    }

    public void onClickFotoValidacionVacuna(View view) {
        final CharSequence[] opciones = {"Tomar Foto", "Seleccionar foto", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(VacunaFormularioActivity.this);
        builder.setTitle("Selecciona una opción: ");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int seleccion) {
                switch (seleccion) {
                    case 0:
                        tomarFoto();
                        break;
                    case 1:
                        seleccionarFoto();
                        break;
                    case 2:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    // TODO: INICIALIZAR FORMULARIO VACUNA PARA EDITAR
    private void inicializarFormulario(Vacuna vacuna) {
    }

    public void tomarFoto() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();

        String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                + File.separator + TEMPORAL_PICTURE_NAME;
        File newFile = new File(path);

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));

        startActivityForResult(i, PHOTO_CODE);
    }

    public void seleccionarFoto() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Selecciona app de imagen"), SELECT_PICTURE);
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
                etFecha.setText(formatoFecha.format(nuevaFecha.getTime()));
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));

        ibtnFechaProxima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electorDeFechaProximaDialogo.show();
            }
        });
        calendario = Calendar.getInstance();
        electorDeFechaProximaDialogo = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int año, int mes, int dia) {
                Calendar nuevaFecha = Calendar.getInstance();
                nuevaFecha.set(año, mes, dia);
                etFechaProxima.setText(formatoFecha.format(nuevaFecha.getTime()));
            }
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                            + File.separator + TEMPORAL_PICTURE_NAME;
                    foto = BitmapFactory.decodeFile(dir);
                    ivFotoPrevia.setImageBitmap(foto);
                }
                break;
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri dir = data.getData();
                    try {
                        foto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dir);
                        ivFotoPrevia.setImageBitmap(foto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
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
                onClickGuardarVacuna();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
