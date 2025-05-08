package com.example.grupo07_crudcinica.Especialidad;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;
import android.widget.AdapterView;

public class EliminarEspecialidadActivity extends AppCompatActivity {

    Spinner spinnerIds;
    TextView txtNombreActual;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;
    List<String> listaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_especialidad);

        spinnerIds = findViewById(R.id.spinnerIdsEliminar);
        txtNombreActual = findViewById(R.id.txtNombreActualEliminar);
        btnEliminar = findViewById(R.id.btnEliminar);

        dbHelper = new ClinicaDbHelper(this);

        cargarIdsEnSpinner();

        spinnerIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mostrarNombreActual();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                txtNombreActual.setText("");
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEspecialidad();
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

    private void mostrarNombreActual() {
        String idSeleccionado = spinnerIds.getSelectedItem().toString();
        Cursor cursor = dbHelper.obtenerEspecialidadPorId(idSeleccionado);
        if (cursor != null && cursor.moveToFirst()) {
            String nombreActual = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_ESPECIALIDAD"));
            txtNombreActual.setText("Nombre: " + nombreActual);
            cursor.close();
        } else {
            txtNombreActual.setText("No encontrado");
        }
    }

    private void eliminarEspecialidad() {
        String idSeleccionado = spinnerIds.getSelectedItem().toString();

        boolean eliminado = dbHelper.eliminarEspecialidad(idSeleccionado);

        if (eliminado) {
            Toast.makeText(this, "Especialidad eliminada correctamente", Toast.LENGTH_SHORT).show();
            listaIds.remove(idSeleccionado);
            ((ArrayAdapter) spinnerIds.getAdapter()).notifyDataSetChanged();
            txtNombreActual.setText("");
        } else {
            Toast.makeText(this, "Error al eliminar especialidad", Toast.LENGTH_SHORT).show();
        }
    }
}