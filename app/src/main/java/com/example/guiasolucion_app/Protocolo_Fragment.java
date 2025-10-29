package com.example.guiasolucion_app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Protocolo_Fragment extends Fragment implements ProtocoloAdapter.OnPasoClickListener {

    // --- Variables de estado que necesitamos guardar ---
    private Date fechaInicio;
    private ArrayList<AccionBitacora> bitacora;
    // ---------------------------------------------

    private RecyclerView recyclerViewPasos;
    private TextView textViewTimer;
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;
    private long startTime = 0L;
    private ProtocoloAdapter adapter;
    private List<PasoProtocolo> listaDePasos;
    private String tituloIncidente = "";

    public Protocolo_Fragment() {}

    public static Protocolo_Fragment newInstance(String titulo) {
        Protocolo_Fragment fragment = new Protocolo_Fragment();
        Bundle args = new Bundle();
        args.putString("TITULO_INCIDENTE", titulo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // LÓGICA DE RESTAURACIÓN DE ESTADO
        if (savedInstanceState != null) {
            // Si hay un estado guardado, lo restauramos.
            bitacora = savedInstanceState.getParcelableArrayList("bitacora_state");
            fechaInicio = (Date) savedInstanceState.getSerializable("fecha_inicio_state");
        } else {
            // Si NO hay estado guardado (es la primera vez), creamos datos nuevos.
            bitacora = new ArrayList<>();
            fechaInicio = new Date();
        }

        if (getArguments() != null) {
            tituloIncidente = getArguments().getString("TITULO_INCIDENTE");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_protocolo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vinculación de vistas
        textViewTimer = view.findViewById(R.id.textViewTimer);
        recyclerViewPasos = view.findViewById(R.id.recyclerViewPasos);
        Button buttonMarcarResuelto = view.findViewById(R.id.buttonMarcarResuelto);
        Button buttonCancelar = view.findViewById(R.id.buttonCancelar);

        // Configuración del RecyclerView
        listaDePasos = generarPasosParaIncidente(tituloIncidente);
        adapter = new ProtocoloAdapter(listaDePasos, this);
        recyclerViewPasos.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewPasos.setAdapter(adapter);

        // Listeners de los botones
        buttonCancelar.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        buttonMarcarResuelto.setOnClickListener(v -> {
            if (timerHandler != null) timerHandler.removeCallbacks(timerRunnable);
            Date fechaFin = new Date();
            long duracionMillis = fechaFin.getTime() - fechaInicio.getTime();

            // Mensaje de depuración para ver el tamaño de la bitácora
            Toast.makeText(getContext(), "Enviando " + bitacora.size() + " acciones al informe.", Toast.LENGTH_LONG).show();

            int seconds = (int) (duracionMillis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            String tiempoFinalCalculado = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
            // Este mensaje te dirá cuántos elementos tiene la lista ANTES de enviarla.
            Toast.makeText(getContext(), "Enviando " + bitacora.size() + " acciones.", Toast.LENGTH_LONG).show();
            InformeIncidenteFragment informe = InformeIncidenteFragment.newInstance(
                    tituloIncidente, tiempoFinalCalculado, fechaInicio, fechaFin, bitacora, duracionMillis);

            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, informe).commit();
            }
        });

        iniciarCronometro();
    }

    // --- MÉTODO PARA GUARDAR EL ESTADO ---
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guardamos nuestras variables importantes aquí.
        outState.putParcelableArrayList("bitacora_state", bitacora);
        outState.putSerializable("fecha_inicio_state", fechaInicio);
    }

    @Override
    public void onPasoChecked(PasoProtocolo paso) {
        Toast.makeText(getContext(), "Check detectado: " + paso.getTitulo(), Toast.LENGTH_SHORT).show();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String horaActual = sdf.format(new Date());

        String descripcionCompleta = paso.getDescripcion();
        String rol = "Usuario";
        if (descripcionCompleta.contains("Responsable:")) {
            try {
                rol = descripcionCompleta.split("Responsable:")[1].split("\\|")[0].trim();
            } catch (Exception e) { /* Ignorar */ }
        }
        bitacora.add(new AccionBitacora(horaActual, "[" + rol + "]", paso.getTitulo()));
    }

    private List<PasoProtocolo> generarPasosParaIncidente(String titulo) {
        List<PasoProtocolo> pasos = new ArrayList<>();
        switch (titulo) {
            case "Falla Eléctrica General":
                pasos.add(new PasoProtocolo("Verificar fuente de alimentación", "Responsable: Equipo Técnico"));
                pasos.add(new PasoProtocolo("Inspeccionar tableros de control", "Responsable: Jefe de Operaciones"));
                pasos.add(new PasoProtocolo("Notificar a equipo de mantenimiento", "Responsable: Administración"));
                pasos.add(new PasoProtocolo("Activar generador de respaldo", "Responsable: Equipo de Seguridad"));
                pasos.add(new PasoProtocolo("Comunicar estado a la gerencia", "Responsable: Gerente General"));
                break;
            case "Caída de Red / Internet":
                pasos.add(new PasoProtocolo("Revisar conexión del router principal", "Responsable: Soporte TI"));
                pasos.add(new PasoProtocolo("Contactar al proveedor de servicios (ISP)", "Responsable: Administración"));
                pasos.add(new PasoProtocolo("Informar a los usuarios sobre la interrupción", "Responsable: Jefe de Operaciones"));
                break;
            default:
                pasos.add(new PasoProtocolo("No se encontraron pasos", "Verifique la configuración del protocolo."));
                break;
        }
        return pasos;
    }

    private void iniciarCronometro() {
        startTime = System.currentTimeMillis();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - startTime;
                int seconds = (int) (millis / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                textViewTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes % 60, seconds % 60));
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }
}