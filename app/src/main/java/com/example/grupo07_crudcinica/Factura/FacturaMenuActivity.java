package com.example.grupo07_crudcinica.Factura;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.Consulta.ActualizarConsultaActivity;
import com.example.grupo07_crudcinica.Consulta.ConsultarConsultaActivity;
import com.example.grupo07_crudcinica.Consulta.EliminarConsultaActivity;
import com.example.grupo07_crudcinica.Consulta.InsertarConsultaActivity;
import com.example.grupo07_crudcinica.R;

public class FacturaMenuActivity extends AppCompatActivity {

    Button btnInsertar, btnConsultar, btnActualizar, btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_factura_menu);


        btnInsertar = findViewById(R.id.btnInsertarFactura);
        btnConsultar = findViewById(R.id.btnConsultarFactura);
        btnActualizar = findViewById(R.id.btnActualizarFactura);
        btnEliminar = findViewById(R.id.btnEliminarFactura);


        btnInsertar.setOnClickListener(v -> startActivity(new Intent(this, InsertarFacturaActivity.class)));
        btnConsultar.setOnClickListener(v -> startActivity(new Intent(this, ConsultarFacturaActivity.class)));
        btnActualizar.setOnClickListener(v -> startActivity(new Intent(this, ActualizarFacturaActivity.class)));
        btnEliminar.setOnClickListener(v -> startActivity(new Intent(this, EliminarFacturaActivity.class)));

    }
}