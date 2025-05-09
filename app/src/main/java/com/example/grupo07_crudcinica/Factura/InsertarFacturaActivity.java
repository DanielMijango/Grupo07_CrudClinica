package com.example.grupo07_crudcinica.Factura;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import com.example.grupo07_crudcinica.R;

import java.util.Locale;

public class InsertarFacturaActivity extends AppCompatActivity {

    EditText edtIdConsulta, edtFechaFactura;
    Button btnGuardarFactura;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_insertar_factura);

        // Enlazar vistas
        edtIdConsulta = findViewById(R.id.edtIdConsulta);
        edtFechaFactura = findViewById(R.id.edtFechaFactura);
        btnGuardarFactura = findViewById(R.id.btnGuardarFactura);

        dbHelper = new ClinicaDbHelper(this);
        edtFechaFactura.setOnClickListener(v -> mostrarDatePicker());
        btnGuardarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarFactura();
            }
        });
    }
    private void mostrarDatePicker() {
        // Implementación del DatePickerDialog
        final java.util.Calendar calendario = java.util.Calendar.getInstance();
        int año = calendario.get(java.util.Calendar.YEAR);
        int mes = calendario.get(java.util.Calendar.MONTH);
        int dia = calendario.get(java.util.Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Formatear la fecha seleccionada como dd/MM/yyyy
                    String fechaSeleccionada = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    edtFechaFactura.setText(fechaSeleccionada);
                },
                año, mes, dia);

        datePickerDialog.show();
    }
    private void insertarFactura() {
        String idFactura = generarIdFactura(); // o puedes usar un campo adicional para escribirlo
        String idConsulta = edtIdConsulta.getText().toString().trim();
        String fechaFactura = edtFechaFactura.getText().toString().trim();

        if (idConsulta.isEmpty() || fechaFactura.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean insertado = dbHelper.insertarFactura(idFactura, idConsulta, fechaFactura);

        if (insertado) {
            Toast.makeText(this, "Factura guardada correctamente", Toast.LENGTH_SHORT).show();
            edtIdConsulta.setText("");
            edtFechaFactura.setText("");
        } else {
            Toast.makeText(this, "Error al guardar la factura", Toast.LENGTH_SHORT).show();
        }
    }

    // Método simple para generar un ID aleatorio tipo F001, F002, etc.
    private String generarIdFactura() {
        int numero = (int)(Math.random() * 900 + 100); // entre 100 y 999
        return "F" + numero;
    }
}