package com.example.grupo07_crudcinica.Hospital;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import android.database.Cursor;
import android.view.View;
import android.widget.*;

public class ActualizarHospitalActivity extends AppCompatActivity {

    Spinner spinner;
    EditText etNombre, etDireccion, etTelefono;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_hospital);

        spinner = findViewById(R.id.spinnerActualizarHospital);
        etNombre = findViewById(R.id.etActualizarNombre);
        etDireccion = findViewById(R.id.etActualizarDireccion);
        etTelefono = findViewById(R.id.etActualizarTelefono);
        btnActualizar = findViewById(R.id.btnActualizarHospital);
        dbHelper = new ClinicaDbHelper(this);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dbHelper.obtenerIdsHospitales());
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int hospitalId = (int) parent.getItemAtPosition(position);
                Cursor cursor = dbHelper.consultarHospitalPorId(hospitalId);
                if (cursor.moveToFirst()) {
                    etNombre.setText(cursor.getString(1));
                    etDireccion.setText(cursor.getString(2));
                    etTelefono.setText(cursor.getString(3));
                }
                cursor.close();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnActualizar.setOnClickListener(v -> {
            int hospitalId = (int) spinner.getSelectedItem();
            String nombre = etNombre.getText().toString();
            String direccion = etDireccion.getText().toString();
            String telefono = etTelefono.getText().toString();

            int result = dbHelper.actualizarHospital(hospitalId, nombre, direccion, telefono);

            Toast.makeText(this, result > 0 ? "Actualizado correctamente" : "Error al actualizar", Toast.LENGTH_SHORT).show();
        });
    }
}