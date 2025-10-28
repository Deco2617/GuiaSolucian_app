package com.example.guiasolucion_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Locale;


public class Protocolo_Fragment extends Fragment {

    private TextView textViewTimer;
    private Handler timerHandler;
    private Runnable timerRunnable;
    private long startTime = 0L; // Tiempo en milisegundos cuando inicia
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;

    public Protocolo_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_protocolo_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializa la vista del cronómetro
        textViewTimer = view.findViewById(R.id.textViewTimer);

        // --- Lógica del Cronómetro ---
        // Inicializa el Handler
        timerHandler = new Handler(Looper.getMainLooper());

        // Define el Runnable que se ejecutará cada segundo
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // Calcula el tiempo transcurrido
                timeInMilliseconds = System.currentTimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                // Convierte el tiempo a formato HH:MM:SS
                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                int hours = mins / 60;
                secs = secs % 60;

                // Formatea la cadena de tiempo
                String timeString = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, mins % 60, secs);

                // Actualiza el TextView
                textViewTimer.setText(timeString);

                // Llama a este mismo Runnable de nuevo después de 1000 milisegundos (1 segundo)
                timerHandler.postDelayed(this, 1000);
            }
        };

        // Inicia el cronómetro tan pronto como la vista se crea
        startTimer();
    }

    // Método para iniciar el cronómetro
    private void startTimer() {
        startTime = System.currentTimeMillis();
        // Inicia el ciclo del Handler. La primera ejecución es inmediata.
        timerHandler.post(timerRunnable);
    }

    // Método para detener el cronómetro
    private void stopTimer() {
        // Remueve cualquier callback pendiente del Runnable para detener el ciclo
        if (timerHandler != null && timerRunnable != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // ¡Muy importante! Detiene el cronómetro para evitar memory leaks
        stopTimer();
    }
}