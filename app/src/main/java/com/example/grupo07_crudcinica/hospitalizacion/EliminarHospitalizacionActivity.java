package com.example.grupo07_crudcinica.hospitalizacion;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import java.util.List;

public class EliminarHospitalizacionActivity extends AppCompatActivity {

    Spinner spinnerIdHospitalizacion;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;
    List<String> listaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_hospitalizacion);

        spinnerIdHospitalizacion = findViewById(R.id.spinnerIdHospitalizacion);
        btnEliminar = findViewById(R.id.btnEliminar);
        dbHelper = new ClinicaDbHelper(this);

        listaIds = dbHelper.obtenerIdsHospitalizacion();
        spinnerIdHospitalizacion.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIds));

        btnEliminar.setOnClickListener(v -> {
            String id = spinnerIdHospitalizacion.getSelectedItem().toString();
            dbHelper.eliminarHospitalizacion(id);
            Toast.makeText(this, "Eliminado correctamente", Toast.LENGTH_SHORT).show();
        });
    }
}