package co.edu.udea.pi.sjm.petted.vista.usuario;

import android.app.ProgressDialog;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuario);
        etNombreUsuario = (EditText) findViewById(R.id.etNombreUsuario);
        etCorreoElectronico = (EditText) findViewById(R.id.etCorreoElectronico);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        etContraseñaRep = (EditText) findViewById(R.id.etContraseñaRep);

    }

    public void onClickCrearUsuario(View v) {

        final Usuario u = new Usuario();
        final UsuarioDAO daoU = new UsuarioDAOImpl();
        u.setCorreo(etCorreoElectronico.getText().toString());
        u.setNombre(etNombreUsuario.getText().toString());
        u.setContraseña(etContraseña.getText().toString());


        switch (Validacion.validarUsuario(u)) {
            case 0:
                daoU.insertarUsuario(u, UsuarioFormularioActivity.this);
                break;
            case 1:
                Toast.makeText(UsuarioFormularioActivity.this, "Error, datos vacios", Toast.LENGTH_SHORT).show();
                break;
            case 2:
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
