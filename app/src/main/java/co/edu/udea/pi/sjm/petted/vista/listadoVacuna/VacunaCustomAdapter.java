package co.edu.udea.pi.sjm.petted.vista.listadoVacuna;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dto.Vacuna;
import co.edu.udea.pi.sjm.petted.util.Utility;

/**
 * Created by Juan on 27/10/2015.
 */
public class VacunaCustomAdapter extends BaseAdapter {

    private Context context;
    private List<Vacuna> listaVacunas;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public VacunaCustomAdapter(Context context, List<Vacuna> listaVacunas) {
        this.context = context;
        this.listaVacunas = listaVacunas;
    }

    private class ViewHolder {
        ImageView ivValidacion;
        TextView tvNombre;
        TextView tvFecha;
        TextView tvFechaProxima;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_vacuna_lista, null);

            holder = new ViewHolder();

            holder.ivValidacion = (ImageView) convertView.findViewById(R.id.ivValidacionVacuna);
            holder.tvNombre = (TextView) convertView.findViewById(R.id.tvNombreVacuna);
            holder.tvFecha = (TextView) convertView.findViewById(R.id.tvFechaVacuna);
            holder.tvFechaProxima = (TextView) convertView.findViewById(R.id.tvFechaVacunaProxima);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Vacuna v = getItem(position);

        holder.tvNombre.setText(v.getNombre());

        if (v.getFecha() != null) {
            holder.tvFecha.setText(formatoFecha.format(v.getFecha()));
        }
        if (v.getFechaProxima() != null) {
            holder.tvFechaProxima.setText(formatoFecha.format(v.getFechaProxima()));
        }

        if (v.getValidacion() != null) {
            holder.ivValidacion.setImageBitmap(Utility.getFoto(v.getValidacion()));
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return listaVacunas.size();
    }

    @Override
    public Vacuna getItem(int arg0) {
        return listaVacunas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return listaVacunas.indexOf(getItem(arg0));
    }

}
