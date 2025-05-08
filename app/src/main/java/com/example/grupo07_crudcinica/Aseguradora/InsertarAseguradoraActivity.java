package com.example.grupo07_crudcinica.Aseguradora;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

public class InsertarAseguradoraActivity extends AppCompatActivity {

    EditText etIdAseguradora, etNombre;
    Button btnInsertar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_aseguradora);

        etIdAseguradora = findViewById(R.id.etIdAseguradora);
        etNombre = findViewById(R.id.etNombre);
        btnInsertar = findViewById(R.id.btnInsertar);

        btnInsertar.setOnClickListener(v -> {
            String id = etIdAseguradora.getText().toString().trim();
            String nombre = etNombre.getText().toString().trim();

            if (id.isEmpty() || nombre.isEmpty()) {
                Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            ClinicaDbHelper dbHelper = new ClinicaDbHelper(this);
            long resultado = dbHelper.insertarAseguradora(id, nombre);

            if (resultado != -1) {
                Toast.makeText(this, "Aseguradora insertada correctamente", Toast.LENGTH_SHORT).show();
                etIdAseguradora.setText("");
                etNombre.setText("");
            } else {
                Toast.makeText(this, "Error al insertar aseguradora", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
