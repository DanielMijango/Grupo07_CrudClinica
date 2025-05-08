package com.example.grupo07_crudcinica.Hospital;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class InsertarHospitalActivity extends AppCompatActivity {

    EditText etNombre, etDireccion, etTelefono;
    Button btnGuardar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_hospital);

        etNombre = findViewById(R.id.etNombreHospital);
        etDireccion = findViewById(R.id.etDireccionHospital);
        etTelefono = findViewById(R.id.etTelefonoHospital);
        btnGuardar = findViewById(R.id.btnGuardarHospital);
        dbHelper = new ClinicaDbHelper(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String direccion = etDireccion.getText().toString();
                String telefono = etTelefono.getText().toString();

                long resultado = dbHelper.insertarHospital(nombre, direccion, telefono);

                if (resultado != -1) {
                    Toast.makeText(getApplicationContext(), "Hospital insertado", Toast.LENGTH_SHORT).show();
                    etNombre.setText("");
                    etDireccion.setText("");
                    etTelefono.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Error al insertar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}