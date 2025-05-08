package com.example.grupo07_crudcinica.hospitalizacion;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import java.util.List;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;
import java.util.List;

public class ConsultarHospitalizacionActivity extends AppCompatActivity {

    Spinner spinnerIdHospitalizacion;
    TextView textViewPaciente, textViewFechaIngreso, textViewFechaAlta;
    ClinicaDbHelper dbHelper;
    List<String> listaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_hospitalizacion);

        spinnerIdHospitalizacion = findViewById(R.id.spinnerIdHospitalizacion);
        textViewPaciente = findViewById(R.id.textViewPaciente);
        textViewFechaIngreso = findViewById(R.id.textViewFechaIngreso);
        textViewFechaAlta = findViewById(R.id.textViewFechaAlta);
        dbHelper = new ClinicaDbHelper(this);

        listaIds = dbHelper.obtenerIdsHospitalizacion();

        // Asegurarse de que la lista no contiene nulls o vacíos
        listaIds.removeIf(id -> id == null || id.trim().isEmpty());
        listaIds.add(0, "Seleccione un ID");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdHospitalizacion.setAdapter(adapter);

        spinnerIdHospitalizacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedId = listaIds.get(position);

                if (position == 0 || selectedId == null || selectedId.trim().isEmpty()) {
                    textViewPaciente.setText("");
                    textViewFechaIngreso.setText("");
                    textViewFechaAlta.setText("");
                    return;
                }

                Cursor cursor = dbHelper.consultarHospitalizacion(selectedId);
                if (cursor != null && cursor.moveToFirst()) {
                    String idPaciente = cursor.getString(cursor.getColumnIndexOrThrow("ID_PACIENTE"));
                    String fechaIngreso = cursor.getString(cursor.getColumnIndexOrThrow("FECHA_INGRESO"));
                    String fechaAlta = cursor.getString(cursor.getColumnIndexOrThrow("FECHA_SALIDA"));

                    textViewPaciente.setText(idPaciente);
                    textViewFechaIngreso.setText(fechaIngreso);
                    textViewFechaAlta.setText(fechaAlta);

                    cursor.close();

                } else {
                    textViewPaciente.setText("");
                    textViewFechaIngreso.setText("");
                    textViewFechaAlta.setText("");
                    Toast.makeText(getApplicationContext(), "No se encontraron datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }
}