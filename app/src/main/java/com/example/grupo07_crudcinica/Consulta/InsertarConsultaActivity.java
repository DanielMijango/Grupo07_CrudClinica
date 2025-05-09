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
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class InsertarConsultaActivity extends AppCompatActivity {

    EditText edtFechaConsulta, edtEmergencia, edtCuota, edtDiagnostico;
    Spinner spinnerDoctor, spinnerPaciente;
    Button btnGuardar;
    ClinicaDbHelper dbHelper;
    ArrayAdapter<String> adapterDoctor, adapterPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_consulta);

        dbHelper = new ClinicaDbHelper(this);

        edtFechaConsulta = findViewById(R.id.edtFechaConsulta);
        edtEmergencia = findViewById(R.id.edtEmergencia);
        edtCuota = findViewById(R.id.edtCuota);
        edtDiagnostico = findViewById(R.id.edtDiagnostico);
        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        btnGuardar = findViewById(R.id.btnGuardarConsulta);

        edtFechaConsulta.setOnClickListener(view -> showDatePicker());

        adapterDoctor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getIds("DOCTOR"));
        adapterPaciente = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getIds("PACIENTE"));

        adapterDoctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterPaciente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDoctor.setAdapter(adapterDoctor);
        spinnerPaciente.setAdapter(adapterPaciente);

        btnGuardar.setOnClickListener(view -> {

           try {
               String idDoctor = spinnerDoctor.getSelectedItem().toString();
               String idPaciente = spinnerPaciente.getSelectedItem().toString();
               String fecha = edtFechaConsulta.getText().toString();
               String emergencia = edtEmergencia.getText().toString();
               double cuota = Double.parseDouble(edtCuota.getText().toString());
               String diagnostico = edtDiagnostico.getText().toString();

               long resultado = dbHelper.insertarConsulta(idDoctor, idPaciente, fecha, emergencia, cuota, diagnostico);

               if (resultado != -1) {
                   Toast.makeText(this, "Consulta insertada correctamente", Toast.LENGTH_SHORT).show();
                   limpiarCampos();
               } else {
                   Toast.makeText(this, "Error al insertar consulta", Toast.LENGTH_SHORT).show();
               }
           }catch (Exception e){
               Toast.makeText(this, "Error al insertar consulta llene todos los campos", Toast.LENGTH_SHORT).show();
           }
        });
    }

    private void showDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) ->
                edtFechaConsulta.setText(String.format("%04d-%02d-%02d", year, month + 1, day)),
                calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private ArrayList<String> getIds(String tabla) {
        ArrayList<String> lista = new ArrayList<>();
        Cursor cursor = null;

        switch (tabla) {
            case "DOCTOR":
                cursor = dbHelper.consultarDoctores(); break;
            case "PACIENTE":
                cursor = dbHelper.consultarPacientes(); break;
        }

        if (cursor != null) while (cursor.moveToNext()) lista.add(cursor.getString(0));
        if (cursor != null) cursor.close();

        return lista;
    }

    private void limpiarCampos() {
        edtFechaConsulta.setText("");
        edtEmergencia.setText("");
        edtCuota.setText("");
        edtDiagnostico.setText("");
    }
}