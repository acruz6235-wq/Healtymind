package com.example.healtymind;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Habito_Adaptater extends RecyclerView.Adapter<Habito_Adaptater.HabitoViewHolder> {

    // lista de datos
    List<DiaHabito> listaDias;

    // 2. El Constructor para recibir la lista
    public Habito_Adaptater(List<DiaHabito> listaDias) {
        this.listaDias = listaDias;
    }

    @NonNull
    @Override
    public HabitoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el diseño del circulito (item_dia.xml)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dia, parent, false);
        return new HabitoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitoViewHolder holder, int position) {
        // Obtenemos los datos del objeto actual
        DiaHabito dia = listaDias.get(position);

        // Ponemos el número en el TextView
        holder.tvNum.setText(String.valueOf(dia.num_dia));

        // Lógica de color: Verde si está hecho, gris si no
        if (dia.dia_hecho) {
            holder.circulo.setBackgroundResource(R.drawable.circle_green);
        } else {
            holder.circulo.setBackgroundResource(R.drawable.circle_gray);
        }
    }

    @Override
    public int getItemCount() {
        // Retorna el tamaño de la lista
        return listaDias.size();
    }

    // 3. El ViewHolder (El mapa de IDs del XML)
    public static class HabitoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNum;
        View circulo;

        public HabitoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vinculamos con los IDs reales de tu item_dia.xml
            tvNum = itemView.findViewById(R.id.tvNumeroDia);
            circulo = itemView.findViewById(R.id.viewCirculo);
        }
    }
}