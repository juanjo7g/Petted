package co.edu.udea.pi.sjm.petted.vista;

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
import co.edu.udea.pi.sjm.petted.dto.Mascota;

/**
 * Created by Juan on 20/09/2015.
 */
public class CustomAdapter extends BaseAdapter {

    Context context;
    List<Mascota> listaOpciones;

    public CustomAdapter(Context context, List<Mascota> opciones) {
        this.context = context;
        this.listaOpciones = opciones;
    }

    // Cambia dependiendo del layout
    private class viewHolder {

        ImageView ivImagen;
        TextView tvNombre;
        TextView tvTipo;
        TextView tvRaza;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_mascota_lista, null);

            holder = new viewHolder();

            // No estamos en un activity
            holder.ivImagen = (ImageView) convertView.findViewById(R.id.ivFoto);
            holder.tvNombre = (TextView) convertView
                    .findViewById(R.id.tvNombre);
            holder.tvTipo = (TextView) convertView.findViewById(R.id.tvTipo);
            holder.tvRaza = (TextView) convertView
                    .findViewById(R.id.tvRaza);
            convertView.setTag(holder);

        } else {
            holder = (viewHolder) convertView.getTag();
        }

        Mascota m = getItem(position);

        holder.tvNombre.setText(m.getNombre());
        holder.tvTipo.setText(m.getTipo());
        holder.tvRaza.setText(m.getRaza());

        switch (position) {
            case 0:
                holder.ivImagen.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.mascota1));
                break;
            case 1:
                holder.ivImagen.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.mascota2));
                break;
            case 2:
                holder.ivImagen.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.mascota3));
                break;
            case 3:
                holder.ivImagen.setImageDrawable(context.getResources()
                        .getDrawable(R.drawable.mascota4));
                break;
            default:
                break;
        }
        return convertView;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listaOpciones.size();
    }

    @Override
    public Mascota getItem(int arg0) {
        // TODO Auto-generated method stub
        return listaOpciones.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return listaOpciones.indexOf(getItem(arg0));
    }

}