package com.example.grupo07_crudcinica.Factura;

import android.annotation.SuppressLint;
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

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.*;
import android.database.Cursor;
public class InsertarFacturaActivity extends AppCompatActivity {

    Spinner spinnerConsulta, spinnerDetalle;
    EditText edtFechaFactura;
    Button btnGuardarFactura;
    ClinicaDbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_factura);

        spinnerConsulta = findViewById(R.id.spinnerConsultaFactura);
        spinnerDetalle = findViewById(R.id.spinnerDetalleFactura);
        edtFechaFactura = findViewById(R.id.edtFechaFactura);
        btnGuardarFactura = findViewById(R.id.btnGuardarFactura);

        dbHelper = new ClinicaDbHelper(this);

        edtFechaFactura.setOnClickListener(v -> mostrarDatePicker());

        ArrayAdapter<String> adapterConsulta = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                dbHelper.obtenerIdsConsultas());

        ArrayAdapter<String> adapterDetalle = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getIdsDetalleFactura());

        adapterConsulta.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDetalle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerConsulta.setAdapter(adapterConsulta);
        spinnerDetalle.setAdapter(adapterDetalle);

        btnGuardarFactura.setOnClickListener(v -> insertarFactura());
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fechaSeleccionada = String.format(Locale.getDefault(),
                            "%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                    edtFechaFactura.setText(fechaSeleccionada);
                },
                año, mes, dia);

        datePickerDialog.show();
    }

    private void insertarFactura() {
        String idFactura = generarIdFactura();
        String idConsulta = spinnerConsulta.getSelectedItem().toString();
        String fechaFactura = edtFechaFactura.getText().toString().trim();

        if (fechaFactura.isEmpty()) {
            Toast.makeText(this, "Por favor ingrese la fecha", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean insertado = dbHelper.insertarFactura(idFactura, idConsulta, fechaFactura);

        if (insertado) {
            Toast.makeText(this, "Factura guardada correctamente", Toast.LENGTH_SHORT).show();
            edtFechaFactura.setText("");
        } else {
            Toast.makeText(this, "Error al guardar la factura", Toast.LENGTH_SHORT).show();
        }
    }

    private String generarIdFactura() {
        int numero = (int)(Math.random() * 900 + 100);
        return "F" + numero;
    }

    private List<String> getIdsDetalleFactura() {
        List<String> ids = new ArrayList<>();
        Cursor cursor = dbHelper.obtenerTodosLosDetalles();
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(cursor.getColumnIndexOrThrow("ID_DETALLE")));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ids;
    }
}