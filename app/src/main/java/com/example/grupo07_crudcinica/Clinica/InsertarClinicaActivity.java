package com.example.grupo07_crudcinica.Clinica;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertarClinicaActivity extends AppCompatActivity {

    EditText edtId, edtNombre, edtDireccion;
    Spinner spinnerDistrito, spinnerEspecialidad;
    Button btnInsertar;

    ClinicaDAO clinicaDAO;

    Map<String, Integer> distritoMap;
    Map<String, String> especialidadMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_clinica);

        edtId = findViewById(R.id.edtId);
        edtNombre = findViewById(R.id.edtNombre);
        edtDireccion = findViewById(R.id.edtDireccion);
        spinnerDistrito = findViewById(R.id.spinnerDistrito);
        spinnerEspecialidad = findViewById(R.id.spinnerEspecialidad);
        btnInsertar = findViewById(R.id.btnInsertar);

        clinicaDAO = new ClinicaDAO(this);

        // Cargar distritos
        List<Distrito> listaDistritos = clinicaDAO.obtenerDistritos();
        List<String> nombresDistritos = new ArrayList<>();
        distritoMap = new HashMap<>();

        for (Distrito d : listaDistritos) {
            nombresDistritos.add(d.getNombre());
            distritoMap.put(d.getNombre(), d.getId());
        }

        ArrayAdapter<String> adapterDistritos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresDistritos);
        adapterDistritos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDistrito.setAdapter(adapterDistritos);

        // Cargar especialidades
        List<Especialidad> listaEspecialidades = clinicaDAO.obtenerEspecialidades();
        List<String> nombresEspecialidades = new ArrayList<>();
        especialidadMap = new HashMap<>();

        for (Especialidad e : listaEspecialidades) {
            nombresEspecialidades.add(e.getNombre());
            especialidadMap.put(e.getNombre(), e.getId());
        }

        ArrayAdapter<String> adapterEspecialidades = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresEspecialidades);
        adapterEspecialidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEspecialidad.setAdapter(adapterEspecialidades);

        btnInsertar.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(edtId.getText().toString());
                String nombre = edtNombre.getText().toString();
                String direccion = edtDireccion.getText().toString();

                String nombreDistrito = spinnerDistrito.getSelectedItem().toString();
                int idDistrito = distritoMap.get(nombreDistrito);

                String nombreEspecialidad = spinnerEspecialidad.getSelectedItem().toString();
                String idEspecialidad = especialidadMap.get(nombreEspecialidad);

                boolean insertado = clinicaDAO.insertarClinica(id, nombre, direccion, idDistrito);

                if (insertado) {
                    clinicaDAO.insertarClinicaEspecialidad(id, idEspecialidad);

                    Toast.makeText(this, "Clínica insertada correctamente", Toast.LENGTH_SHORT).show();
                    edtId.setText("");
                    edtNombre.setText("");
                    edtDireccion.setText("");
                    spinnerDistrito.setSelection(0);
                    spinnerEspecialidad.setSelection(0);
                } else {
                    Toast.makeText(this, "Error: ID ya existe", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error al insertar debe seleccionar especialidad: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}