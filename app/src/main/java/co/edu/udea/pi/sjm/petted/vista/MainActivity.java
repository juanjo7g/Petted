package co.edu.udea.pi.sjm.petted.vista;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.UsuarioDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Usuario;
import co.edu.udea.pi.sjm.petted.dtoParse.MascotaPO;
import co.edu.udea.pi.sjm.petted.util.Utility;
import co.edu.udea.pi.sjm.petted.vista.listadoMascotas.ListadoMascotasActivity;
import co.edu.udea.pi.sjm.petted.vista.usuario.CreacionUsuarioActivity;

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

        Intent i;

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            i = new Intent(this, ListadoMascotasActivity.class);
            startActivity(i);
            finish();
            Toast.makeText(MainActivity.this, "Ya esta logueado: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickIniciar(View view) {
        ParseUser.logInInBackground(etEmail.getText().toString(), etContraseña.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            Intent i = new Intent(MainActivity.this, ListadoMascotasActivity.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(MainActivity.this, "Bienvenido: " + parseUser.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Error en el logueo", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
