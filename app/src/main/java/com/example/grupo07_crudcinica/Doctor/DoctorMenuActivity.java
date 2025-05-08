package com.example.grupo07_crudcinica.Doctor;

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

public class DoctorMenuActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_menu);

        btnInsertar = findViewById(R.id.btnInsertarDoctor);
        btnConsultar = findViewById(R.id.btnConsultarDoctor);
        btnActualizar = findViewById(R.id.btnActualizarDoctor);
        btnEliminar = findViewById(R.id.btnEliminarDoctor);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarDoctorActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarDoctorActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarDoctorActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarDoctorActivity.class)));
    }
}