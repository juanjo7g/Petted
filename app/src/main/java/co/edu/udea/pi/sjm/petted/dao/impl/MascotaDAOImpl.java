package co.edu.udea.pi.sjm.petted.dao.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.List;

import co.edu.udea.pi.sjm.petted.SQLite.PettedDataBaseHelper;
import co.edu.udea.pi.sjm.petted.dao.MascotaDAO;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Usuario;

/**
 * Created by Juan on 02/10/2015.
 */
public class MascotaDAOImpl implements MascotaDAO {
    @Override
    public void insertarMascota(Mascota mascota, Context context) {
        PettedDataBaseHelper helper;
        try {
            helper = PettedDataBaseHelper.getInstance(context);
            helper.insertarMascota(mascota);
        } catch (Exception e) {
        }
    }

    @Override
    public Mascota obtenerMascota(int id, Context context) {
        return null;
    }

    @Override
    public void actualizarMascota(Mascota mascota, Context context) {

    }

    @Override
    public void eliminarMascota(Mascota mascota, Context context) {

    }

    @Override
    public List<Mascota> obtenerMasctoras(Usuario usuario, Context context) {
        return null;
    }

    @Override
    public List<Mascota> obtener(Context context) {
        return null;
    }
}
