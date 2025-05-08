package com.example.grupo07_crudcinica.Hospital;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.R;

public class MenuHospitalActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_hospital);

        btnInsertar = findViewById(R.id.btnInsertarHospital);
        btnConsultar = findViewById(R.id.btnConsultarHospital);
        btnActualizar = findViewById(R.id.btnActualizarHospital);
        btnEliminar = findViewById(R.id.btnEliminarHospital);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarHospitalActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarHospitalActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarHospitalActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarHospitalActivity.class)));
    }
}