package com.example.guiasolucion_app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Protocolo_Fragment extends Fragment {

    // --- Vistas de la UI ---
    private RecyclerView recyclerViewPasos;
    private TextView textViewTimer;

    // --- Variables para el Cronómetro ---
    private Handler timerHandler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;
    private long startTime = 0L;

    // --- Variables para la lista ---
    private ProtocoloAdapter adapter;
    private List<PasoProtocolo> listaDePasos;
    private String tituloIncidente = "";

    Button buttonCancelar;

    // Constructor público vacío requerido
    public Protocolo_Fragment() {}

    /**
     * Método estático para crear una instancia del fragmento y pasarle argumentos.
     * Esta es la forma recomendada de pasar datos a un fragmento.
     */
    public static Protocolo_Fragment newInstance(String titulo) { // ✅ AÑADE EL GUION BAJO
        Protocolo_Fragment fragment = new Protocolo_Fragment(); // ✅ AÑADE EL GUION BAJO
        Bundle args = new Bundle();
        args.putString("TITULO_INCIDENTE", titulo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Recibe el título desde los argumentos
        if (getArguments() != null) {
            tituloIncidente = getArguments().getString("TITULO_INCIDENTE");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Infla el layout del fragmento
        return inflater.inflate(R.layout.fragment_protocolo, container, false);
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vincula las vistas
        textViewTimer = view.findViewById(R.id.textViewTimer);
        recyclerViewPasos = view.findViewById(R.id.recyclerViewPasos);

        // 1. Vincula el nuevo botón de cancelar
        buttonCancelar = view.findViewById(R.id.buttonCancelar);

        // 2. Configura el listener para el clic
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. Llama a popBackStack para volver al fragmento anterior
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });
        // --- FIN DEL CÓDIGO A AÑADIR ---
        Button buttonMarcarResuelto = view.findViewById(R.id.buttonMarcarResuelto);

        buttonMarcarResuelto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. Detenemos el cronómetro para que no siga corriendo
                if (timerHandler != null && timerRunnable != null) {
                    timerHandler.removeCallbacks(timerRunnable);
                }

                // 2. Obtenemos el tiempo final que muestra el TextView
                String tiempoFinal = textViewTimer.getText().toString();

                // 3. Creamos una instancia del nuevo fragmento de informe
                InformeIncidenteFragment informeFragment = InformeIncidenteFragment.newInstance(tituloIncidente, tiempoFinal);

                // 4. Realizamos la transacción para mostrar el fragmento del informe
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, informeFragment)
                            // No usamos addToBackStack para que el usuario no pueda "volver" al protocolo ya resuelto
                            .commit();
                }
            }
        });
        // 1. Genera la lista de pasos dinámicamente
        listaDePasos = generarPasosParaIncidente(tituloIncidente);

        // 2. Configura el RecyclerView
        recyclerViewPasos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ProtocoloAdapter(listaDePasos);
        recyclerViewPasos.setAdapter(adapter);

        // 3. Inicia el cronómetro
        iniciarCronometro();
    }

    /**
     * Genera una lista de pasos específica basada en el título del incidente.
     */
    private List<PasoProtocolo> generarPasosParaIncidente(String titulo) {
        List<PasoProtocolo> pasos = new ArrayList<>();
        // Usamos un switch para decidir qué lista crear
        switch (titulo) {
            case "Falla Eléctrica General":
                pasos.add(new PasoProtocolo("Verificar fuente de alimentación", "Responsable: Equipo Técnico | Completado: 00:03:00"));
                pasos.add(new PasoProtocolo("Inspeccionar tableros de control", "Responsable: Jefe de Operaciones | RTO Objetivo: < 15 min"));
                pasos.add(new PasoProtocolo("Notificar a equipo de mantenimiento", "Responsable: Administración"));
                pasos.add(new PasoProtocolo("Activar generador de respaldo", "Responsable: Equipo de Seguridad"));
                pasos.add(new PasoProtocolo("Comunicar estado a la gerencia", "Responsable: Gerente General"));
                break;
            case "Caída de Red / Internet":
                pasos.add(new PasoProtocolo("Revisar conexión del router principal", "Responsable: Soporte TI"));
                pasos.add(new PasoProtocolo("Contactar al proveedor de servicios (ISP)", "Responsable: Administración"));
                pasos.add(new PasoProtocolo("Informar a los usuarios sobre la interrupción", "Responsable: Jefe de Operaciones"));
                break;
            // Añade más 'case' para los otros tipos de incidentes
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
                seconds = seconds % 60;
                minutes = minutes % 60;

                textViewTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerHandler.post(timerRunnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Detiene el cronómetro para evitar fugas de memoria
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

}