package co.edu.udea.pi.sjm.petted.vista.mascota;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.MascotaDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.util.Utility;

public class TagNFCFormularioActivity extends AppCompatActivity {

    private Button btnAsociar;

    private TextView tvIdTag;
    private NfcAdapter myNfcAdapter;

    private MascotaDAO daoM;
    private String mascotaId;
    private Mascota mascota;
    private String idTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_tag_nfc);
        btnAsociar = (Button) findViewById(R.id.btnAsociar);
        btnAsociar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAsociarTag();
            }
        });
        tvIdTag = (TextView) findViewById(R.id.tvIdTagNFC);

        mascotaId = this.getIntent().getExtras().getString("mascotaId");

        myNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (!myNfcAdapter.isEnabled()) {
            new AlertDialog.Builder(this)
                    .setTitle("Activar NFC")
                    .setMessage("Por favor active el NFC y presione el botón de atrás para retornar a la aplicación")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            onNewIntent(getIntent());
        }
    }

    private void onClickAsociarTag() {
        if (true) {
            daoM = new MascotaDAOImpl();
            mascota = daoM.obtenerMascota(mascotaId, this);
            mascota.setIdTag(idTag);
            daoM.actualizarMascota(mascota, this);
            finish();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
            Tag myTagId = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            idTag = Utility.bytesToHexString(myTagId.getId());
            tvIdTag.setText(idTag);
        } else {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableForegroundDispatchSystem();
        onNewIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        myNfcAdapter.disableForegroundDispatch(this);
    }

    private void enableForegroundDispatchSystem() {
        Intent intent = new Intent(this, TagNFCFormularioActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] intentFilter = new IntentFilter[]{};
        myNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_formulario_tag_nfc, menu);
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
