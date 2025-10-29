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

public class MainActivity extends AppCompatActivity {

    // 1. Declara los componentes y los arreglos
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;

    // Arreglos para almacenar los usuarios válidos
    // La contraseña en la posición 'i' corresponde al correo en la posición 'i'
    private String[] validEmails = {"admin@ari.com", "tecnico@ari.com", "gerente@ari.com"};
    private String[] validPasswords = {"admin123", "tecnico123", "gerente123"};

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

    /**
     * Método para manejar la lógica de inicio de sesión usando arreglos.
     */
    // Reemplaza este método en tu MainActivity.java

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean loginSuccessful = false;
        for (int i = 0; i < validEmails.length; i++) {
            if (email.equalsIgnoreCase(validEmails[i]) && password.equals(validPasswords[i])) {
                loginSuccessful = true;
                break;
            }
        }

        if (loginSuccessful) {
            Toast.makeText(this, "¡Login exitoso!", Toast.LENGTH_SHORT).show();

            // ---  LA SOLUCIÓN A LA SUPERPOSICIÓN ---

            // 1. Oculta el grupo de vistas de login
            findViewById(R.id.login_group).setVisibility(View.GONE);

            // 2. Muestra el contenedor de fragmentos
            findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

            // 3. Carga el fragmento como ya lo hacías
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new SeleccionIncidenteFragment())
                    .commit();

        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos.", Toast.LENGTH_LONG).show();
        }
    }
}