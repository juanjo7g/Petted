package co.edu.udea.pi.sjm.petted.vista.medicamento;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.pi.sjm.petted.R;
import co.edu.udea.pi.sjm.petted.dto.Medicamento;

/**
 * Created by Juan on 29/11/2015.
 */
public class MedicamentoCustomAdapter extends BaseAdapter {

    private Context context;
    private List<Medicamento> listaMedicamentos;


    public MedicamentoCustomAdapter(Context context, List<Medicamento> listaMedicamentos) {
        this.context = context;
        this.listaMedicamentos = listaMedicamentos;
    }

    private class ViewHolder {
        TextView tvNombre;
        TextView tvPesoDosis;
        TextView tvIntervaloDosis;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.item_medicamento_lista, null);

            holder = new ViewHolder();

            holder.tvNombre = (TextView) convertView.findViewById(R.id.tvNombreMedicamento);
            holder.tvPesoDosis = (TextView) convertView.findViewById(R.id.tvPesoDosisMedicamento);
            holder.tvIntervaloDosis = (TextView) convertView.findViewById(R.id.tvIntervaloDosisMedicamento);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Medicamento m = getItem(position);

        holder.tvNombre.setText(m.getNombre());
        holder.tvPesoDosis.setText(m.getPesoDosis() + " grs");
        if (m.getIntervaloDosis() == 1) {
            holder.tvIntervaloDosis.setText("Aplicar cada hora");
        } else {
            holder.tvIntervaloDosis.setText("Aplicar cada " + m.getIntervaloDosis() + " horas");
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return listaMedicamentos.size();
    }

    @Override
    public Medicamento getItem(int arg0) {
        return listaMedicamentos.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return listaMedicamentos.indexOf(getItem(arg0));
    }

}
