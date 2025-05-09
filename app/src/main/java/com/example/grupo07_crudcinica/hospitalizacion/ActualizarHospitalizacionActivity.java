package com.example.grupo07_crudcinica.hospitalizacion;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import java.util.*;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import java.util.*;

public class ActualizarHospitalizacionActivity extends AppCompatActivity {

    Spinner spinnerIdHospitalizacion, spinnerPaciente;
    EditText editTextFechaIngreso, editTextFechaAlta;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;
    List<String> listaIds, listaPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_hospitalizacion);

        spinnerIdHospitalizacion = findViewById(R.id.spinnerIdHospitalizacion);
        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        editTextFechaIngreso = findViewById(R.id.editTextFechaIngreso);
        editTextFechaAlta = findViewById(R.id.editTextFechaAlta);
        btnActualizar = findViewById(R.id.btnActualizar);
        dbHelper = new ClinicaDbHelper(this);

        listaIds = dbHelper.obtenerIdsHospitalizacion();
        listaPacientes = dbHelper.obtenerIdsPacientes();

        ArrayAdapter<String> adapterHospitalizaciones = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIds);
        adapterHospitalizaciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdHospitalizacion.setAdapter(adapterHospitalizaciones);

        ArrayAdapter<String> adapterPacientes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaPacientes);
        adapterPacientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPaciente.setAdapter(adapterPacientes);

        spinnerIdHospitalizacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = listaIds.get(position);
                Cursor cursor = dbHelper.consultarHospitalizacion(selectedId);

                if (cursor != null && cursor.moveToFirst()) {
                    String idPaciente = cursor.getString(cursor.getColumnIndexOrThrow("ID_PACIENTE"));
                    String fechaIngreso = cursor.getString(cursor.getColumnIndexOrThrow("FECHA_INGRESO"));
                    String fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("FECHA_SALIDA"));

                    int indexPaciente = listaPacientes.indexOf(idPaciente);
                    if (indexPaciente >= 0) {
                        spinnerPaciente.setSelection(indexPaciente);
                    }

                    editTextFechaIngreso.setText(fechaIngreso);
                    editTextFechaAlta.setText(fechaAlta);
                    cursor.close();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        editTextFechaIngreso.setOnClickListener(v -> mostrarDatePicker(editTextFechaIngreso));
        editTextFechaAlta.setOnClickListener(v -> mostrarDatePicker(editTextFechaAlta));

        btnActualizar.setOnClickListener(v -> {
            try {
                String id = spinnerIdHospitalizacion.getSelectedItem().toString();
                String paciente = spinnerPaciente.getSelectedItem().toString();
                String ingreso = editTextFechaIngreso.getText().toString();
                String alta = editTextFechaAlta.getText().toString();

                boolean resultado = dbHelper.actualizarHospitalizacion(id, paciente, ingreso, alta);
                if (resultado) {
                    Toast.makeText(this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void mostrarDatePicker(EditText campo) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, day) ->
                campo.setText(year + "-" + (month + 1) + "-" + day),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}