package com.example.grupo07_crudcinica.Tratamiento;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import android.database.Cursor;

public class ConsultarTratamientoActivity extends AppCompatActivity {

    Spinner spinnerIdTratamiento;
    TextView txtResultado;
    ClinicaDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tratamiento);

        spinnerIdTratamiento = findViewById(R.id.spinnerIdTratamiento);
        txtResultado = findViewById(R.id.txtResultadoTratamiento);
        db = new ClinicaDbHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, db.obtenerIdsTratamientos());
        spinnerIdTratamiento.setAdapter(adapter);

        spinnerIdTratamiento.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedId = parent.getItemAtPosition(position).toString();
                Cursor cursor = db.obtenerTratamientoPorId(selectedId);
                if (cursor.moveToFirst()) {
                    String info = "ID: " + cursor.getString(0) + "\n" +
                            "Consulta: " + cursor.getString(1) + "\n" +
                            "Fecha: " + cursor.getString(2) + "\n" +
                            "Descripción: " + cursor.getString(3);
                    txtResultado.setText(info);
                }
                cursor.close();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }
}