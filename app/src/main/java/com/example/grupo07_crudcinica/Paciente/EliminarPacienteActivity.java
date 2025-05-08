package com.example.grupo07_crudcinica.Paciente;

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
import android.database.Cursor;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;

public class EliminarPacienteActivity extends AppCompatActivity {
    Spinner spinnerPaciente;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_paciente);

        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        btnEliminar = findViewById(R.id.btnEliminarPaciente);
        dbHelper = new ClinicaDbHelper(this);

        cargarPacientes();

        btnEliminar.setOnClickListener(v -> {
            String id = listaIds.get(spinnerPaciente.getSelectedItemPosition());
            int resultado = dbHelper.eliminarPaciente(id);
            if (resultado > 0) {
                Toast.makeText(this, "Paciente eliminado correctamente", Toast.LENGTH_SHORT).show();
                recreate(); // recargar la lista
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarPacientes() {
        Cursor cursor = dbHelper.consultarPacientes();
        ArrayList<String> nombres = new ArrayList<>();
        while (cursor.moveToNext()) {
            listaIds.add(cursor.getString(0));
            nombres.add(cursor.getString(1) + " " + cursor.getString(2));
        }
        spinnerPaciente.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombres));
    }
}