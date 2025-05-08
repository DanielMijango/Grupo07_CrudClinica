package com.example.grupo07_crudcinica.Clinica;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.R;

public class EliminarClinicaActivity extends AppCompatActivity {

    EditText edtIdEliminar;
    Button btnEliminar;
    ClinicaDAO clinicaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_clinica);

        edtIdEliminar = findViewById(R.id.edtIdEliminar);
        btnEliminar = findViewById(R.id.btnEliminar);

        clinicaDAO = new ClinicaDAO(this);

        btnEliminar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(edtIdEliminar.getText().toString());
                boolean eliminado = clinicaDAO.eliminarClinica(id);

                if (eliminado) {
                    Toast.makeText(this, "Clínica eliminada correctamente", Toast.LENGTH_SHORT).show();
                    edtIdEliminar.setText("");
                } else {
                    Toast.makeText(this, "No se encontró clínica con ese ID", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}