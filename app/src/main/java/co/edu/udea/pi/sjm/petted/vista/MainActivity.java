package co.edu.udea.pi.sjm.petted.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

public class MainActivity extends AppCompatActivity {

    private TextView tvCrearUsuario;
    private EditText etEmail;
    private EditText etContraseña;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCrearUsuario = (TextView) findViewById(R.id.tvCrearUsuario);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContraseña = (EditText) findViewById(R.id.etContraseña);

    }


    public void onClickIniciar(View view) {
        Intent i = new Intent(this, ListadoMascotasActivity.class);
        PettedDataBaseHelper helper = PettedDataBaseHelper.getInstance(this);
        Usuario u;
        u = helper.obtenerUsuario(etEmail.getText().toString());
        if (u != null) {
            if (u.getContraseña().equals(etContraseña.getText().toString())) {
                finish();
                Toast.makeText(MainActivity.this, "Nombre: " + u.getNombre() + " Correo: " +
                        u.getCorreo() + " Contraseña: " + u.getContraseña(), Toast.LENGTH_LONG).show();
                startActivity(i);
            }
            else{
                Toast.makeText(MainActivity.this, R.string.errorContraseña, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, R.string.errorUsuarioNoExiste, Toast.LENGTH_SHORT).show();
        }
    }

    public void iniciarCreacionUsuario(View view) {
        Intent i = new Intent(this, CreacionUsuarioActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
