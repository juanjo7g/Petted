package co.edu.udea.pi.sjm.petted.vista;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.util.Utility;
import co.edu.udea.pi.sjm.petted.vista.listadoMascotas.ListadoMascotasActivity;
import co.edu.udea.pi.sjm.petted.vista.usuario.UsuarioFormularioActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvCrearUsuario;
    private EditText etUsuario;
    private EditText etContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCrearUsuario = (TextView) findViewById(R.id.tvCrearUsuario);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Intent i;
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            i = new Intent(this, ListadoMascotasActivity.class);
            finish();
            startActivity(i);
            if (!currentUser.getBoolean("emailVerified")) {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Bienvenido " + currentUser.getUsername())
//                        .setMessage("Por favor verificar el e-mail próximamente" +
//                                " será requerido para acceder a la aplicación.")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                finish();
//                                startActivity(i);
//                            }
//                        })
//                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialog) {
//                                finish();
//                                startActivity(i);
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
            }else{
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickIniciar(View view) {
        if (Utility.isOnline()) {
            if (etUsuario.getText().toString().equals("")) {
                etUsuario.setError("Campo requerido.");
            }
            if (etContraseña.getText().toString().equals("")) {
                etContraseña.setError("Campo requerido.");
            }
            if ((etUsuario.getError() == null) && (etContraseña.getError() == null)) {
                final ProgressDialog progress = new ProgressDialog(this);
                progress.setMessage("Iniciando sesión...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.show();

                ParseUser.logInInBackground(etUsuario.getText().toString(), etContraseña.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser parseUser, ParseException e) {
                                if (parseUser != null) {
                                    final Intent i = new Intent(MainActivity.this, ListadoMascotasActivity.class);
                                    cargarDatosDelUsuario();
                                    if (!parseUser.getBoolean("emailVerified")) {
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setTitle("Bienvenido " + parseUser.getUsername())
                                                .setMessage("Por favor verificar el e-mail próximamente" +
                                                        " será requerido para acceder a la aplicación.")
                                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                        startActivity(i);
                                                    }
                                                })
                                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                                    @Override
                                                    public void onCancel(DialogInterface dialog) {
                                                        finish();
                                                        startActivity(i);
                                                    }
                                                })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    }
                                } else {
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("Error iniciando sesión")
                                            .setMessage("Usuario y/o contraseña incorrectos.")
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                                progress.dismiss();
                            }
                        });
            }
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Sin conexión")
                    .setMessage("Conexión a internet necesaria para iniciar sesión.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void cargarDatosDelUsuario() {
        try {
            ParseQuery<ParseObject> query;
            List<ParseObject> list;
            query = ParseQuery.getQuery("Mascota");

            ParseQuery<ParseObject> queryC;
            List<ParseObject> listC;
            queryC = ParseQuery.getQuery("Cita");

            ParseQuery<ParseObject> queryMe;
            List<ParseObject> listMe;
            queryMe = ParseQuery.getQuery("Medicamento");

            ParseQuery<ParseObject> queryV;
            List<ParseObject> listV;
            queryV = ParseQuery.getQuery("Vacuna");

            query.whereEqualTo("propietario", ParseUser.getCurrentUser());
            list = query.find();

            for (int j = 0; j < list.size(); j++) {
                list.get(j).pin();

                queryC.whereEqualTo("mascota", list.get(j));
                listC = queryC.find();

                for (int k = 0; k < listC.size(); k++) {
                    listC.get(k).pin();
                }

                queryMe.whereEqualTo("mascota", list.get(j));
                listMe = queryMe.find();

                for (int k = 0; k < listMe.size(); k++) {
                    listMe.get(k).pin();
                }

                queryV.whereEqualTo("mascota", list.get(j));
                listV = queryV.find();

                for (int k = 0; k < listV.size(); k++) {
                    listV.get(k).pin();
                }

            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    }

    public void onClickIniciarConFacebook(View view) {
        if (Utility.isOnline()) {


            final List<String> permissions = Arrays.asList("public_profile", "email");

            ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException err) {
                    if (user == null) {
                        Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        Toast.makeText(MainActivity.this, "Error iniciando sesión con Facebook.", Toast.LENGTH_LONG).show();
                    } else if (user.isNew()) {
                        Log.d("MyApp", "User signed up and logged in through Facebook!");
                        getFacebookUserDetails(user);
                        onResume();
                    } else {
                        Log.d("MyApp", "User logged in through Facebook!");
                        onResume();
                    }
                }
            });
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Sin conexión")
                    .setMessage("Conexión a internet necesaria para iniciar sesión con Facebook.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

    }

    public void onClickCrearUsuario(View view) {
        if (Utility.isOnline()) {
            Intent i = new Intent(this, UsuarioFormularioActivity.class);
            startActivity(i);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Sin conexión")
                    .setMessage("Conexión a internet necesaria para crear usuario.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
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


    public void getFacebookUserDetails(final ParseUser user) {

        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            String userName = object.getString("name");
                            String userId = object.getString("id");
                            String userGender = object.getString("gender");
                            String userProfileURL = object.getString("link");
                            String userEmail = object.getString("email");
                            String firstName = object.getString("first_name");
                            String lastName = object.getString("last_name");

                            user.put("username", userEmail);
                            user.put("email", userEmail);

                            user.put("nameFacebook", userName);
                            user.put("idFacebook", userId);
                            user.put("genderFacebok", userGender);
                            user.put("urlFacebook", userProfileURL);
                            user.put("firstName", firstName);
                            user.put("lastName", lastName);
                            user.put("fullName", firstName + " " + lastName);

                            user.save();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "age_range,gender,name,id,link,email,picture.type(large),first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();

    }
}