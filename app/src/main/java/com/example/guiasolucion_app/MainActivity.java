package com.example.guiasolucion_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 1. Declara los componentes y los arreglos
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;

    // Arreglos para almacenar los usuarios válidos
    // La contraseña en la posición 'i' corresponde al correo en la posición 'i'
    private List<Usuario> listaUsuarios = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Inicializamos la lista de usuarios
        inicializarUsuarios();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void inicializarUsuarios() {
        listaUsuarios.add(new Usuario("Carlos Mendoza", "Fiscal Superior (Directivo)", "fiscal@ari.com", "fiscal123"));
        listaUsuarios.add(new Usuario("Ana Sánchez", "Gerente Administrativo (Gerencial)", "gerente@ari.com", "gerente123"));
        listaUsuarios.add(new Usuario("Juan Pérez", "Jefe de TI (Operativo)", "jefeti@ari.com", "jefeti123"));
        listaUsuarios.add(new Usuario("Maria Rodríguez", "Admin. de Red (Operativo)", "adminred@ari.com", "adminred123"));
        listaUsuarios.add(new Usuario("Luis Gonzales", "Equipo Técnico (Operativo)", "tecnico@ari.com", "tecnico123"));
    }

    /**
     * Método para manejar la lógica de inicio de sesión usando arreglos.
     */
    // Reemplaza este método en tu MainActivity.java

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // ... (validación de campos vacíos) ...

        Usuario usuarioLogueado = null;
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email) && usuario.getPassword().equals(password)) {
                usuarioLogueado = usuario;
                break;
            }
        }

        if (usuarioLogueado != null) {
            Toast.makeText(this, "Bienvenido, " + usuarioLogueado.getNombre(), Toast.LENGTH_SHORT).show();

            // Guardamos el usuario en nuestro Singleton
            CurrentUser.getInstance().setUsuario(usuarioLogueado);

            // ... (tu código para ocultar el login y mostrar el fragment)
            findViewById(R.id.login_group).setVisibility(View.GONE);
            findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SeleccionIncidenteFragment())
                    .commit();

        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos.", Toast.LENGTH_LONG).show();
        }
    }
}