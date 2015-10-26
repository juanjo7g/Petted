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

import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.util.Utility;
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
    private static final String TABLA_CITAS = "citas";

    private static final String KEY_USUARIO_CORREO = "correo";
    private static final String KEY_USUARIO_NOMBRE = "nombre";
    private static final String KEY_USUARIO_CONTRASEÑA = "contraseña";
    private static final String KEY_USUARIO_LOGUEADO = "logueado"; // 0-> No logueado 1-> Logueado

    private static final String KEY_MASCOTA_ID = "id";
    private static final String KEY_MASCOTA_PROPIETARIO = "propietario";
    private static final String KEY_MASCOTA_NOMBRE = "nombre";
    private static final String KEY_MASCOTA_FECHA_NACIMIENTO = "fechaNacimiento";
    private static final String KEY_MASCOTA_TIPO = "tipo";
    private static final String KEY_MASCOTA_RAZA = "raza";
    private static final String KEY_MASCOTA_GENERO = "genero";
    private static final String KEY_MASCOTA_ID_TAG = "idTag";
    private static final String KEY_MASCOTA_FOTO = "foto";
    private static final String KEY_MASCOTA_NOTIFICACIONES = "notificaciones"; // 0-> Desactivado 1->Activado
    private static final String KEY_MASCOTA_ESTADO = "estado"; // 0-> Sin sincronizar 1-> Sincronizado
    private static final String KEY_MASCOTA_PERDIDA = "perdida"; // 0-> No esta reportada como perdida 1-> Esta perdida

    private static final String KEY_CITA_ID = "id";
    private static final String KEY_CITA_MASCOTA = "mascota";
    private static final String KEY_CITA_NOMBRE = "nombre";
    private static final String KEY_CITA_DESCRIPCION = "descripcion";
    private static final String KEY_CITA_TIPO = "tipo";
    private static final String KEY_CITA_FECHA_HORA = "fechaHora";
    private static final String KEY_CITA_ESTADO = "estado";


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
                KEY_USUARIO_CONTRASEÑA + " TEXT NOT NULL," +
                KEY_USUARIO_LOGUEADO + " TEXT" +
                ")";
        String CREATE_TABLA_MASCOTAS = "CREATE TABLE " + TABLA_MASCOTAS +
                "(" +
                KEY_MASCOTA_ID + " INTEGER PRIMARY KEY autoincrement," + // Autoincrementable PK
                KEY_MASCOTA_PROPIETARIO + " TEXT NOT NULL," + // CLAVE FORANEA, CORREO DE PROPETARIO
                KEY_MASCOTA_NOMBRE + " TEXT NOT NULL," +
                KEY_MASCOTA_FECHA_NACIMIENTO + " TEXT," +
                KEY_MASCOTA_TIPO + " TEXT," +
                KEY_MASCOTA_RAZA + " TEXT," +
                KEY_MASCOTA_GENERO + " TEXT," +
                KEY_MASCOTA_ID_TAG + " TEXT," +
                KEY_MASCOTA_FOTO + " BLOB, " +
                KEY_MASCOTA_NOTIFICACIONES + " TEXT, " +
                KEY_MASCOTA_ESTADO + " TEXT, " +
                KEY_MASCOTA_PERDIDA + " TEXT, " +
                " FOREIGN KEY(" + KEY_MASCOTA_PROPIETARIO + ") REFERENCES " + TABLA_USUARIOS +
                "(" + KEY_USUARIO_CORREO + "))";
        String CREATE_TABLA_CITAS = "CREATE TABLE" + TABLA_CITAS +
                "(" +
                KEY_CITA_ID + " INTEGER PRIMARY KEY autoincrement," + // Autoincrementable pk
                KEY_CITA_MASCOTA + " TEXT NOT NULL," + // CLAVE FORANEA, id mascota
                KEY_CITA_NOMBRE + " TEXT," +
                KEY_CITA_DESCRIPCION + " TEXT," +
                KEY_CITA_TIPO + " TEXT," +
                KEY_CITA_FECHA_HORA + " TEXT," +
                KEY_CITA_ESTADO + " TEXT," +
                " FOREIGN KEY(" + KEY_CITA_MASCOTA + ") REFERENCES " + TABLA_MASCOTAS +
                "(" + KEY_MASCOTA_ID + "))";

        db.execSQL(CREATE_TABLA_USUARIOS);
        db.execSQL(CREATE_TABLA_MASCOTAS);
        db.execSQL(CREATE_TABLA_CITAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_MASCOTAS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLA_CITAS);
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
            values.put(KEY_USUARIO_LOGUEADO, "0");

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

            values.put(KEY_MASCOTA_PROPIETARIO, mascota.getPropietario().getCorreo());
            values.put(KEY_MASCOTA_NOMBRE, mascota.getNombre());

            formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            if (mascota.getFechaNacimiento() != null) {
                values.put(KEY_MASCOTA_FECHA_NACIMIENTO, formateadorDeFecha.format(mascota.getFechaNacimiento()));
            } else {
                values.put(KEY_MASCOTA_FECHA_NACIMIENTO, (byte[]) null);
            }

            values.put(KEY_MASCOTA_TIPO, mascota.getTipo());
            values.put(KEY_MASCOTA_RAZA, mascota.getRaza());
            values.put(KEY_MASCOTA_GENERO, mascota.getGenero());
            values.put(KEY_MASCOTA_ID_TAG, mascota.getIdTag());

            if (mascota.getFoto() != null) {
                values.put(KEY_MASCOTA_FOTO, mascota.getFoto()); // Se obtiene el arreglo de bytes
            } else {
                values.put(KEY_MASCOTA_FOTO, (byte[]) null);
            }

            values.put(KEY_MASCOTA_NOTIFICACIONES, "1");
            values.put(KEY_MASCOTA_ESTADO, "0");
            values.put(KEY_MASCOTA_PERDIDA, "0");

            db.insertOrThrow(TABLA_MASCOTAS, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("ERROR", "Error almacenando mascota en la base de datos");
        } finally {
            db.endTransaction();
        }
    }

    public void insertarCita(Cita cita) {
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat formateadorDeFecha;

        try {
            ContentValues values = new ContentValues();
            db.beginTransaction();

            values.put(KEY_CITA_MASCOTA, cita.getMascota().getId());
            values.put(KEY_CITA_NOMBRE, cita.getNombre());
            values.put(KEY_CITA_DESCRIPCION, cita.getDescripcion());
            values.put(KEY_CITA_TIPO, cita.getTipo());

            formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);

            if (cita.getFechaHora() != null) {
                values.put(KEY_CITA_FECHA_HORA, formateadorDeFecha.format(cita.getFechaHora()));
            } else {
                values.put(KEY_CITA_FECHA_HORA, (byte[]) null);
            }

            values.put(KEY_CITA_ESTADO, "0");

            db.insertOrThrow(TABLA_CITAS, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("ERROR", "Error almacenando cita en la base de datos");
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

    public Cursor obtenerUsuarioLogueado() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;

        db.beginTransaction();
        try {
            String selection = KEY_USUARIO_LOGUEADO + " = ? ";//WHERE logueado = ?
            String selectionArgs[] = new String[]{"1"};
            c = db.query(TABLA_USUARIOS, null, selection, selectionArgs, null, null, null);
        } catch (Exception e) {
            Log.d("ERROR", "Error");
        } finally {
            db.endTransaction();
        }
        return c;
    }

    public Cursor obtenerMascota(String id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;

        db.beginTransaction();
        try {
            String selection = KEY_MASCOTA_ID + " = ? ";//WHERE ID = ?
            String selectionArgs[] = new String[]{id + ""};
            c = db.query(TABLA_MASCOTAS, null, selection, selectionArgs, null, null, null);
        } catch (Exception e) {
            Log.d("ERROR", "Error");
        } finally {
            db.endTransaction();
        }
        return c;
    }

    public Cursor obtenerCita(String id) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = null;

        db.beginTransaction();
        try {
            String selection = KEY_CITA_ID + " = ? ";//WHERE ID = ?
            String selectionArgs[] = new String[]{id + ""};
            c = db.query(TABLA_CITAS, null, selection, selectionArgs, null, null, null);
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
        String selection = KEY_MASCOTA_PROPIETARIO + " = ? ";//WHERE propietario.correo = ?
        String selectionArgs[] = new String[]{correo};
        c = db.query(TABLA_MASCOTAS, null, selection, selectionArgs, null, null, null);
        return c;
    }

    public Cursor obtenerCitas(Mascota m) {
        SQLiteDatabase db = getWritableDatabase();
        String idMascota = m.getId();
        Cursor c;
        String selection = KEY_CITA_MASCOTA + " = ? ";//WHERE mascota.id = ?
        String selectionArgs[] = new String[]{idMascota};
        c = db.query(TABLA_CITAS, null, selection, selectionArgs, null, null, null);
        return c;
    }

    public void actualizarUsuario(Usuario u) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            db.beginTransaction();

            values.put(KEY_USUARIO_NOMBRE, u.getNombre());
            values.put(KEY_USUARIO_CONTRASEÑA, u.getContraseña());
            values.put(KEY_USUARIO_LOGUEADO, u.getLogueado());

            db.update(TABLA_USUARIOS, values, KEY_USUARIO_CORREO + "= ?", new String[]{u.getCorreo()});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("ERROR", "Error actualizando mascota en la base de datos");
        } finally {
            db.endTransaction();
        }
    }

    public void actualizarMascota(Mascota mascota) {
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat formateadorDeFecha;

        try {
            ContentValues values = new ContentValues();
            db.beginTransaction();

            values.put(KEY_MASCOTA_PROPIETARIO, mascota.getPropietario().getCorreo());
            values.put(KEY_MASCOTA_NOMBRE, mascota.getNombre());

            formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

            if (mascota.getFechaNacimiento() != null) {
                values.put(KEY_MASCOTA_FECHA_NACIMIENTO, formateadorDeFecha.format(mascota.getFechaNacimiento()));
            } else {
                values.put(KEY_MASCOTA_FECHA_NACIMIENTO, (byte[]) null);
            }

            values.put(KEY_MASCOTA_TIPO, mascota.getTipo());
            values.put(KEY_MASCOTA_RAZA, mascota.getRaza());
            values.put(KEY_MASCOTA_GENERO, mascota.getGenero());
            values.put(KEY_MASCOTA_ID_TAG, mascota.getIdTag());

            if (mascota.getFoto() != null) {
                values.put(KEY_MASCOTA_FOTO, mascota.getFoto()); // Se obtiene el arreglo de bytes
            } else {
                values.put(KEY_MASCOTA_FOTO, (byte[]) null);
            }

            values.put(KEY_MASCOTA_NOTIFICACIONES, mascota.getNotificaciones());
            values.put(KEY_MASCOTA_ESTADO, mascota.getEstado());

            db.update(TABLA_MASCOTAS, values, KEY_MASCOTA_ID + "= ?", new String[]{mascota.getId()});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("ERROR", "Error actualizando mascota en la base de datos");
        } finally {
            db.endTransaction();
        }

    }

    public void actualizarCita(Cita cita) {
        SQLiteDatabase db = getWritableDatabase();
        SimpleDateFormat formateadorDeFecha;

        try {
            ContentValues values = new ContentValues();
            db.beginTransaction();

            values.put(KEY_CITA_MASCOTA, cita.getMascota().getId());
            values.put(KEY_CITA_NOMBRE, cita.getNombre());
            values.put(KEY_CITA_DESCRIPCION, cita.getDescripcion());
            values.put(KEY_CITA_TIPO, cita.getTipo());

            formateadorDeFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);

            if (cita.getFechaHora() != null) {
                values.put(KEY_CITA_FECHA_HORA, formateadorDeFecha.format(cita.getFechaHora()));
            } else {
                values.put(KEY_CITA_FECHA_HORA, (byte[]) null);
            }

            values.put(KEY_CITA_ESTADO, cita.getEstado());

            db.update(TABLA_CITAS, values, KEY_CITA_ID + "= ?", new String[]{cita.getId()});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            Log.d("ERROR", "Error actualizando cita en la base de datos local");
        } finally {
            db.endTransaction();
        }

    }

    public void eliminarMascota(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLA_MASCOTAS, KEY_MASCOTA_ID + "=" + id, null);
    }

    public void eliminarCita(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLA_CITAS, KEY_CITA_ID + "=" + id, null);
    }

}