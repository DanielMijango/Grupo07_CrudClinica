package com.example.grupo07_crudcinica.Tratamiento;

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

public class InsertarTratamientoActivity extends AppCompatActivity {

    EditText edtIdConsulta, edtFecha, edtDescripcion;
    Button btnGuardar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_tratamiento);

        edtIdConsulta = findViewById(R.id.edtIdConsulta);
        edtFecha = findViewById(R.id.edtFecha);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        btnGuardar = findViewById(R.id.btnGuardarTratamiento);

        dbHelper = new ClinicaDbHelper(this);

        // Mostrar DatePicker al hacer clic en edtFecha
        edtFecha.setOnClickListener(v -> mostrarDatePicker());

        btnGuardar.setOnClickListener(v -> {
            String idConsulta = edtIdConsulta.getText().toString();
            String fecha = edtFecha.getText().toString();
            String descripcion = edtDescripcion.getText().toString();

            long result = dbHelper.insertarTratamiento(idConsulta, fecha, descripcion);
            if (result != -1) {
                Toast.makeText(this, "Tratamiento insertado", Toast.LENGTH_SHORT).show();
                edtIdConsulta.setText("");
                edtFecha.setText("");
                edtDescripcion.setText("");
            } else {
                Toast.makeText(this, "Error al insertar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int año = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String fechaSeleccionada = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth);
                    edtFecha.setText(fechaSeleccionada);
                }, año, mes, dia);
        dialog.show();
    }
}