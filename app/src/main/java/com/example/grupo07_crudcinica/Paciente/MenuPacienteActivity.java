package com.example.grupo07_crudcinica.Paciente;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import android.view.View;
import android.widget.Button;

public class MenuPacienteActivity extends AppCompatActivity {
    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_paciente);

        btnInsertar = findViewById(R.id.btnInsertarPaciente);
        btnConsultar = findViewById(R.id.btnConsultarPaciente);
        btnActualizar = findViewById(R.id.btnActualizarPaciente);
        btnEliminar = findViewById(R.id.btnEliminarPaciente);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarPacienteActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarPacienteActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarPacienteActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarPacienteActivity.class)));
    }
}