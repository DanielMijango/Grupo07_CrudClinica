package com.example.grupo07_crudcinica.hospitalizacion;


import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.R;





public class hospitalizacionMenuActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitalizacion_menu);

        btnInsertar = findViewById(R.id.btnIrInsertarHospitalizacion);
        btnConsultar = findViewById(R.id.btnIrConsultarHospitalizacion);
        btnActualizar = findViewById(R.id.btnIrActualizarHospitalizacion);
        btnEliminar = findViewById(R.id.btnIrEliminarHospitalizacion);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarHospitalizacionActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarHospitalizacionActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarHospitalizacionActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarHospitalizacionActivity.class)));
    }
}
