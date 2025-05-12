package com.example.grupo07_crudcinica.Doctor;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;

public class ActualizarDoctorActivity extends AppCompatActivity {

    Spinner spinnerIds;
    EditText etNombre, etApellido;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_doctor);

        spinnerIds = findViewById(R.id.spinnerIdActualizar);
        etNombre = findViewById(R.id.editTextNombreActualizar);
        etApellido = findViewById(R.id.editTextApellidoActualizar);
        btnActualizar = findViewById(R.id.btnActualizarDoctor);
        dbHelper = new ClinicaDbHelper(this);

        cargarIDs();

        spinnerIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String idSeleccionado = listaIds.get(position);
                Cursor cursor = dbHelper.obtenerDoctorPorId(idSeleccionado);
                if (cursor.moveToFirst()) {
                    etNombre.setText(cursor.getString(1));
                    etApellido.setText(cursor.getString(2));
                }
                cursor.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnActualizar.setOnClickListener(v -> {
            if (etNombre.getText().toString().trim().isEmpty()) {
                etNombre.setError("Ingrese el nombre del doctor");
                return;
            }

            if (etApellido.getText().toString().trim().isEmpty()) {
                etApellido.setError("Ingrese el apellido");
                return;
            }

            try {
                String id = spinnerIds.getSelectedItem().toString();
                String nuevoNombre = etNombre.getText().toString();
                String nuevoApellido = etApellido.getText().toString();

                if (dbHelper.actualizarDoctor(id, nuevoNombre, nuevoApellido)) {
                    Toast.makeText(this, "Doctor actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarIDs() {
        Cursor cursor = dbHelper.consultarDoctores();
        while (cursor.moveToNext()) {
            listaIds.add(cursor.getString(0));
        }
        cursor.close();
        spinnerIds.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaIds));
    }
}