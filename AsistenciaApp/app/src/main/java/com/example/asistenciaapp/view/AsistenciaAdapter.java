package com.example.asistenciaapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asistenciaapp.R;
import com.example.asistenciaapp.model.Asistencia;

import java.util.List;

public class AsistenciaAdapter extends RecyclerView.Adapter<AsistenciaAdapter.ViewHolder> {

    private List<Asistencia> asistencias;

    public AsistenciaAdapter(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asistencia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (asistencias == null || asistencias.isEmpty()) {
            return; // no hay datos
        }

        Asistencia asistencia = asistencias.get(position);

        // Manejo seguro de valores null
        holder.tvNombre.setText(asistencia.getNombre() != null ? asistencia.getNombre() : "Sin nombre");
        holder.tvFecha.setText(asistencia.getFecha() != null ? asistencia.getFecha() : "Sin fecha");
        holder.tvHora.setText(asistencia.getHora() != null ? asistencia.getHora() : "Sin hora");
    }

    @Override
    public int getItemCount() {
        return asistencias != null ? asistencias.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvFecha, tvHora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvHora = itemView.findViewById(R.id.tvHora);
        }
    }
}
