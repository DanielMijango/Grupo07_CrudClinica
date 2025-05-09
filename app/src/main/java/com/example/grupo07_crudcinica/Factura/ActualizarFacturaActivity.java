package com.example.grupo07_crudcinica.Factura;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

public class ActualizarFacturaActivity extends AppCompatActivity {

    // Variables de los EditText y botón
    private EditText edtIdFactura, edtNuevaFecha;
    private Button btnActualizarFecha;

    // Instancia del DbHelper para interactuar con la base de datos
    private ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_factura);

        // Inicialización de la base de datos
        dbHelper = new ClinicaDbHelper(this);

        // Referencia a los EditText y Button
        edtIdFactura = findViewById(R.id.edtIdFactura);
        edtNuevaFecha = findViewById(R.id.edtNuevaFecha);
        btnActualizarFecha = findViewById(R.id.btnActualizarFecha);

        // Configuramos el evento del botón para realizar la actualización
        btnActualizarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados
                String idFactura = edtIdFactura.getText().toString().trim();
                String nuevaFecha = edtNuevaFecha.getText().toString().trim();

                // Validación de campos
                if (idFactura.isEmpty() || nuevaFecha.isEmpty()) {
                    Toast.makeText(ActualizarFacturaActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Llamar a la función para actualizar la fecha de la factura
                    boolean actualizacionExitosa = dbHelper.actualizarFechaFactura(idFactura, nuevaFecha);

                    if (actualizacionExitosa) {
                        Toast.makeText(ActualizarFacturaActivity.this, "Factura actualizada exitosamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ActualizarFacturaActivity.this, "Error al actualizar la factura", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}