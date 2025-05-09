package com.example.grupo07_crudcinica.Aseguradora;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;

public class ActualizarAseguradoraActivity extends AppCompatActivity {

    Spinner spinnerIdAseguradora;
    EditText etNombre;
    Button btnActualizar;

    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIds = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_aseguradora);

        spinnerIdAseguradora = findViewById(R.id.spinnerIdAseguradora);
        etNombre = findViewById(R.id.etNombre);
        btnActualizar = findViewById(R.id.btnActualizar);

        dbHelper = new ClinicaDbHelper(this);
        cargarIdsAseguradora();

        btnActualizar.setOnClickListener(v -> {
            try {
                String idSeleccionado = spinnerIdAseguradora.getSelectedItem().toString();
                String nuevoNombre = etNombre.getText().toString();

                if (dbHelper.actualizarAseguradora(idSeleccionado, nuevoNombre)) {
                    Toast.makeText(this, "Aseguradora actualizada correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarIdsAseguradora() {
        Cursor cursor = dbHelper.consultarAseguradoras();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                listaIds.add(cursor.getString(0)); // ID_ASEGURADORA
            }
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdAseguradora.setAdapter(adapter);
    }
}
