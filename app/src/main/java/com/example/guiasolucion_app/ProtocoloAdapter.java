package com.example.guiasolucion_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProtocoloAdapter extends RecyclerView.Adapter<ProtocoloAdapter.PasoViewHolder> {

    private List<PasoProtocolo> listaPasos;

    // Constructor que recibe la lista de datos
    public ProtocoloAdapter(List<PasoProtocolo> listaPasos) {
        this.listaPasos = listaPasos;
    }

    // Este método crea una nueva "fila" (ViewHolder) inflando el layout del item
    @NonNull
    @Override
    public PasoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_protocolo_paso, parent, false);
        return new PasoViewHolder(view);
    }

    // Este método conecta los datos de un paso con las vistas de una fila
    @Override
    public void onBindViewHolder(@NonNull PasoViewHolder holder, int position) {
        PasoProtocolo paso = listaPasos.get(position);
        holder.textViewTitulo.setText(paso.getTitulo());
        holder.textViewDescripcion.setText(paso.getDescripcion());
    }

    // Devuelve el número total de items en la lista
    @Override
    public int getItemCount() {
        return listaPasos.size();
    }

    /**
     * El ViewHolder representa una sola fila en la lista.
     * Almacena las referencias a las vistas para evitar llamara a findViewById() repetidamente.
     */
    public static class PasoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        TextView textViewDescripcion;
        // CheckBox checkBoxPaso; // Podrías añadirlo aquí si lo necesitas

        public PasoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTituloPaso);
            textViewDescripcion = itemView.findViewById(R.id.textViewDescripcionPaso);
            // checkBoxPaso = itemView.findViewById(R.id.checkboxPaso);
        }
    }
}