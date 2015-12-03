package co.edu.udea.pi.sjm.petted.util;

import co.edu.udea.pi.sjm.petted.dao.UsuarioDAO;
import co.edu.udea.pi.sjm.petted.dao.impl.UsuarioDAOImpl;
import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.dto.Mascota;
import co.edu.udea.pi.sjm.petted.dto.Medicamento;
import co.edu.udea.pi.sjm.petted.dto.Usuario;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;

/**
 * Created by Juan on 04/10/2015.
 */
public class Validacion {
    public static int validarUsuario(Usuario u, String contraseñaRep) {
//        if (u.getNombre().equals("") || u.getContraseña().equals("")) {
//            return 1;
//        }
//        if (!u.getContraseña().equals(contraseñaRep)) {
//            return 2;
//        }
        return 0;
    }

    public static int validarMascota(Mascota m) {
        return 0;
    }

    public static int validarCita(Cita c) {
        return 0;
    }

    public static int validarVacuna(Vacuna v) {
        return 0;
    }

    public static int validarMedicamento(Medicamento m) {
        return 0;
    }

    public static String validarContraseña(String s) {
        if (s.equals("")) {
            return "Campo requerido.";
        }
        if (s.length() < 5) {
            return "Mínimo 5 caracteres.";
        }
        return null;
    }

    public static String validarNombreUsuario(String s) {
        if (s.equals("")) {
            return "Campo requerido.";
        }
        UsuarioDAO daoU = new UsuarioDAOImpl();
        if (daoU.obtenerUsuario(s) != null) {
            return "Nombre de Usuario no disponible.";
        }
        return null;
    }

    public static String validarFormatoCorreoElectronico(String s) {
        if (s.equals("")){
            return "Campo requerido.";
        }
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(s);
        if (!m.matches()) {
            return "Correo invalido.";
        }
        return null;
    }

    public static String validarCorreoElectronico(String c) {
        if (c.equals("")) {
            return "Campo requerido.";
        }
        UsuarioDAO daoU = new UsuarioDAOImpl();
        if (daoU.obtenerUsuarioPorCorreo(c) != null) {
            return "Correo ya exite.";
        }
        return null;
    }
}
