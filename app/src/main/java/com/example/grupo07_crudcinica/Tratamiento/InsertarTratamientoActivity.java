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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.Calendar;

import android.app.DatePickerDialog;
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

public class InsertarTratamientoActivity extends AppCompatActivity {

    Spinner spinnerConsulta;
    EditText edtFecha, edtDescripcion;
    Button btnGuardar;
    ClinicaDbHelper dbHelper;
    String idConsultaSeleccionada = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_tratamiento);

        spinnerConsulta = findViewById(R.id.spinnerConsulta);
        edtFecha = findViewById(R.id.edtFecha);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        btnGuardar = findViewById(R.id.btnGuardar);

        dbHelper = new ClinicaDbHelper(this);
        cargarSpinnerConsultas();

        edtFecha.setOnClickListener(v -> mostrarDatePicker());

        btnGuardar.setOnClickListener(v -> {
            String fecha = edtFecha.getText().toString();
            String descripcion = edtDescripcion.getText().toString();

            if (idConsultaSeleccionada.isEmpty() || fecha.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            long resultado = dbHelper.insertarTratamiento(idConsultaSeleccionada, fecha, descripcion);

            if (resultado != -1) {
                Toast.makeText(this, "Tratamiento insertado correctamente", Toast.LENGTH_SHORT).show();
                edtFecha.setText("");
                edtDescripcion.setText("");
            } else {
                Toast.makeText(this, "Error al insertar tratamiento", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarSpinnerConsultas() {
        List<String> ids = dbHelper.obtenerIdsConsultas();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConsulta.setAdapter(adapter);

        spinnerConsulta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String fechaSeleccionada = year1 + "-" + (month1 + 1) + "-" + dayOfMonth;
            edtFecha.setText(fechaSeleccionada);
        }, year, month, day);
        datePickerDialog.show();
    }
}