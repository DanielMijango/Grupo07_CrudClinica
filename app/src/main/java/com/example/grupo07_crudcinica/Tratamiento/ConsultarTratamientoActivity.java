package com.example.grupo07_crudcinica.Tratamiento;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.List;

public class ConsultarTratamientoActivity extends AppCompatActivity {

    Spinner spinnerTratamientos;
    TextView txtIdConsulta, txtFecha, txtDescripcion;
    ClinicaDbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tratamiento);

        spinnerTratamientos = findViewById(R.id.spinnerTratamientos);
        txtIdConsulta = findViewById(R.id.txtIdConsulta);
        txtFecha = findViewById(R.id.txtFecha);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        dbHelper = new ClinicaDbHelper(this);
        cargarSpinnerTratamientos();
    }

    private void cargarSpinnerTratamientos() {
        List<String> ids = dbHelper.obtenerIdsTratamientos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTratamientos.setAdapter(adapter);

        spinnerTratamientos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String idTratamiento = parent.getItemAtPosition(position).toString();
                Cursor cursor = dbHelper.obtenerTratamientoPorId(idTratamiento);
                if (cursor != null && cursor.moveToFirst()) {
                    txtIdConsulta.setText(cursor.getString(cursor.getColumnIndexOrThrow("ID_CONSULTA")));
                    txtFecha.setText(cursor.getString(cursor.getColumnIndexOrThrow("FECHA_TRATAMIENTO")));
                    txtDescripcion.setText(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPCION")));
                    cursor.close();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}