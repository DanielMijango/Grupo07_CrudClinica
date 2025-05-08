package com.example.grupo07_crudcinica.Clinica;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

import com.example.grupo07_crudcinica.R;

public class ClinicaMenuActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinica_menu);

        btnInsertar = findViewById(R.id.btnInsertarClinica);
        btnConsultar = findViewById(R.id.btnConsultarClinica);
        btnActualizar = findViewById(R.id.btnActualizarClinica);
        btnEliminar = findViewById(R.id.btnEliminarClinica);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarClinicaActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarClinicaActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarClinicaActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarClinicaActivity.class)));
    }
}