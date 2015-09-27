package co.edu.udea.pi.sjm.petted.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

public class CreacionUsuarioActivity extends AppCompatActivity {

    private EditText etNombreUsuario;
    private EditText etCorreoElectronico;
    private EditText etContraseña;
    private EditText etContraseñaRep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_usuario);
        etNombreUsuario = (EditText) findViewById(R.id.etNombreUsuario);
        etCorreoElectronico = (EditText) findViewById(R.id.etCorreoElectronico);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        etContraseñaRep = (EditText) findViewById(R.id.etContraseñaRep);
    }

    public void onClickCrearUsuario(View v) {
        Usuario u = new Usuario();
        u.setCorreo(etCorreoElectronico.getText().toString());
        u.setNombre(etNombreUsuario.getText().toString());
        u.setContraseña(etContraseña.getText().toString());
        PettedDataBaseHelper helper = PettedDataBaseHelper.getInstance(this);
        helper.insertarUsuario(u);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_creacion_usuario, menu);
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
