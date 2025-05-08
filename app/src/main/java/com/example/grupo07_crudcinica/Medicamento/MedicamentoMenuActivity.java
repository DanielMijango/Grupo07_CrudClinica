package com.example.grupo07_crudcinica.Medicamento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.Clinica.ActualizarClinicaActivity;
import com.example.grupo07_crudcinica.Clinica.ConsultarClinicaActivity;
import com.example.grupo07_crudcinica.Clinica.EliminarClinicaActivity;
import com.example.grupo07_crudcinica.Clinica.InsertarClinicaActivity;
import com.example.grupo07_crudcinica.R;

public class MedicamentoMenuActivity extends AppCompatActivity {
    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento_menu);

        btnInsertar = findViewById(R.id.btnInsertarMedicamento);
        btnConsultar = findViewById(R.id.btnConsultarMedicamento);
        btnActualizar = findViewById(R.id.btnActualizarMedicamento);
        btnEliminar = findViewById(R.id.btnEliminarMedicamento);


        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarMedicamento.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarMedicamento.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarMedicamento.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarMedicamento.class)));

}

}