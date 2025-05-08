package com.example.grupo07_crudcinica.Doctor;

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

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;

public class EliminarDoctorActivity extends AppCompatActivity {

    Spinner spinnerIds;
    TextView tvNombre, tvApellido;
    Button btnEliminar;
    ClinicaDbHelper dbHelper;
    ArrayList<String> listaIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_doctor);

        spinnerIds = findViewById(R.id.spinnerIdEliminar);
        tvNombre = findViewById(R.id.textViewNombreEliminar);
        tvApellido = findViewById(R.id.textViewApellidoEliminar);
        btnEliminar = findViewById(R.id.btnEliminarDoctor);
        dbHelper = new ClinicaDbHelper(this);

        cargarIDs();

        spinnerIds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String idSeleccionado = listaIds.get(position);
                Cursor cursor = dbHelper.obtenerDoctorPorId(idSeleccionado);
                if (cursor.moveToFirst()) {
                    tvNombre.setText(cursor.getString(1));
                    tvApellido.setText(cursor.getString(2));
                }
                cursor.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnEliminar.setOnClickListener(v -> {
            String id = spinnerIds.getSelectedItem().toString();
            if (dbHelper.eliminarDoctor(id)) {
                Toast.makeText(this, "Doctor eliminado", Toast.LENGTH_SHORT).show();
                cargarIDs();
            } else {
                Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarIDs() {
        listaIds.clear();
        Cursor cursor = dbHelper.consultarDoctores();
        while (cursor.moveToNext()) {
            listaIds.add(cursor.getString(0));
        }
        cursor.close();
        spinnerIds.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listaIds));
    }
}