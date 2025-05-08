package com.example.grupo07_crudcinica.Consulta;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.Medicamento.ActualizarMedicamento;
import com.example.grupo07_crudcinica.Medicamento.ConsultarMedicamento;
import com.example.grupo07_crudcinica.Medicamento.EliminarMedicamento;
import com.example.grupo07_crudcinica.Medicamento.InsertarMedicamento;
import com.example.grupo07_crudcinica.R;

public class ConsultaMenuActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consulta_menu);


        btnInsertar = findViewById(R.id.btnInsertarConsulta);
        btnConsultar = findViewById(R.id.btnConsultarConsulta);
        btnActualizar = findViewById(R.id.btnActualizarConsulta);
        btnEliminar = findViewById(R.id.btnEliminarConsulta);


        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarConsultaActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarConsultaActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarConsultaActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarConsultaActivity.class)));


    }
}