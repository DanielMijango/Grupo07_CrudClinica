package com.example.grupo07_crudcinica.Paciente;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;

public class ActualizarPacienteActivity extends AppCompatActivity {
    Spinner spinnerPaciente, spinnerAseguradora;
    EditText etNombre, etApellido, etDui;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIdsPaciente = new ArrayList<>();
    ArrayList<String> listaIdsAseguradora = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_paciente);

        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        spinnerAseguradora = findViewById(R.id.spinnerAseguradora);
        etNombre = findViewById(R.id.etNombrePaciente);
        etApellido = findViewById(R.id.etApellidoPaciente);
        etDui = findViewById(R.id.etDuiPaciente);
        btnActualizar = findViewById(R.id.btnActualizarPaciente);
        dbHelper = new ClinicaDbHelper(this);

        cargarPacientes();
        cargarAseguradoras();

        spinnerPaciente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String idPaciente = listaIdsPaciente.get(pos);
                Cursor c = dbHelper.obtenerPacientePorId(idPaciente);
                if (c.moveToFirst()) {
                    etNombre.setText(c.getString(1));
                    etApellido.setText(c.getString(2));
                    etDui.setText(c.getString(3));

                    // Seleccionar automáticamente la aseguradora correspondiente
                    String idAseguradora = c.getString(4);
                    int index = listaIdsAseguradora.indexOf(idAseguradora);
                    if (index != -1) {
                        spinnerAseguradora.setSelection(index);
                    }
                }
                c.close();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnActualizar.setOnClickListener(v -> {
            try{
            String idPaciente = listaIdsPaciente.get(spinnerPaciente.getSelectedItemPosition());
            String nombre = etNombre.getText().toString();
            String apellido = etApellido.getText().toString();
            String dui = etDui.getText().toString();
            String idAseguradora = listaIdsAseguradora.get(spinnerAseguradora.getSelectedItemPosition());

            boolean resultado = dbHelper.actualizarPaciente(idPaciente, nombre, apellido, dui, idAseguradora);
            if (resultado) {
                Toast.makeText(this, "Paciente actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar paciente", Toast.LENGTH_SHORT).show();
            }
            } catch (Exception e) {
                Toast.makeText(this, "Error al actualizar paciente llene todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarPacientes() {
        Cursor cursor = dbHelper.consultarPacientes();
        ArrayList<String> nombres = new ArrayList<>();
        listaIdsPaciente.clear();
        while (cursor.moveToNext()) {
            listaIdsPaciente.add(cursor.getString(0)); // ID_PACIENTE
            nombres.add(cursor.getString(1) + " " + cursor.getString(2)); // Nombre + Apellido
        }
        spinnerPaciente.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombres));
        cursor.close();
    }

    private void cargarAseguradoras() {
        Cursor cursor = dbHelper.consultarAseguradoras();
        ArrayList<String> nombres = new ArrayList<>();
        listaIdsAseguradora.clear();
        while (cursor.moveToNext()) {
            listaIdsAseguradora.add(cursor.getString(0)); // ID_ASEGURADORA
            nombres.add(cursor.getString(1)); // Nombre de aseguradora
        }
        spinnerAseguradora.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombres));
        cursor.close();
    }
}
