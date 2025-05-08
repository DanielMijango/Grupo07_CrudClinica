package com.example.grupo07_crudcinica.DetalleFactura;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertarDetalle extends AppCompatActivity {

    private EditText edtIdFactura, edtMontoDetalle, edtFormaPago;
    private Spinner spinnerMedicamentos;
    private List<String> listaMedicamentos; // Puedes usar objetos reales si deseas mostrar nombre y obtener ID
    Map<String, String> medicamentoMap;
    private Button btnGuardarDetalle;

    private ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_detalle);

        dbHelper = new ClinicaDbHelper(this);

        // Referenciar vistas
        edtIdFactura = findViewById(R.id.edtIdFactura);
        edtMontoDetalle = findViewById(R.id.edtMontoDetalle);
        edtFormaPago = findViewById(R.id.edtFormaPago);
        btnGuardarDetalle = findViewById(R.id.btnAgregarDetalle);
        spinnerMedicamentos = findViewById(R.id.spinnerMedicamentos);
        cargarListaMedicamentos();

        // Configurar el botón de guardar
        btnGuardarDetalle.setOnClickListener(v -> guardarDetalleFactura());

    }
    private void cargarListaMedicamentos() {
        listaMedicamentos = new ArrayList<>();
        medicamentoMap = new HashMap<>();  // Inicializamos el mapa

        // Obtener cursor desde el helper
        Cursor cursor = dbHelper.obtenerTodosLosMedicamentos();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Suponiendo que el nombre del medicamento está en la columna "NOMBRE"
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_MEDICAMENTO"));
                String idMedicamento = cursor.getString(cursor.getColumnIndexOrThrow("ID_MEDICAMENTO")); // Asegúrate de tener el ID del medicamento

                // Agregamos el nombre y el ID al mapa
                medicamentoMap.put(nombre, idMedicamento);

                // Agregar solo el nombre al spinner
                listaMedicamentos.add(nombre);
            } while (cursor.moveToNext());
            cursor.close();
        }

        // Configurar el adaptador para el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMedicamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedicamentos.setAdapter(adapter);
    }
    private void guardarDetalleFactura() {
        String idFactura = edtIdFactura.getText().toString().trim();
        String montoStr = edtMontoDetalle.getText().toString().trim();
        String formaPago = edtFormaPago.getText().toString().trim();
        String nombreMedicamento = spinnerMedicamentos.getSelectedItem().toString();


        // Validaciones
        if (idFactura.isEmpty()) {
            edtIdFactura.setError("Ingrese el ID de la factura");
            return;
        }

        if (montoStr.isEmpty()) {
            edtMontoDetalle.setError("Ingrese el monto del detalle");
            return;
        }

        if (formaPago.isEmpty()) {
            edtFormaPago.setError("Ingrese la forma de pago");
            return;
        }

        try {
            double monto = Double.parseDouble(montoStr);
            String idMedicamento = medicamentoMap.get(nombreMedicamento); // Asegúrate de tener este mapa

            // Insertar el detalle, pero el ID generado es un String con prefijo "DET"
            String idDetalle = dbHelper.insertarDetalleFactura(idFactura, monto, formaPago);

            if (idDetalle != null) {
                // Insertar la relación medicamento-detalle
                boolean resultadoRelacion = dbHelper.insertarMedicamentoDetalle(idDetalle, idMedicamento);

                if (resultadoRelacion) {
                    Toast.makeText(this, "Detalle y relación guardados exitosamente", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                } else {
                    Toast.makeText(this, "Error al guardar la relación medicamento-detalle", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error al guardar el detalle", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            edtMontoDetalle.setError("Monto inválido");
            e.printStackTrace();
        }
    }


    private void limpiarCampos() {
        edtIdFactura.setText("");
        edtMontoDetalle.setText("");
        edtFormaPago.setText("");
        edtIdFactura.requestFocus();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}