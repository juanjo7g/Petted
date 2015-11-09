package co.edu.udea.pi.sjm.petted.dtoParse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.UUID;

/**
 * Created by Juan on 05/11/2015.
 */
@ParseClassName("Mascota")
public class MascotaPO extends ParseObject {

    public String getNombre() {
        return getString("nombre");
    }

    public void setNombre(String nombre) {
        put("nombre", nombre);
    }

    public ParseUser getPropietario() {
        return getParseUser("propietario");
    }

    public void setPropietario(ParseUser propietario) {
        put("propietario", propietario);
    }

    public boolean isDraft() {
        return getBoolean("isDraft");
    }

    public void setDraft(boolean isDraft) {
        put("isDraft", isDraft);
    }

    public void setUuidString() {
        UUID uuid = UUID.randomUUID();
        put("uuid", uuid.toString());
    }

    public String getUuidString() {
        return getString("uuid");
    }

    public static ParseQuery<MascotaPO> getQuery() {
        return ParseQuery.getQuery(MascotaPO.class);
    }

}
