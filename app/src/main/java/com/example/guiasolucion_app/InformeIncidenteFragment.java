package com.example.guiasolucion_app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InformeIncidenteFragment extends Fragment {

    // Argumentos que recibiremos
    private static final String ARG_TITULO = "titulo_incidente";
    private static final String ARG_TIEMPO = "tiempo_final";

    private String tituloIncidente;
    private String tiempoFinal;

    public InformeIncidenteFragment() {
        // Required empty public constructor
    }

    /**
     * Método fábrica para crear una nueva instancia del fragmento
     * y pasarle los datos de forma segura.
     */
    public static InformeIncidenteFragment newInstance(String titulo, String tiempo) {
        InformeIncidenteFragment fragment = new InformeIncidenteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITULO, titulo);
        args.putString(ARG_TIEMPO, tiempo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tituloIncidente = getArguments().getString(ARG_TITULO);
            tiempoFinal = getArguments().getString(ARG_TIEMPO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_informe_incidente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vinculamos las vistas del XML del informe
        TextView tvTipo = view.findViewById(R.id.textViewTipoIncidente);
        TextView tvFecha = view.findViewById(R.id.textViewFechaIncidente);
        TextView tvRtoValue = view.findViewById(R.id.textViewRtoValue);
        // Puedes vincular el resto de vistas aquí...

        // Rellenamos las vistas con los datos recibidos
        tvTipo.setText("Tipo: " + tituloIncidente);
        tvRtoValue.setText(tiempoFinal);

        // Rellenamos datos autogenerados como la fecha actual
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
        String fechaActual = sdf.format(new Date());
        tvFecha.setText("Fecha: " + fechaActual);

        // Aquí podrías añadir lógica para el botón de exportar, etc.
    }
}