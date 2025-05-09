package com.example.grupo07_crudcinica.Factura;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.List;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Locale;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.List;

public class ConsultarFacturaActivity extends AppCompatActivity {

    Spinner spinnerFacturaId;
    TextView txtResultado;
    ClinicaDbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_factura);

        spinnerFacturaId = findViewById(R.id.spinnerIdFactura);
        txtResultado = findViewById(R.id.txtResultadoFactura);
        dbHelper = new ClinicaDbHelper(this);

        List<String> ids = dbHelper.consultarIds("FACTURA", "ID_FACTURA");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFacturaId.setAdapter(adapter);

        findViewById(R.id.btnBuscarFactura).setOnClickListener(view -> {
            try {
                String id = spinnerFacturaId.getSelectedItem().toString();
                Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM FACTURA WHERE ID_FACTURA = ?", new String[]{id});
                if (cursor.moveToFirst()) {
                    String resultado = "ID Factura: " + id +
                            "\nID Consulta: " + cursor.getString(cursor.getColumnIndexOrThrow("ID_CONSULTA")) +
                            "\nFecha: " + cursor.getString(cursor.getColumnIndexOrThrow("FECHA_FACTURA"));
                    txtResultado.setText(resultado);
                } else {
                    txtResultado.setText("Factura no encontrada.");
                }
                cursor.close();
            } catch (Exception e) {
                Toast.makeText(this, "Error no hay facturas registradas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}