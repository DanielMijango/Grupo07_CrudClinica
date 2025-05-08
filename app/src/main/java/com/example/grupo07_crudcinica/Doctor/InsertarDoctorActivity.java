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
import java.util.HashMap;

public class InsertarDoctorActivity extends AppCompatActivity {
    EditText etNombre, etApellido;
    Spinner spinnerEspecialidad;
    Button btnGuardar;

    ClinicaDbHelper dbHelper;
    ArrayList<String> listaEspecialidades;
    HashMap<String, String> especialidadMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_doctor);

        etNombre = findViewById(R.id.etNombreDoctor);
        etApellido = findViewById(R.id.etApellidoDoctor);
        spinnerEspecialidad = findViewById(R.id.spinnerEspecialidad);
        btnGuardar = findViewById(R.id.btnGuardarDoctor);

        dbHelper = new ClinicaDbHelper(this);
        listaEspecialidades = new ArrayList<>();
        especialidadMap = new HashMap<>();

        cargarEspecialidades();

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String seleccion = spinnerEspecialidad.getSelectedItem().toString();
            String idEspecialidad = especialidadMap.get(seleccion);

            long resultado = dbHelper.insertarDoctor(nombre, apellido, idEspecialidad);
            if (resultado != -1) {
                Toast.makeText(this, "Doctor insertado correctamente", Toast.LENGTH_SHORT).show();
                etNombre.setText("");
                etApellido.setText("");
            } else {
                Toast.makeText(this, "Error al insertar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarEspecialidades() {
        Cursor cursor = dbHelper.consultarEspecialidades();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String nombre = cursor.getString(1);
            listaEspecialidades.add(nombre);
            especialidadMap.put(nombre, id);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaEspecialidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecialidad.setAdapter(adapter);
    }
}