package com.example.grupo07_crudcinica.Paciente;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;

public class ConsultarPacienteActivity extends AppCompatActivity {
    Spinner spinnerPaciente;
    TextView tvNombre, tvApellido, tvDui, tvAseguradora;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIds = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_paciente);

        spinnerPaciente = findViewById(R.id.spinnerPaciente);
        tvNombre = findViewById(R.id.tvNombrePaciente);
        tvApellido = findViewById(R.id.tvApellidoPaciente);
        tvDui = findViewById(R.id.tvDuiPaciente);
        tvAseguradora = findViewById(R.id.tvAseguradoraPaciente);
        dbHelper = new ClinicaDbHelper(this);

        cargarPacientes();

        spinnerPaciente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String idPaciente = listaIds.get(pos);
                Cursor c = dbHelper.obtenerPacientePorId(idPaciente);
                if (c.moveToFirst()) {
                    String nombre = c.getString(c.getColumnIndexOrThrow("NOMBRE_PACIENTE"));
                    String apellido = c.getString(c.getColumnIndexOrThrow("APELLIDO_PACIENTE"));
                    String dui = c.getString(c.getColumnIndexOrThrow("DUI_PACIENTE"));
                    String idAseguradora = c.getString(c.getColumnIndexOrThrow("ID_ASEGURADORA"));

                    tvNombre.setText(nombre);
                    tvApellido.setText(apellido);
                    tvDui.setText(dui);

                    // Obtener nombre de aseguradora
                    Cursor aseg = dbHelper.obtenerAseguradoraPorId(idAseguradora);
                    if (aseg.moveToFirst()) {
                        String nombreAseguradora = aseg.getString(aseg.getColumnIndexOrThrow("NOMBRE_ASEGURADORA"));
                        tvAseguradora.setText(nombreAseguradora);
                    } else {
                        tvAseguradora.setText("No encontrada");
                    }
                    aseg.close();
                }
                c.close();
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void cargarPacientes() {
        Cursor cursor = dbHelper.consultarPacientes();
        ArrayList<String> nombres = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            listaIds.add(id);
            nombres.add(nombre + " " + apellido);
        }
        spinnerPaciente.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombres));
        cursor.close();
    }
}
