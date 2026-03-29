package com.example.healtymind;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Habito_Adaptater extends RecyclerView.Adapter<Habito_Adaptater.ViewHolder> {

    private List<DiaHabito> listaDias;

    public Habito_Adaptater(List<DiaHabito> listaDias) {
        this.listaDias = listaDias;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaHabito dia = listaDias.get(position);
        holder.tvDia.setText(String.valueOf(dia.getNumeroDia()));

        if (dia.isCompletado()) {
            // Fondo verde si está hecho
            holder.viewCirculo.setBackgroundResource(R.drawable.circle_green);
            holder.tvDia.setTextColor(Color.WHITE);
        } else {
            // Fondo vacío si no
            holder.viewCirculo.setBackgroundResource(R.drawable.circle_gray);
            holder.tvDia.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return listaDias.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDia;
        View viewCirculo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDia = itemView.findViewById(R.id.tvNumeroDia);
            viewCirculo = itemView.findViewById(R.id.viewCirculo);
        }
    }
}