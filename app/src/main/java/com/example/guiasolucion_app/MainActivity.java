package com.example.guiasolucion_app;

import android.content.Intent; // Importa Intent para la navegación
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast; // Importa Toast para mostrar mensajes

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // 1. Declara las variables para los componentes de la UI
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // El código de los insets se mantiene igual
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Vincula las variables con los componentes del XML por su ID
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // 3. Configura el listener para el evento de clic en el botón
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método que contiene la lógica de login
                loginUser();
            }
        });
    }

    /**
     * Método para manejar la lógica de inicio de sesión.
     */
    private void loginUser() {
        // 4. Obtiene el texto de los campos de email y contraseña
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // 5. Realiza una validación simple
        if (email.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su correo electrónico", Toast.LENGTH_SHORT).show();
            return; // Detiene la ejecución si el campo está vacío
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su contraseña", Toast.LENGTH_SHORT).show();
            return; // Detiene la ejecución si el campo está vacío
        }

        // 6. Simula una autenticación (en un caso real, aquí llamarías a tu API o base de datos)
        if (email.equals("usuario@ari.com") && password.equals("123456")) {
            // Si las credenciales son correctas
            Toast.makeText(this, "¡Login exitoso!", Toast.LENGTH_LONG).show();

            // Aquí iría el código para navegar a la siguiente pantalla (por ejemplo, SeleccionIncidenteActivity)
            // Intent intent = new Intent(MainActivity.this, SeleccionIncidenteActivity.class);
            // startActivity(intent);
            // finish(); // Opcional: cierra la actividad de login para que el usuario no pueda volver con el botón de atrás

        } else {
            // Si las credenciales son incorrectas
            Toast.makeText(this, "Correo o contraseña incorrectos.", Toast.LENGTH_LONG).show();
        }
    }
}