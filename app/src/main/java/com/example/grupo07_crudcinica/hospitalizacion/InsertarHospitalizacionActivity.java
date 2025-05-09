package com.example.grupo07_crudcinica.hospitalizacion;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
public class InsertarHospitalizacionActivity extends AppCompatActivity {

    private EditText editFechaIngreso, editFechaAlta;
    private Spinner spinnerPacientes, spinnerHospitales;
    private Button btnInsertar;
    private ClinicaDbHelper dbHelper;
    private List<String> listaIdsPacientes, listaIdsHospitales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_hospitalizacion);

        editFechaIngreso = findViewById(R.id.editFechaIngreso);
        editFechaAlta = findViewById(R.id.editFechaAlta);
        spinnerPacientes = findViewById(R.id.spinnerPacientes);
        spinnerHospitales = findViewById(R.id.spinnerHospitales);
        btnInsertar = findViewById(R.id.btnInsertarHospitalizacion);

        dbHelper = new ClinicaDbHelper(this);

        // Llenar spinner de pacientes
        listaIdsPacientes = dbHelper.obtenerTodosLosIdPacientes();
        ArrayAdapter<String> adapterPacientes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIdsPacientes);
        adapterPacientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPacientes.setAdapter(adapterPacientes);

        // Llenar spinner de hospitales
        listaIdsHospitales = dbHelper.obtenerTodosLosIdHospitales();
        ArrayAdapter<String> adapterHospitales = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaIdsHospitales);
        adapterHospitales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHospitales.setAdapter(adapterHospitales);

        // DatePickers
        editFechaIngreso.setOnClickListener(v -> mostrarDatePicker(editFechaIngreso));
        editFechaAlta.setOnClickListener(v -> mostrarDatePicker(editFechaAlta));

        btnInsertar.setOnClickListener(v -> {
            String fechaIngreso = editFechaIngreso.getText().toString();
            String fechaAlta = editFechaAlta.getText().toString();
            String idPacienteSeleccionado = spinnerPacientes.getSelectedItem().toString();
            // idHospitalSeleccionado se obtiene pero no se usa, por compatibilidad
            String idHospitalSeleccionado = spinnerHospitales.getSelectedItem().toString();

            long resultado = dbHelper.insertarHospitalizacion(idPacienteSeleccionado, fechaIngreso, fechaAlta);

            if (resultado != -1) {
                Toast.makeText(this, "Hospitalización insertada correctamente", Toast.LENGTH_SHORT).show();
                editFechaIngreso.setText("");
                editFechaAlta.setText("");
            } else {
                Toast.makeText(this, "Error al insertar hospitalización", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDatePicker(final EditText campoFecha) {
        final Calendar calendario = Calendar.getInstance();
        int anio = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth);
                    campoFecha.setText(fechaSeleccionada);
                }, anio, mes, dia);
        datePickerDialog.show();
    }
}
