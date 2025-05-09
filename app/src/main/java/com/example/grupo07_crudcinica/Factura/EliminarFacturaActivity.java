package com.example.grupo07_crudcinica.Factura;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

public class EliminarFacturaActivity extends AppCompatActivity {

    Spinner spinnerFacturaEliminar;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_factura);

        spinnerFacturaEliminar = findViewById(R.id.spinnerEliminarFactura);
        btnEliminar = findViewById(R.id.btnEliminarFactura);
        dbHelper = new ClinicaDbHelper(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                dbHelper.consultarIds("FACTURA", "ID_FACTURA"));
        spinnerFacturaEliminar.setAdapter(adapter);

        btnEliminar.setOnClickListener(v -> {
            String id = spinnerFacturaEliminar.getSelectedItem().toString();
            boolean eliminado = dbHelper.eliminarFacturaYDetalles(id);
            Toast.makeText(this, eliminado ? "Factura eliminada" : "Error al eliminar", Toast.LENGTH_SHORT).show();
        });
    }
}