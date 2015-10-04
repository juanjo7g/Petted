package co.edu.udea.pi.sjm.petted.SQLite;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.Utility;
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
                KEY_MASCOTAS_ID + " INTEGER PRIMARY KEY autoincrement," + // Autoincrementable PK
                KEY_MASCOTAS_PROPIETARIO + " TEXT NOT NULL," + // TODO: MANEJO DE CLAVE FORANEA, CORREO DE PROPETARIO
                KEY_MASCOTAS_NOMBRE + " TEXT NOT NULL," +
                KEY_MASCOTAS_FECHA_NACIMIENTO + " TEXT," + // TODO: MANEJO DE FECHA
                KEY_MASCOTAS_TIPO + " TEXT," +
                KEY_MASCOTAS_RAZA + " TEXT," +
                KEY_MASCOTAS_GENERO + " TEXT," +
                KEY_MASCOTAS_ID_TAG + " TEXT," +
                KEY_MASCOTAS_FOTO + " BLOB, " +
                " FOREIGN KEY(" + KEY_MASCOTAS_PROPIETARIO + ") REFERENCES " + TABLA_USUARIOS +
                "(" + KEY_USUARIO_CORREO + "))";
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

        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(KEY_USUARIO_CORREO, usuario.getCorreo());
            values.put(KEY_USUARIO_NOMBRE, usuario.getNombre());
            values.put(KEY_USUARIO_CONTRASEÑA, usuario.getContraseña());

            db.insertOrThrow(TABLA_USUARIOS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("ERROR", "Error almacenando usuario en la base de datos: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void insertarMascota(Mascota mascota) {
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat formateadorDeFecha;

        try {
            ContentValues values = new ContentValues();
            db.beginTransaction();

            values.put(KEY_MASCOTAS_PROPIETARIO, mascota.getPropietario().getCorreo());
            values.put(KEY_MASCOTAS_NOMBRE, mascota.getNombre());

            formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            if (mascota.getFechaNacimiento() != null) {
                values.put(KEY_MASCOTAS_FECHA_NACIMIENTO, formateadorDeFecha.format(mascota.getFechaNacimiento()));
            } else {
                values.put(KEY_MASCOTAS_FECHA_NACIMIENTO, (byte[]) null);
            }

            values.put(KEY_MASCOTAS_TIPO, mascota.getTipo());
            values.put(KEY_MASCOTAS_RAZA, mascota.getRaza());
            values.put(KEY_MASCOTAS_GENERO, mascota.getGenero());
            values.put(KEY_MASCOTAS_ID_TAG, mascota.getIdTag());

            if (mascota.getFoto() != null) {
                values.put(KEY_MASCOTAS_FOTO, Utility.getBytes(mascota.getFoto())); // Se obtiene el arreglo de bytes
            } else {
                values.put(KEY_MASCOTAS_FOTO, (byte[]) null);
            }

            db.insertOrThrow(TABLA_MASCOTAS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("ERROR", "Error almacenando mascota en la base de datos");
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

    public Cursor obtenerMascota(int id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;

        db.beginTransaction();
        try {
            String selection = KEY_MASCOTAS_ID + " = ? ";//WHERE ID = ?
            String selectionArgs[] = new String[]{id + ""};
            c = db.query(TABLA_MASCOTAS, null, selection, selectionArgs, null, null, null);
        } catch (Exception e) {
            Log.d("ERROR", "Error");
        } finally {
            db.endTransaction();
        }
        return c;
    }

    public Cursor obtenerMascotas() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(
                "SELECT * FROM " + TABLA_MASCOTAS, null);
        return c;
    }

    public Cursor obtenerMascotas(Usuario u) {
        SQLiteDatabase db = getWritableDatabase();
        String correo = u.getCorreo();
        Cursor c;
        String selection = KEY_MASCOTAS_PROPIETARIO + " = ? ";//WHERE propietario.correo = ?
        String selectionArgs[] = new String[]{correo};
        c = db.query(TABLA_MASCOTAS, null, selection, selectionArgs, null, null, null);
        return c;
    }


}