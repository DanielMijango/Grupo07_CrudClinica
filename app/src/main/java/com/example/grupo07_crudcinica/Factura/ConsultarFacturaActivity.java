package com.example.grupo07_crudcinica.Factura;

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

public class ConsultarFacturaActivity extends AppCompatActivity {

    private EditText edtBuscarFecha;
    private Button btnBuscarFacturaPorFecha;
    private ListView listaDetallesFactura;
    private ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_consultar_factura);

        dbHelper = new ClinicaDbHelper(this);

        edtBuscarFecha = findViewById(R.id.edtBuscarFecha);
        btnBuscarFacturaPorFecha = findViewById(R.id.btnBuscarFacturaPorFecha);
        listaDetallesFactura = findViewById(R.id.listaDetallesFactura); // Debes agregar esto al XML

        edtBuscarFecha.setOnClickListener(v -> mostrarDatePicker());

        btnBuscarFacturaPorFecha.setOnClickListener(v -> {
            String fecha = edtBuscarFecha.getText().toString().trim();

            if (fecha.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese una fecha", Toast.LENGTH_SHORT).show();
            } else {
                List<String> resultados = dbHelper.consultarFacturasYDetallesPorFecha(fecha);

                if (resultados.isEmpty()) {
                    Toast.makeText(this, "No se encontraron facturas para esta fecha.", Toast.LENGTH_SHORT).show();
                    listaDetallesFactura.setAdapter(null);
                } else {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultados);
                    listaDetallesFactura.setAdapter(adapter);
                }
            }
        });
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    String fechaSeleccionada = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    edtBuscarFecha.setText(fechaSeleccionada);
                },
                año, mes, dia);

        datePickerDialog.show();
    }
}