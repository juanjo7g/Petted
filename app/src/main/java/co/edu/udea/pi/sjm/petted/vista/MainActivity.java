package co.edu.udea.pi.sjm.petted.vista;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.vista.listadoMascotas.ListadoMascotasActivity;
import co.edu.udea.pi.sjm.petted.vista.usuario.UsuarioFormularioActivity;

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
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Iniciando sesión...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

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
                        progress.dismiss();
                    }
                });

    }

    public void onClickIniciarConFacebook(View view) {
        List<String> permissions = Arrays.asList("public_profile");
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                    Toast.makeText(MainActivity.this, "Uh oh. The user cancelled the Facebook login.", Toast.LENGTH_SHORT).show();
                } else if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    Toast.makeText(MainActivity.this, "User signed up and logged in through Facebook!"+user.toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    Toast.makeText(MainActivity.this, "User logged in through Facebook!"+user.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void iniciarCreacionUsuario(View view) {
        Intent i = new Intent(this, UsuarioFormularioActivity.class);
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

        return super.onOptionsItemSelected(item);
    }
}
