package co.edu.udea.pi.sjm.petted.vista.mascota_nueva;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.util.Validacion;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.util.Utility;
import co.edu.udea.pi.sjm.petted.dao.impl.MascotaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;
import co.edu.udea.pi.sjm.petted.vista.mascota.MascotaActivity;

public class MascotaFormularioActivity extends AppCompatActivity {


    private EditText etFechaNacimiento;
    private DatePickerDialog electorDeFechaDialogo;
    private SimpleDateFormat formateadorDeFecha;
    private Spinner spinnerTipoMascota;
    private Spinner spinnerRazaMascota;
    private Spinner spinnerGenero;
    private EditText etNombreMascota;
    private ImageButton ibFechaNacimiento;
    private Button btnFoto;
    private ImageView ivFotoPrevia;
    private Bitmap foto;

    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";


    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

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
        ibFechaNacimiento = (ImageButton) findViewById(R.id.ibFechaNacimiento);
        btnFoto = (Button) findViewById(R.id.btnFoto);
        ivFotoPrevia = (ImageView) findViewById(R.id.ivFotoPrevia);

        formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        etFechaNacimiento.setInputType(InputType.TYPE_NULL);
        etFechaNacimiento.requestFocus();

        mostrarFecha();

        inicializarSpinners();

        if (this.getIntent().getExtras().getSerializable("mascota") != null) {
            inicializarFormulario((Mascota) this.getIntent().getExtras().getSerializable("mascota"));
            super.setTitle("Editar Mascota");
        }

    }

    private void inicializarFormulario(Mascota mascota) {
        etNombreMascota.setText(mascota.getNombre());
        SimpleDateFormat formateadorDeFecha;
        formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        if (mascota.getFechaNacimiento() != null) {
            etFechaNacimiento.setText(formateadorDeFecha.format(mascota.getFechaNacimiento()));
        }

        int posicion = Utility.getIndex(spinnerTipoMascota, mascota.getTipo());
        spinnerTipoMascota.setSelection(posicion);

        spinnerGenero.setSelection(Utility.getIndex(spinnerGenero, mascota.getGenero()));

        if (mascota.getFoto() != null) {
            foto = Utility.getFoto(mascota.getFoto());
            ivFotoPrevia.setImageBitmap(foto);
        }

    }

    public void onClickFoto(View view) {
        final CharSequence[] opciones = {"Tomar Foto", "Seleccionar foto", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(MascotaFormularioActivity.this);
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
                    Bitmap fotoPrevia;
                    try {
                        foto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dir);
                        ivFotoPrevia.setImageBitmap(foto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
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

    @Deprecated
    public void onClickAñadirMascota(View view) {
        MascotaDAO dao = new MascotaDAOImpl();
        Mascota m;
        Usuario u;
        u = (Usuario) this.getIntent().getSerializableExtra("propietario");
        m = new Mascota();
        m.setNombre(etNombreMascota.getText().toString());
        m.setPropietario(u);
        m.setFoto(Utility.getBytes(Utility.resizeImage(this, R.drawable.mascota1, 300, 300)));

        switch (Validacion.validarMascota(m)) {
            case 0:
                dao.insertarMascota(m, this);
                Toast.makeText(MascotaFormularioActivity.this, m.getNombre(), Toast.LENGTH_SHORT).show();
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
        try {
            m.setFechaNacimiento(formateadorDeFecha.parse(etFechaNacimiento.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("Error en fecha", e.getMessage());
        }
        m.setTipo((String) spinnerTipoMascota.getSelectedItem());
        m.setRaza((String) spinnerRazaMascota.getSelectedItem());
        m.setGenero((String) spinnerGenero.getSelectedItem());

        if (foto != null) {
            m.setFoto(Utility.getBytes(Utility.resizeImage(foto, 300, 300)));
        } else {
            m.setFoto(Utility.getBytes(Utility.resizeImage(this, R.drawable.mascota1, 300, 300)));
        }

        m.setEstado("0");
        m.setNotificaciones("1");

        switch (Validacion.validarMascota(m)) {
            case 0:
                if (this.getIntent().getExtras().getSerializable("mascota") != null) {
                    m.setId(((Mascota) this.getIntent().getExtras().getSerializable("mascota")).getId());
                    m.setNotificaciones(((Mascota) this.getIntent().getExtras().getSerializable("mascota")).getNotificaciones());
                    m.setEstado(((Mascota) this.getIntent().getExtras().getSerializable("mascota")).getEstado());
                    dao.actualizarMascota(m, this);

                    setResult(0);

//                    Intent i = new Intent(this, MascotaActivity.class);
//                    i.putExtra("id", m.getId() + "");
//                    i.putExtra("mascota", m);
//                    startActivity(i);

                    Toast.makeText(MascotaFormularioActivity.this, "Mascota editada", Toast.LENGTH_SHORT).show();
                } else {
                    dao.insertarMascota(m, this);
                    Toast.makeText(MascotaFormularioActivity.this, m.getNombre(), Toast.LENGTH_SHORT).show();
                }
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

    private void onSelectedItemTiposDeMascotas(View view, int posicion, String raza) {
        try {
            cambiarItemsSpinnerRaza(posicion, view);
            if (raza != null) {
                seleccionarItemRaza(raza);
            }
        } catch (Exception e) {
        }
    }

    private void seleccionarItemRaza(String raza) {
        spinnerRazaMascota.setSelection(Utility.getIndex(spinnerRazaMascota, raza));
    }

    private void cambiarItemsSpinnerRaza(int posicion, View view) {
        ArrayAdapter<CharSequence> adapter = null;
        switch (posicion) {
            case 0:
                adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDeGatos,
                        android.R.layout.simple_dropdown_item_1line);
                break;
            case 1:
                adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDePerros,
                        android.R.layout.simple_dropdown_item_1line);
                break;
            case 2:
                adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDeAves,
                        android.R.layout.simple_dropdown_item_1line);
                break;
            case 3:
                adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasDeReptiles,
                        android.R.layout.simple_dropdown_item_1line);
                break;
            case 4:
                adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.RazasOtro,
                        android.R.layout.simple_dropdown_item_1line);
                break;
        }
        spinnerRazaMascota.setAdapter(adapter);
    }

    private void inicializarSpinners() {
        ArrayAdapter<CharSequence> adapter;

        adapter = ArrayAdapter.createFromResource(this, R.array.TiposDeMascotas,
                android.R.layout.simple_spinner_item);
        final String raza;
        if (this.getIntent().getExtras().getSerializable("mascota") != null) {
            raza = ((Mascota) this.getIntent().getExtras().getSerializable("mascota")).getRaza();
        } else {
            raza = null;
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTipoMascota.setAdapter(adapter);
        spinnerTipoMascota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSelectedItemTiposDeMascotas(view, position, raza);
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
