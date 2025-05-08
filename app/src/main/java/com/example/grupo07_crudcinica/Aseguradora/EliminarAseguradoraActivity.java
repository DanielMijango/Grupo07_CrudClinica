package com.example.grupo07_crudcinica.Aseguradora;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;

public class EliminarAseguradoraActivity extends AppCompatActivity {

    Spinner spinnerIdAseguradora;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIds = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_aseguradora);

        spinnerIdAseguradora = findViewById(R.id.spinnerIdAseguradora);
        btnEliminar = findViewById(R.id.btnEliminar);
        dbHelper = new ClinicaDbHelper(this);

        cargarIdsAseguradora();

        btnEliminar.setOnClickListener(v -> {
            String idSeleccionado = spinnerIdAseguradora.getSelectedItem().toString();

            if (dbHelper.eliminarAseguradora(idSeleccionado)) {
                Toast.makeText(this, "Aseguradora eliminada correctamente", Toast.LENGTH_SHORT).show();
                cargarIdsAseguradora(); // refrescar el spinner
            } else {
                Toast.makeText(this, "Error al eliminar aseguradora", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarIdsAseguradora() {
        listaIds.clear(); // limpiar antes de volver a cargar
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
