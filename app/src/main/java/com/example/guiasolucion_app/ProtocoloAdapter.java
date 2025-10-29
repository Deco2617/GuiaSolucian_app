package com.example.guiasolucion_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProtocoloAdapter extends RecyclerView.Adapter<ProtocoloAdapter.PasoViewHolder> {

    // 1. La "interface" que actúa como un contrato para notificar al fragmento.
    public interface OnPasoClickListener {
        void onPasoChecked(PasoProtocolo paso);
    }

    private List<PasoProtocolo> listaPasos;
    private OnPasoClickListener listener;

    // 2. El constructor que recibe la lista y el "oyente" (el fragmento).
    public ProtocoloAdapter(List<PasoProtocolo> listaPasos, OnPasoClickListener listener) {
        this.listaPasos = listaPasos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PasoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_protocolo_paso, parent, false);
        return new PasoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasoViewHolder holder, int position) {
        PasoProtocolo paso = listaPasos.get(position);
        holder.textViewTitulo.setText(paso.getTitulo());
        holder.textViewDescripcion.setText(paso.getDescripcion());

        // --- ¡ESTA ES LA PARTE MÁS IMPORTANTE! ---
        // 3. Se asegura de que cada CheckBox en la lista tenga un listener.
        // Cuando se hace clic, llama al método en el fragmento.
        holder.checkBoxPaso.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPasoChecked(paso);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPasos.size();
    }

    // El ViewHolder que contiene las vistas de cada fila.
    public static class PasoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        TextView textViewDescripcion;
        CheckBox checkBoxPaso; // Variable para el CheckBox.

        public PasoViewHolder(@NonNull View itemView) {
            super(itemView);
            // Vincula las variables con los componentes del XML.
            textViewTitulo = itemView.findViewById(R.id.textViewTituloPaso);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcionPaso);
            checkBoxPaso = itemView.findViewById(R.id.checkboxPaso); // ¡Esta línea es crucial!
        }
    }
}