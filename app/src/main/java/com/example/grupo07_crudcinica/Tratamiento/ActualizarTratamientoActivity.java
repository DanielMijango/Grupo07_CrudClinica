package com.example.grupo07_crudcinica.Tratamiento;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.Calendar;
import java.util.List;

public class ActualizarTratamientoActivity extends AppCompatActivity {

    Spinner spinnerTratamientos, spinnerConsultas;
    EditText edtFecha, edtDescripcion;
    Button btnActualizar;
    ClinicaDbHelper dbHelper;
    String idTratamientoSeleccionado = "";
    String idConsultaSeleccionada = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_tratamiento);

        spinnerTratamientos = findViewById(R.id.spinnerTratamientos);
        spinnerConsultas = findViewById(R.id.spinnerConsultas);
        edtFecha = findViewById(R.id.edtFecha);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        btnActualizar = findViewById(R.id.btnActualizar);

        dbHelper = new ClinicaDbHelper(this);
        cargarSpinnerTratamientos();
        cargarSpinnerConsultas();

        edtFecha.setOnClickListener(v -> mostrarDatePicker());

        btnActualizar.setOnClickListener(v -> {
            String fecha = edtFecha.getText().toString();
            String descripcion = edtDescripcion.getText().toString();

            if (idTratamientoSeleccionado.isEmpty() || idConsultaSeleccionada.isEmpty()
                    || fecha.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean actualizado = dbHelper.actualizarTratamiento(
                    idTratamientoSeleccionado, idConsultaSeleccionada, fecha, descripcion
            );

            if (actualizado) {
                Toast.makeText(this, "Tratamiento actualizado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarSpinnerTratamientos() {
        List<String> ids = dbHelper.obtenerIdsTratamientos();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTratamientos.setAdapter(adapter);

        spinnerTratamientos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idTratamientoSeleccionado = parent.getItemAtPosition(position).toString();
                Cursor cursor = dbHelper.obtenerTratamientoPorId(idTratamientoSeleccionado);
                if (cursor != null && cursor.moveToFirst()) {
                    String idConsulta = cursor.getString(cursor.getColumnIndexOrThrow("ID_CONSULTA"));
                    edtFecha.setText(cursor.getString(cursor.getColumnIndexOrThrow("FECHA_TRATAMIENTO")));
                    edtDescripcion.setText(cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPCION")));

                    // seleccionar el ID_CONSULTA en el spinner de consultas
                    int posConsulta = ((ArrayAdapter<String>) spinnerConsultas.getAdapter()).getPosition(idConsulta);
                    if (posConsulta >= 0) {
                        spinnerConsultas.setSelection(posConsulta);
                    }

                    cursor.close();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idTratamientoSeleccionado = "";
            }
        });
    }

    private void cargarSpinnerConsultas() {
        List<String> ids = dbHelper.obtenerIdsConsultas();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConsultas.setAdapter(adapter);

        spinnerConsultas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idConsultaSeleccionada = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idConsultaSeleccionada = "";
            }
        });
    }

    private void mostrarDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String fecha = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
            edtFecha.setText(fecha);
        }, year, month, day);
        dialog.show();
    }
}