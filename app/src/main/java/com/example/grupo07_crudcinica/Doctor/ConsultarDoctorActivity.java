package com.example.grupo07_crudcinica.Doctor;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;

public class ConsultarDoctorActivity extends AppCompatActivity {

    Spinner spinnerDoctores;
    TextView tvNombre, tvApellido;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_doctor);

        spinnerDoctores = findViewById(R.id.spinnerDoctorId);
        tvNombre = findViewById(R.id.textViewNombreDoc);
        tvApellido = findViewById(R.id.textViewApellidoDoc);
        dbHelper = new ClinicaDbHelper(this);

        cargarIDs();

        spinnerDoctores.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                String idSeleccionado = listaIds.get(position);
                Cursor cursor = dbHelper.obtenerDoctorPorId(idSeleccionado);
                if (cursor.moveToFirst()) {
                    tvNombre.setText(cursor.getString(1));
                    tvApellido.setText(cursor.getString(2));
                }
                cursor.close();
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private void cargarIDs() {
        Cursor cursor = dbHelper.consultarDoctores();
        while (cursor.moveToNext()) {
            listaIds.add(cursor.getString(0));
        }
        cursor.close();
        spinnerDoctores.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaIds));
    }
}