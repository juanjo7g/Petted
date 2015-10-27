package co.edu.udea.pi.sjm.petted.vista.listadoCita;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dto.Cita;

/**
 * Created by Juan on 26/10/2015.
 */
public class CitaCustomAdapter extends BaseAdapter {

    private Context context;
    private List<Cita> listaCitas;
    private SimpleDateFormat formatoFechaHora = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.US);


    public CitaCustomAdapter(Context context, List<Cita> listaCitas) {
        this.context = context;
        this.listaCitas = listaCitas;
    }

    private class ViewHolder {
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

            convertView = mInflater.inflate(R.layout.item_cita_lista, null);

            holder = new ViewHolder();

            holder.tvNombre = (TextView) convertView.findViewById(R.id.tvNombreCita);
            holder.tvTipo = (TextView) convertView.findViewById(R.id.tvTipoCita1);
            holder.tvFecha = (TextView) convertView.findViewById(R.id.tvFechaCita);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Cita c = getItem(position);

        holder.tvNombre.setText(c.getNombre());
        holder.tvTipo.setText(c.getTipo());
        if (c.getFechaHora() != null) {
            holder.tvFecha.setText(formatoFechaHora.format(c.getFechaHora()));
        }

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
