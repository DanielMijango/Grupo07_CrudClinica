package com.example.grupo07_crudcinica.Clinica;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo07_crudcinica.R;

public class ActualizarClinicaActivity extends AppCompatActivity {

    EditText edtId, edtNuevoNombre, edtNuevaDireccion;
    Button btnActualizar;
    ClinicaDAO clinicaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_clinica);

        edtId = findViewById(R.id.edtIdActualizar);
        edtNuevoNombre = findViewById(R.id.edtNuevoNombre);
        edtNuevaDireccion = findViewById(R.id.edtNuevaDireccion);
        btnActualizar = findViewById(R.id.btnActualizar);

        clinicaDAO = new ClinicaDAO(this);

        btnActualizar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(edtId.getText().toString());
                String nombre = edtNuevoNombre.getText().toString();
                String direccion = edtNuevaDireccion.getText().toString();

                boolean actualizado = clinicaDAO.actualizarClinica(id, nombre, direccion);

                if (actualizado) {
                    Toast.makeText(this, "Clínica actualizada correctamente", Toast.LENGTH_SHORT).show();
                    edtId.setText("");
                    edtNuevoNombre.setText("");
                    edtNuevaDireccion.setText("");
                } else {
                    Toast.makeText(this, "No se encontró clínica con ese ID", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al actualizar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}