package com.example.grupo07_crudcinica.Aseguradora;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;

public class ConsultarAseguradoraActivity extends AppCompatActivity {

    Spinner spinnerIdAseguradora;
    EditText etNombre;
    Button btnBuscar;

    ArrayList<String> listaIds = new ArrayList<>();
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_aseguradora);

        spinnerIdAseguradora = findViewById(R.id.spinnerIdAseguradora);
        etNombre = findViewById(R.id.etNombre);
        btnBuscar = findViewById(R.id.btnBuscar);

        dbHelper = new ClinicaDbHelper(this);
        cargarIdsAseguradora();

        btnBuscar.setOnClickListener(v -> {
            String idSeleccionado = spinnerIdAseguradora.getSelectedItem().toString();
            Cursor cursor = dbHelper.obtenerAseguradoraPorId(idSeleccionado);

            if (cursor.moveToFirst()) {
                etNombre.setText(cursor.getString(1)); // NOMBRE_ASEGURADORA
            } else {
                etNombre.setText("No encontrada");
            }
            cursor.close();
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
