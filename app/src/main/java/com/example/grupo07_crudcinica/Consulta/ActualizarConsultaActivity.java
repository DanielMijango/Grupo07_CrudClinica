package com.example.grupo07_crudcinica.Consulta;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.*;

public class ActualizarConsultaActivity extends AppCompatActivity {

    Spinner spinnerConsultaId, spinnerDoctor, spinnerPaciente;
    EditText edtFecha, edtEmergencia, edtCuota, edtDiagnostico;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_consulta);

        dbHelper = new ClinicaDbHelper(this);

        spinnerConsultaId = findViewById(R.id.spinnerConsultaId);
        spinnerDoctor = findViewById(R.id.spinnerDoctorUpd);
        spinnerPaciente = findViewById(R.id.spinnerPacienteUpd);
        edtFecha = findViewById(R.id.edtFechaConsultaUpd);
        edtEmergencia = findViewById(R.id.edtEmergenciaUpd);
        edtCuota = findViewById(R.id.edtCuotaUpd);
        edtDiagnostico = findViewById(R.id.edtDiagnosticoUpd);
        btnActualizar = findViewById(R.id.btnActualizarConsulta);

        ArrayAdapter<String> adapterId = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dbHelper.obtenerIdsConsultas());
        spinnerConsultaId.setAdapter(adapterId);
        spinnerDoctor.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getIds("DOCTOR")));
        spinnerPaciente.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getIds("PACIENTE")));

        edtFecha.setOnClickListener(v -> showDatePicker());

        btnActualizar.setOnClickListener(v -> {
            if (edtFecha.getText().toString().trim().isEmpty()) {
                edtFecha.setError("Ingrese la fecha");
                return;
            }
            if (edtEmergencia.getText().toString().trim().isEmpty()) {
                edtEmergencia.setError("Ingrese la emergencia");
                return;
            }
            if (edtCuota.getText().toString().trim().isEmpty()) {
                edtCuota.setError("Ingrese la cuota");
                return;
            }
            try{
            String idConsulta = spinnerConsultaId.getSelectedItem().toString();
            String idDoctor = spinnerDoctor.getSelectedItem().toString();
            String idPaciente = spinnerPaciente.getSelectedItem().toString();
            String fecha = edtFecha.getText().toString();
            String emergencia = edtEmergencia.getText().toString();
            double cuota = Double.parseDouble(edtCuota.getText().toString());
            String diagnostico = edtDiagnostico.getText().toString();

            boolean actualizado = dbHelper.actualizarConsulta(idConsulta, idDoctor, idPaciente, fecha, emergencia, cuota, diagnostico);
            Toast.makeText(this, actualizado ? "Consulta actualizada" : "Error al actualizar", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error al actualizar llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) ->
                edtFecha.setText(String.format("%04d-%02d-%02d", year, month + 1, day)),
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private ArrayList<String> getIds(String tabla) {
        ArrayList<String> lista = new ArrayList<>();
        Cursor cursor = null;
        switch (tabla) {
            case "DOCTOR": cursor = dbHelper.consultarDoctores(); break;
            case "PACIENTE": cursor = dbHelper.consultarPacientes(); break;
        }
        if (cursor != null) while (cursor.moveToNext()) lista.add(cursor.getString(0));
        if (cursor != null) cursor.close();
        return lista;
    }
}