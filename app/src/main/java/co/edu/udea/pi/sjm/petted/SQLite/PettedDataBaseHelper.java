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

import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 27/09/2015.
 */
public class PettedDataBaseHelper extends SQLiteOpenHelper {

    private static PettedDataBaseHelper sInstance; // Instancia unica de la clase SINGLETON!

    private static final String DATABASE_NAME = "pettedDataBase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLA_USUARIOS = "usuarios";
    private static final String TABLA_MASCOTAS = "mascotas";

    private static final String KEY_USUARIO_CORREO = "correo";
    private static final String KEY_USUARIO_NOMBRE = "nombre";
    private static final String KEY_USUARIO_CONTRASEÑA = "contraseña";

    private static final String KEY_MASCOTAS_ID = "id";
    private static final String KEY_MASCOTAS_PROPIETARIO = "propietario";
    private static final String KEY_MASCOTAS_NOMBRE = "nombre";
    private static final String KEY_MASCOTAS_FECHA_NACIMIENTO = "fechaNacimiento";
    private static final String KEY_MASCOTAS_TIPO = "tipo";
    private static final String KEY_MASCOTAS_RAZA = "raza";
    private static final String KEY_MASCOTAS_GENERO = "genero";
    private static final String KEY_MASCOTAS_ID_TAG = "idTag";
    private static final String KEY_MASCOTAS_FOTO = "foto";


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
        String CREATE_TABLA_MASCOTAS = "CREATE TABLE " + TABLA_MASCOTAS +
                "(" +
                KEY_MASCOTAS_ID + " TEXT PRIMARY KEY," +
                KEY_MASCOTAS_PROPIETARIO + " TEXT NOT NULL," + // TODO: MANEJO DE CLAVE FORANEA
                KEY_MASCOTAS_NOMBRE + " TEXT NOT NULL," +
                KEY_MASCOTAS_FECHA_NACIMIENTO + " TEXT," + // TODO: MANEJO DE FECHA
                KEY_MASCOTAS_TIPO + " TEXT," +
                KEY_MASCOTAS_RAZA + " TEXT," +
                KEY_MASCOTAS_GENERO + " TEXT," +
                KEY_MASCOTAS_ID_TAG + " TEXT," +
                KEY_MASCOTAS_FOTO + " BLOB" +
                ")";
        db.execSQL(CREATE_TABLA_USUARIOS);
        db.execSQL(CREATE_TABLA_MASCOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_MASCOTAS);
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

    public void insertarMascota(Mascota mascota) {

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