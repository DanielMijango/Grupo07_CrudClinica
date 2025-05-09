package com.example.grupo07_crudcinica.Factura;

import android.annotation.SuppressLint;
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

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.*;

public class ActualizarFacturaActivity extends AppCompatActivity {

    Spinner spinnerIdFactura;
    EditText edtFecha;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_factura);

        spinnerIdFactura = findViewById(R.id.spinnerFacturaActualizar);
        edtFecha = findViewById(R.id.edtFechaActualizarFactura);
        btnActualizar = findViewById(R.id.btnActualizarFactura);
        dbHelper = new ClinicaDbHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                dbHelper.consultarIds("FACTURA", "ID_FACTURA"));
        spinnerIdFactura.setAdapter(adapter);

        edtFecha.setOnClickListener(v -> showDatePicker());

        btnActualizar.setOnClickListener(v -> {
            try {
                String idFactura = spinnerIdFactura.getSelectedItem().toString();
                String nuevaFecha = edtFecha.getText().toString();
                boolean exito = dbHelper.actualizarFechaFactura(idFactura, nuevaFecha);
                Toast.makeText(this, exito ? "Factura actualizada" : "Error al actualizar", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error al actualizar, llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendario = Calendar.getInstance();
        new DatePickerDialog(this,
                (view, year, month, day) -> edtFecha.setText(String.format("%04d-%02d-%02d", year, month + 1, day)),
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }
}