package com.example.guiasolucion_app;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SeleccionIncidenteFragment extends Fragment {

    public SeleccionIncidenteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_incidente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Vinculamos los botones
        Button btnFallaElectrica = view.findViewById(R.id.buttonFallaElectrica);
        Button btnCaidaRed = view.findViewById(R.id.buttonCaidaRed);
        Button btnSeguridad = view.findViewById(R.id.buttonSeguridad);
        Button btnServidor = view.findViewById(R.id.buttonServidor);

        // Configuramos los listeners
        btnFallaElectrica.setOnClickListener(v -> abrirProtocolo("Falla Eléctrica General"));
        btnCaidaRed.setOnClickListener(v -> abrirProtocolo("Caída de Red / Internet"));
        btnSeguridad.setOnClickListener(v -> abrirProtocolo("Incidente de Seguridad"));
        btnServidor.setOnClickListener(v -> abrirProtocolo("Falla de Servidor Crítico"));
    }

    private void abrirProtocolo(String tituloIncidente) {
        // Creamos una instancia del fragmento de protocolo, pasándole el título
        Protocolo_Fragment protocoloFragment = Protocolo_Fragment.newInstance(tituloIncidente);

        // Usamos el FragmentManager del Activity padre para hacer la transacción
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, protocoloFragment)
                .addToBackStack(null) // Esto permite al usuario volver atrás
                .commit();
    }
}