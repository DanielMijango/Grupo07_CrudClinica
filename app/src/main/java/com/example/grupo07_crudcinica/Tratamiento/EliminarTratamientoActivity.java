package com.example.grupo07_crudcinica.Tratamiento;

import android.annotation.SuppressLint;
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

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.List;

public class EliminarTratamientoActivity extends AppCompatActivity {

    Spinner spinnerTratamientos;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;
    String idTratamientoSeleccionado = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_tratamiento);

        spinnerTratamientos = findViewById(R.id.spinnerTratamientos);
        btnEliminar = findViewById(R.id.btnEliminar);
        dbHelper = new ClinicaDbHelper(this);

        cargarSpinnerTratamientos();

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!idTratamientoSeleccionado.isEmpty()) {
                    boolean eliminado = dbHelper.eliminarTratamiento(idTratamientoSeleccionado);
                    if (eliminado) {
                        Toast.makeText(EliminarTratamientoActivity.this, "Tratamiento eliminado", Toast.LENGTH_SHORT).show();
                        cargarSpinnerTratamientos(); // refrescar lista
                    } else {
                        Toast.makeText(EliminarTratamientoActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void cargarSpinnerTratamientos() {
        List<String> ids = dbHelper.obtenerIdsTratamientos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTratamientos.setAdapter(adapter);

        spinnerTratamientos.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                idTratamientoSeleccionado = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                idTratamientoSeleccionado = "";
            }
        });
    }
}