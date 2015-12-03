package co.edu.udea.pi.sjm.petted.vista.usuario;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.UsuarioDAOImpl;
import co.edu.udea.pi.sjm.petted.util.Validacion;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

public class UsuarioFormularioActivity extends AppCompatActivity {

    private EditText etNombreUsuario;
    private EditText etCorreoElectronico;
    private EditText etContraseña;
    private EditText etContraseñaRep;

    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuario);

        ActionBar actionBar = ((AppCompatActivity) this).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white);
        }

        etNombreUsuario = (EditText) findViewById(R.id.etNombreUsuario);
        etCorreoElectronico = (EditText) findViewById(R.id.etCorreoElectronico);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        etContraseñaRep = (EditText) findViewById(R.id.etContraseñaRep);

        detectarErroresEnFormulario();

    }

    private void detectarErroresEnFormulario() {
        // TODO: Ejecutar en background!
        etNombreUsuario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String e = Validacion.validarNombreUsuario(etNombreUsuario.getText().toString());
                    if (e != null) {
                        etNombreUsuario.setError(e);
                    }
                }
            }
        });

        etCorreoElectronico.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String e = Validacion.validarCorreoElectronico(etCorreoElectronico.getText().toString());
                    if (e != null) {
                        etCorreoElectronico.setError(e);
                    }
                }
            }
        });

        etCorreoElectronico.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String e = Validacion.validarFormatoCorreoElectronico(etCorreoElectronico.getText().toString());
                if (e != null) {
                    etCorreoElectronico.setError(e);
                }
            }
        });

        etContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String e = Validacion.validarContraseña(etContraseña.getText().toString());
                if (e != null) {
                    etContraseña.setError(e);
                }
                if (!etContraseñaRep.getText().toString().equals(etContraseña.getText().toString()))
                    etContraseñaRep.setError("Las contraseñas no coinciden.");
            }
        });

        etContraseñaRep.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etContraseñaRep.getText().toString().equals(etContraseña.getText().toString()))
                    etContraseñaRep.setError("Las contraseñas no coinciden.");
            }
        });

    }


    private void verificarCamposRequeridos() {
        if (etNombreUsuario.getText().toString().equals("")) {
            etNombreUsuario.setError("Campo requerido.");
        }
        if (etCorreoElectronico.getText().toString().equals("")) {
            etCorreoElectronico.setError("Campo requerido.");
        }
        if (etContraseña.getText().toString().equals("")) {
            etContraseña.setError("Campo requerido.");
        }

        String e = Validacion.validarNombreUsuario(etNombreUsuario.getText().toString());
        if (e != null) {
            etNombreUsuario.setError(e);
        }

        e = Validacion.validarCorreoElectronico(etCorreoElectronico.getText().toString());
        if (e != null) {
            etCorreoElectronico.setError(e);
        }

    }


    public void onClickCrearUsuario(View v) {

        verificarCamposRequeridos();

        final Usuario u = new Usuario();
        final UsuarioDAO daoU = new UsuarioDAOImpl();
        u.setCorreo(etCorreoElectronico.getText().toString());
        u.setNombre(etNombreUsuario.getText().toString());
        u.setContraseña(etContraseña.getText().toString());


        switch (Validacion.validarUsuario(u, etContraseñaRep.getText().toString())) {
            case 0:
                if ((etNombreUsuario.getError() == null) && (etCorreoElectronico.getError() == null) &&
                        (etContraseña.getError() == null) && (etContraseñaRep.getError() == null)) {
                    daoU.insertarUsuario(u);
                    if (ParseUser.getCurrentUser() != null) {
                        finish();
                    }
                }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario_usuario, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

}
