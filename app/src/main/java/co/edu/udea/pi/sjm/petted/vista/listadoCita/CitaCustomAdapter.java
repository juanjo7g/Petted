package co.edu.udea.pi.sjm.petted.vista.listadoCita;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dto.Cita;
import co.edu.udea.pi.sjm.petted.util.Utility;

/**
 * Created by Juan on 26/10/2015.
 */
public class CitaCustomAdapter extends BaseAdapter {

    Context context;
    List<Cita> listaCitas;

    public CitaCustomAdapter(Context context, List<Cita> listaCitas) {
        this.context = context;
        this.listaCitas = listaCitas;
    }

    // Cambia dependiendo del layout
    private class ViewHolder {
        ImageView ivImagen;
        TextView tvNombre;
        TextView tvTipo;
        TextView tvFecha;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_mascota_lista, null);

            holder = new ViewHolder();

            // No estamos en un activity
            holder.ivImagen = (ImageView) convertView.findViewById(R.id.ivFoto);
            holder.tvNombre = (TextView) convertView.findViewById(R.id.tvNombreCita);
            holder.tvTipo = (TextView) convertView.findViewById(R.id.tvTipoCita);
            holder.tvFecha = (TextView) convertView.findViewById(R.id.tvFechaCita);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cita c = getItem(position);

        holder.tvNombre.setText(c.getNombre());
        holder.tvTipo.setText(c.getTipo());
        holder.tvFecha.setText(c.getFechaHora().toString());

//        if (c.getFoto() != null) {
//            holder.ivImagen.setImageBitmap(Utility.getCircleBitmap(Utility.getFoto(c.getFoto())));
//        }

        return convertView;
    }

    @Override
    public int getCount() {
        return listaCitas.size();
    }

    @Override
    public Cita getItem(int arg0) {
        return listaCitas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return listaCitas.indexOf(getItem(arg0));
    }

}
