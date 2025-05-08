package com.example.grupo07_crudcinica.Especialidad;

import android.os.Bundle;
import com.example.grupo07_crudcinica.Especialidad.ActualizarEspecialidadActivity;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;


import android.content.Intent;
import android.widget.Button;



public class EspecialidadMenuActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_especialidad_menu);

        btnInsertar = findViewById(R.id.btnInsertarEspecialidad);
        btnConsultar = findViewById(R.id.btnConsultarEspecialidad);
        btnActualizar = findViewById(R.id.btnActualizarEspecialidad);
        btnEliminar = findViewById(R.id.btnEliminarEspecialidad);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarEspecialidadActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarEspecialidadActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarEspecialidadActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarEspecialidadActivity.class)));
    }
}