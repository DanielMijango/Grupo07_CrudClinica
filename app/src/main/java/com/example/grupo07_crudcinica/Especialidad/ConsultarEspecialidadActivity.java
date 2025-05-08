package com.example.grupo07_crudcinica.Especialidad;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;
import java.util.List;

public class ConsultarEspecialidadActivity extends AppCompatActivity {

    Spinner spinnerIds;
    EditText edtNombreEspecialidad;
    Button btnBuscar;
    ClinicaDbHelper dbHelper;
    List<String> listaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_especialidad);

        spinnerIds = findViewById(R.id.spinnerIds);
        edtNombreEspecialidad = findViewById(R.id.edtNombreEspecialidad);
        btnBuscar = findViewById(R.id.btnBuscar);

        dbHelper = new ClinicaDbHelper(this);

        cargarIdsEnSpinner();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarEspecialidadPorId();
            }
        });
    }

    private void cargarIdsEnSpinner() {
        listaIds = new ArrayList<>();
        Cursor cursor = dbHelper.obtenerTodasEspecialidades();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("ID_ESPECIALIDAD"));
                listaIds.add(id);
            } while (cursor.moveToNext());
            cursor.close();
        } else {
            Toast.makeText(this, "No hay especialidades registradas", Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIds.setAdapter(adapter);
    }

    private void buscarEspecialidadPorId() {
        String idSeleccionado = spinnerIds.getSelectedItem().toString();

        Cursor cursor = dbHelper.obtenerTodasEspecialidades();
        boolean encontrado = false;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("ID_ESPECIALIDAD"));
                if (id.equals(idSeleccionado)) {
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_ESPECIALIDAD"));
                    edtNombreEspecialidad.setText(nombre);
                    encontrado = true;
                    break;
                }
            } while (cursor.moveToNext());
            cursor.close();
        }

        if (!encontrado) {
            Toast.makeText(this, "Especialidad no encontrada", Toast.LENGTH_SHORT).show();
            edtNombreEspecialidad.setText("");
        }
    }
}
