package com.example.grupo07_crudcinica.Tratamiento;

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

public class MenuTratamientoActivity extends AppCompatActivity {
    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tratamiento);

        btnInsertar = findViewById(R.id.btnInsertarTratamiento);
        btnConsultar = findViewById(R.id.btnConsultarTratamiento);
        btnActualizar = findViewById(R.id.btnActualizarTratamiento);
        btnEliminar = findViewById(R.id.btnEliminarTratamiento);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarTratamientoActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarTratamientoActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarTratamientoActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarTratamientoActivity.class)));
    }
}