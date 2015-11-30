package co.edu.udea.pi.sjm.petted.util;

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
        if(u.getNombre().equals("")||u.getContraseña().equals("")){
            return 1;
        }
        if (!u.getContraseña().equals(contraseñaRep)){
            return 2;
        }
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
}
