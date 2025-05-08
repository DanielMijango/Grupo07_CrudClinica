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

public class ActualizarTratamientoActivity extends AppCompatActivity {

    Spinner spinnerId;
    EditText edtConsulta, edtFecha, edtDescripcion;
    Button btnActualizar;
    ClinicaDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_tratamiento);

        spinnerId = findViewById(R.id.spinnerIdTratamiento);
        edtConsulta = findViewById(R.id.edtConsultaActualizar);
        edtFecha = findViewById(R.id.edtFechaActualizar);
        edtDescripcion = findViewById(R.id.edtDescripcionActualizar);
        btnActualizar = findViewById(R.id.btnActualizarTratamiento);

        db = new ClinicaDbHelper(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, db.obtenerIdsTratamientos());
        spinnerId.setAdapter(adapter);

        btnActualizar.setOnClickListener(v -> {
            String id = spinnerId.getSelectedItem().toString();
            String consulta = edtConsulta.getText().toString();
            String fecha = edtFecha.getText().toString();
            String descripcion = edtDescripcion.getText().toString();

            boolean actualizado = db.actualizarTratamiento(id, consulta, fecha, descripcion);
            Toast.makeText(this, actualizado ? "Actualizado" : "Error", Toast.LENGTH_SHORT).show();
        });
    }
}