package com.example.grupo07_crudcinica.Aseguradora;

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

public class AseguradoraMenuActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aseguradora_menu);

        btnInsertar = findViewById(R.id.btnInsertarAseguradora);
        btnConsultar = findViewById(R.id.btnConsultarAseguradora);
        btnActualizar = findViewById(R.id.btnActualizarAseguradora);
        btnEliminar = findViewById(R.id.btnEliminarAseguradora);

        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarAseguradoraActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarAseguradoraActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarAseguradoraActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarAseguradoraActivity.class)));
    }
}