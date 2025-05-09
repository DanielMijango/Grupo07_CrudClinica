package com.example.grupo07_crudcinica.Consulta;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ConsultarConsultaActivity extends AppCompatActivity {

    Spinner spinnerConsultaId;
    TextView txtResultado;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_consulta);

        spinnerConsultaId = findViewById(R.id.spinnerIdConsulta);
        txtResultado = findViewById(R.id.txtResultadoConsulta);
        dbHelper = new ClinicaDbHelper(this);

        List<String> ids = dbHelper.obtenerIdsConsultas();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConsultaId.setAdapter(adapter);

        findViewById(R.id.btnBuscarConsulta).setOnClickListener(view -> {
            String id = spinnerConsultaId.getSelectedItem().toString();
            Cursor cursor = dbHelper.obtenerConsultaPorId(id);
            if (cursor.moveToFirst()) {
                String resultado = "ID Consulta: " + id +
                        "\nID Doctor: " + cursor.getString(cursor.getColumnIndexOrThrow("ID_DOCTOR")) +
                        "\nID Paciente: " + cursor.getString(cursor.getColumnIndexOrThrow("ID_PACIENTE")) +
                        "\nFecha: " + cursor.getString(cursor.getColumnIndexOrThrow("FECHA_CONSULTA")) +
                        "\nEmergencia: " + cursor.getString(cursor.getColumnIndexOrThrow("EMERGENCIA")) +
                        "\nCuota: $" + cursor.getDouble(cursor.getColumnIndexOrThrow("CUOTA")) +
                        "\nDiagnóstico: " + cursor.getString(cursor.getColumnIndexOrThrow("DIAGNOSTICO"));
                txtResultado.setText(resultado);
            } else {
                txtResultado.setText("Consulta no encontrada.");
            }
            cursor.close();
        });
    }
}
