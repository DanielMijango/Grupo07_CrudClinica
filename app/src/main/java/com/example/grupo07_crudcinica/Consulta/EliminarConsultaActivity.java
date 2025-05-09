package com.example.grupo07_crudcinica.Consulta;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.grupo07_crudcinica.databinding.ActivityEliminarConsultaBinding;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EliminarConsultaActivity extends AppCompatActivity {

    Spinner spinnerIdConsulta;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_consulta);

        spinnerIdConsulta = findViewById(R.id.spinnerEliminarConsulta);
        btnEliminar = findViewById(R.id.btnEliminarConsulta);
        dbHelper = new ClinicaDbHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                dbHelper.obtenerIdsConsultas());
        spinnerIdConsulta.setAdapter(adapter);

        btnEliminar.setOnClickListener(view -> {
            String id = spinnerIdConsulta.getSelectedItem().toString();
            boolean eliminado = dbHelper.eliminarConsulta(id);
            Toast.makeText(this, eliminado ? "Consulta eliminada" : "Error al eliminar", Toast.LENGTH_SHORT).show();
        });
    }
}