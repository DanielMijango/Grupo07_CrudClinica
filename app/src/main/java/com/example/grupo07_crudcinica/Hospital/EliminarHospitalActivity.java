package com.example.grupo07_crudcinica.Hospital;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import android.database.Cursor;
import android.view.View;
import android.widget.*;

public class EliminarHospitalActivity extends AppCompatActivity {

    Spinner spinner;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_hospital);

        spinner = findViewById(R.id.spinnerEliminarHospital);
        btnEliminar = findViewById(R.id.btnEliminarHospital);
        dbHelper = new ClinicaDbHelper(this);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dbHelper.obtenerIdsHospitales());
        spinner.setAdapter(adapter);

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                int hospitalId = (int) spinner.getSelectedItem();
                int result = dbHelper.eliminarHospital(hospitalId);
                Toast.makeText(getApplicationContext(), result > 0 ? "Eliminado" : "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}