package com.example.grupo07_crudcinica.hospitalizacion;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.*;


public class InsertarHospitalizacionActivity extends AppCompatActivity {
    EditText editFechaIngreso, editFechaAlta;
    Spinner spinnerPacientes;
    Button btnInsertar;
    ClinicaDbHelper dbHelper;
    List<String> listaIdsPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_hospitalizacion);

        editFechaIngreso = findViewById(R.id.editFechaIngreso);
        editFechaAlta = findViewById(R.id.editFechaAlta);
        spinnerPacientes = findViewById(R.id.spinnerPacientes);
        btnInsertar = findViewById(R.id.btnInsertarHospitalizacion);

        dbHelper = new ClinicaDbHelper(this);
        listaIdsPacientes = dbHelper.obtenerTodosLosIdPacientes();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIdsPacientes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPacientes.setAdapter(adapter);

        editFechaIngreso.setOnClickListener(v -> mostrarDatePicker(editFechaIngreso));
        editFechaAlta.setOnClickListener(v -> mostrarDatePicker(editFechaAlta));

        btnInsertar.setOnClickListener(v -> {
            String fechaIngreso = editFechaIngreso.getText().toString();
            String fechaAlta = editFechaAlta.getText().toString();
            String idPaciente = spinnerPacientes.getSelectedItem() != null
                    ? spinnerPacientes.getSelectedItem().toString()
                    : "";

            long resultado = dbHelper.insertarHospitalizacion(idPaciente, fechaIngreso, fechaAlta);
            Toast.makeText(this, resultado != -1 ? "Insertado correctamente" : "Error al insertar", Toast.LENGTH_SHORT).show();
        });
    }

    private void mostrarDatePicker(EditText campoFecha) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String fecha = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
                    campoFecha.setText(fecha);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
