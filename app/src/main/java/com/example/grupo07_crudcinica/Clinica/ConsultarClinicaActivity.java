package com.example.grupo07_crudcinica.Clinica;

import android.os.Bundle;

import android.database.Cursor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.R;

public class ConsultarClinicaActivity extends AppCompatActivity {

    EditText edtIdBuscar, edtNombre, edtDireccion;
    Button btnBuscar;
    ClinicaDAO clinicaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_clinica);

        edtIdBuscar = findViewById(R.id.edtIdBuscar);
        edtNombre = findViewById(R.id.edtNombre);
        edtDireccion = findViewById(R.id.edtDireccion);
        btnBuscar = findViewById(R.id.btnBuscar);

        clinicaDAO = new ClinicaDAO(this);

        btnBuscar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(edtIdBuscar.getText().toString());
                Cursor cursor = clinicaDAO.consultarClinicaPorId(id);

                if (cursor.moveToFirst()) {
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    String direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"));
                    edtNombre.setText(nombre);
                    edtDireccion.setText(direccion);
                } else {
                    Toast.makeText(this, "Clínica no encontrada", Toast.LENGTH_SHORT).show();
                    edtNombre.setText("");
                    edtDireccion.setText("");
                }

                cursor.close();
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}