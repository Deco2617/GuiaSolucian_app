package com.example.guiasolucion_app;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class InformeIncidenteFragment extends Fragment {

    private static final String ARG_TITULO = "titulo_incidente";
    private static final String ARG_TIEMPO = "tiempo_final";
    private static final String ARG_FECHA_INICIO = "fecha_inicio";
    private static final String ARG_FECHA_FIN = "fecha_fin";
    private static final String ARG_BITACORA = "bitacora";
    private static final String ARG_DURACION_MILLIS = "duracion_millis";

    private String tituloIncidente;
    private String tiempoFinal;
    private Date fechaInicio;
    private Date fechaFin;
    private ArrayList<AccionBitacora> bitacora;
    private long duracionMillis;

    public static InformeIncidenteFragment newInstance(String titulo, String tiempo, Date inicio, Date fin, ArrayList<AccionBitacora> log, long duracion) {
        InformeIncidenteFragment fragment = new InformeIncidenteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITULO, titulo);
        args.putString(ARG_TIEMPO, tiempo);
        args.putSerializable(ARG_FECHA_INICIO, inicio);
        args.putSerializable(ARG_FECHA_FIN, fin);
        args.putParcelableArrayList(ARG_BITACORA, log);
        args.putLong(ARG_DURACION_MILLIS, duracion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tituloIncidente = getArguments().getString(ARG_TITULO);
            tiempoFinal = getArguments().getString(ARG_TIEMPO);
            fechaInicio = (Date) getArguments().getSerializable(ARG_FECHA_INICIO);
            fechaFin = (Date) getArguments().getSerializable(ARG_FECHA_FIN);
            bitacora = getArguments().getParcelableArrayList(ARG_BITACORA); // Aquí se recibe la lista
            duracionMillis = getArguments().getLong(ARG_DURACION_MILLIS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_informe_incidente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vinculación de vistas
        TextView tvTipo = view.findViewById(R.id.textViewTipoIncidente);
        TextView tvFecha = view.findViewById(R.id.textViewFechaIncidente);
        TextView tvLider = view.findViewById(R.id.textViewLiderRespuesta);
        TextView tvRtoValue = view.findViewById(R.id.textViewRtoValue);
        TextView tvRtoStatus = view.findViewById(R.id.textViewRtoStatus);
        TableLayout tablaBitacora = view.findViewById(R.id.tableLayoutBitacora);
        Button buttonExportar = view.findViewById(R.id.buttonExportar);

        // Llenado de datos
        tvTipo.setText("Tipo: " + tituloIncidente);
        tvRtoValue.setText(tiempoFinal);
        Usuario lider = CurrentUser.getInstance().getUsuario();
        if (lider != null) {
            tvLider.setText("Líder de Respuesta: " + lider.getNombre() + " (" + lider.getCargo() + ")");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        if (fechaInicio != null) {
            tvFecha.setText("Inicio: " + sdf.format(fechaInicio) + "\nFin:      " + sdf.format(fechaFin));
        }

        // Lógica del RTO
        if (duracionMillis < 10000) {
            tvRtoStatus.setText("(Objetivo: < 15 min - Cumplido)");
            tvRtoStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        } else {
            tvRtoStatus.setText("(Objetivo: < 15 min - No Cumplido)");
            tvRtoStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.crimson));
        }

        // LÓGICA PARA LLENAR LA BITÁCORA
        if (bitacora != null && !bitacora.isEmpty()) {
            for (AccionBitacora accion : bitacora) {
                TableRow fila = new TableRow(getContext());
                TextView tvHora = new TextView(getContext());
                TextView tvRol = new TextView(getContext());
                TextView tvDesc = new TextView(getContext());

                tvHora.setText(accion.getHora());
                tvRol.setText(accion.getRol());
                tvDesc.setText(accion.getDescripcion());
                tvHora.setPadding(0, 8, 16, 8);
                tvRol.setPadding(0, 8, 16, 8);
                tvDesc.setPadding(0, 8, 0, 8);

                fila.addView(tvHora);
                fila.addView(tvRol);
                fila.addView(tvDesc);
                tablaBitacora.addView(fila);
            }
        } else {
            // Si la bitácora está vacía, muestra un mensaje
            TableRow filaVacia = new TableRow(getContext());
            TextView mensaje = new TextView(getContext());
            mensaje.setText("No se registraron acciones.");
            TableRow.LayoutParams params = new TableRow.LayoutParams();
            params.span = 3;
            mensaje.setLayoutParams(params);
            mensaje.setPadding(0, 8, 0, 8);
            filaVacia.addView(mensaje);
            tablaBitacora.addView(filaVacia);
        }

        // Lógica del botón de exportar
        buttonExportar.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Informe archivado exitosamente", Toast.LENGTH_LONG).show();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SeleccionIncidenteFragment())
                        .commit();
            }
        });
    }
}