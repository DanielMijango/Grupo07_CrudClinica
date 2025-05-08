package com.example.grupo07_crudcinica.DetalleFactura;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

import java.util.ArrayList;
import java.util.List;

public class ActualizarDetalle extends AppCompatActivity {

    EditText edtIdDetalle, edtMontoDetalle, edtFormaPago;
    Spinner spinnerMedicamentos;
    Button btnActualizarDetalle;
    private ClinicaDbHelper dbHelper;

    // Listas paralelas para mostrar nombres y usar IDs
    private List<String> listaIdsMedicamentos = new ArrayList<>();
    private List<String> listaNombresMedicamentos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_detalle);

        edtIdDetalle = findViewById(R.id.edtIdDetalle);
        edtMontoDetalle = findViewById(R.id.edtMontoDetalle);
        edtFormaPago = findViewById(R.id.edtFormaPago);
        spinnerMedicamentos = findViewById(R.id.spinnerMedicamentos);
        btnActualizarDetalle = findViewById(R.id.btnActualizarDetalle);

        dbHelper = new ClinicaDbHelper(this);

        cargarMedicamentosSpinner();

        btnActualizarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idDetalle = edtIdDetalle.getText().toString().trim();
                String montoStr = edtMontoDetalle.getText().toString().trim();
                String formaPago = edtFormaPago.getText().toString().trim();

                if (idDetalle.isEmpty() || montoStr.isEmpty() || formaPago.isEmpty()) {
                    Toast.makeText(ActualizarDetalle.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                double montoDetalle;
                try {
                    montoDetalle = Double.parseDouble(montoStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(ActualizarDetalle.this, "Monto inválido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Obtener el ID real del medicamento seleccionado (usando la misma posición del spinner)
                int posicionSeleccionada = spinnerMedicamentos.getSelectedItemPosition();
                if (posicionSeleccionada < 0 || posicionSeleccionada >= listaIdsMedicamentos.size()) {
                    Toast.makeText(ActualizarDetalle.this, "Error al seleccionar el medicamento", Toast.LENGTH_SHORT).show();
                    return;
                }
                String idMedicamentoSeleccionado = listaIdsMedicamentos.get(posicionSeleccionada);

                List<String> nuevosMedicamentos = new ArrayList<>();
                nuevosMedicamentos.add(idMedicamentoSeleccionado);  // ID real, no nombre

                int resultado = dbHelper.actualizarDetalleFactura(idDetalle, montoDetalle, formaPago, nuevosMedicamentos);
                if (resultado > 0) {
                    Toast.makeText(ActualizarDetalle.this, "Detalle actualizado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ActualizarDetalle.this, "Error al actualizar el detalle", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void cargarMedicamentosSpinner() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_MEDICAMENTO, NOMBRE_MEDICAMENTO FROM MEDICAMENTO", null);

        listaIdsMedicamentos.clear();
        listaNombresMedicamentos.clear();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex("ID_MEDICAMENTO"));
                String nombre = cursor.getString(cursor.getColumnIndex("NOMBRE_MEDICAMENTO"));
                listaIdsMedicamentos.add(id);
                listaNombresMedicamentos.add(nombre);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaNombresMedicamentos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedicamentos.setAdapter(adapter);
    }
}
