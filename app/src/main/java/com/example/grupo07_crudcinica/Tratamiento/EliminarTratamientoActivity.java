package com.example.grupo07_crudcinica.Tratamiento;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

public class EliminarTratamientoActivity extends AppCompatActivity {

    Spinner spinnerEliminar;
    Button btnEliminar;
    ClinicaDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_tratamiento);

        spinnerEliminar = findViewById(R.id.spinnerEliminarTratamiento);
        btnEliminar = findViewById(R.id.btnEliminarTratamiento);
        db = new ClinicaDbHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, db.obtenerIdsTratamientos());
        spinnerEliminar.setAdapter(adapter);

        btnEliminar.setOnClickListener(v -> {
            String id = spinnerEliminar.getSelectedItem().toString();
            boolean eliminado = db.eliminarTratamiento(id);
            Toast.makeText(this, eliminado ? "Eliminado" : "Error al eliminar", Toast.LENGTH_SHORT).show();

            // Actualizar lista del Spinner
            ArrayAdapter<String> newAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, db.obtenerIdsTratamientos());
            spinnerEliminar.setAdapter(newAdapter);
        });
    }
}