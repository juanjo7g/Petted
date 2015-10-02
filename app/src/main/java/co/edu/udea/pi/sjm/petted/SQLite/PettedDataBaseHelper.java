package co.edu.udea.pi.sjm.petted.SQLite;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 27/09/2015.
 */
public class PettedDataBaseHelper extends SQLiteOpenHelper {

    private static PettedDataBaseHelper sInstance; // Instancia unica de la clase SINGLETON!

    private static final String DATABASE_NAME = "pettedDataBase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLA_USUARIOS = "usuarios";

    private static final String KEY_USUARIO_CORREO = "correo";
    private static final String KEY_USUARIO_NOMBRE = "nombre";
    private static final String KEY_USUARIO_CONTRASEÑA = "contraseña";

    public static synchronized PettedDataBaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PettedDataBaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private PettedDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLA_USUARIOS = "CREATE TABLE " + TABLA_USUARIOS +
                "(" +
                KEY_USUARIO_CORREO + " TEXT PRIMARY KEY," +
                KEY_USUARIO_NOMBRE + " TEXT NOT NULL," +
                KEY_USUARIO_CONTRASEÑA + " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_TABLA_USUARIOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
            onCreate(db);
        }
    }

    public void insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USUARIO_CORREO, usuario.getCorreo());
            values.put(KEY_USUARIO_NOMBRE, usuario.getNombre());
            values.put(KEY_USUARIO_CONTRASEÑA, usuario.getContraseña());

            db.insertOrThrow(TABLA_USUARIOS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("ERROR", "Error almacenando usuario en la base de datos");
        } finally {
            db.endTransaction();
        }
    }

    public Cursor obtenerUsuario(String correo) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;

        db.beginTransaction();
        try {
            String selection = KEY_USUARIO_CORREO + " = ? ";//WHERE correo = ?
            String selectionArgs[] = new String[]{correo};
            c = db.query(TABLA_USUARIOS, null, selection, selectionArgs, null, null, null);

        } catch (Exception e) {
            Log.d("ERROR", "Error");
        } finally {
            db.endTransaction();
        }
        return c;
    }

}