package com.example.grupo07_crudcinica.Paciente;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;

public class InsertarPacienteActivity extends AppCompatActivity {
    EditText etNombre, etApellido, etDui;
    Spinner spinnerAseguradoras;
    Button btnGuardar;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIdsAseguradora;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_paciente);


        etNombre = findViewById(R.id.etNombrePaciente);
        etApellido = findViewById(R.id.etApellidoPaciente);
        etDui = findViewById(R.id.etDuiPaciente);
        spinnerAseguradoras = findViewById(R.id.spinnerAseguradoraPaciente);
        btnGuardar = findViewById(R.id.btnGuardarPaciente);

        dbHelper = new ClinicaDbHelper(this);
        cargarIdsAseguradoras();

        btnGuardar.setOnClickListener(v -> {
            try{
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String dui = etDui.getText().toString();
            String idAseguradora = spinnerAseguradoras.getSelectedItem().toString();

            long resultado = dbHelper.insertarPaciente(nombre, apellido, dui, idAseguradora);
            if (resultado != -1) {
                Toast.makeText(this, "Paciente insertado correctamente", Toast.LENGTH_SHORT).show();
                etNombre.setText("");
                etApellido.setText("");
                etDui.setText("");
                spinnerAseguradoras.setSelection(0);
            } else {
                Toast.makeText(this, "Error al insertar", Toast.LENGTH_SHORT).show();
            }
            } catch (Exception e) {
                Toast.makeText(this, "Error al insertar paciente llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cargarIdsAseguradoras() {
        listaIdsAseguradora = new ArrayList<>();
        Cursor cursor = dbHelper.consultarAseguradoras();
        if (cursor.moveToFirst()) {
            do {
                listaIdsAseguradora.add(cursor.getString(0)); // ID_ASEGURADORA
            } while (cursor.moveToNext());
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIdsAseguradora);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAseguradoras.setAdapter(adapter);
    }
}
