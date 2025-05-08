package com.example.grupo07_crudcinica.Especialidad;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.AdapterView;
import com.example.grupo07_crudcinica.R;
import android.widget.TextView;
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

public class ActualizarEspecialidadActivity extends AppCompatActivity {

    Spinner spinnerIds;
    TextView txtNombreActual;
    EditText edtNuevoNombre;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;
    List<String> listaIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_especialidad);

        spinnerIds = findViewById(R.id.spinnerIdsActualizar);
        txtNombreActual = findViewById(R.id.txtNombreActual);
        edtNuevoNombre = findViewById(R.id.edtNuevoNombre);
        btnActualizar = findViewById(R.id.btnActualizar);

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

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEspecialidad();
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
            txtNombreActual.setText("Nombre actual: " + nombreActual);
            cursor.close();
        } else {
            txtNombreActual.setText("No encontrado");
        }
    }

    private void actualizarEspecialidad() {
        String idSeleccionado = spinnerIds.getSelectedItem().toString();
        String nuevoNombre = edtNuevoNombre.getText().toString().trim();

        if (nuevoNombre.isEmpty()) {
            Toast.makeText(this, "Ingrese el nuevo nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean actualizado = dbHelper.actualizarEspecialidad(idSeleccionado, nuevoNombre);

        if (actualizado) {
            Toast.makeText(this, "Especialidad actualizada correctamente", Toast.LENGTH_SHORT).show();
            mostrarNombreActual();  // Actualiza el nombre mostrado
        } else {
            Toast.makeText(this, "Error al actualizar especialidad", Toast.LENGTH_SHORT).show();
        }
    }
}