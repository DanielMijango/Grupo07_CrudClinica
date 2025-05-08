package com.example.grupo07_crudcinica.Medicamento;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

public class ConsultarMedicamento extends AppCompatActivity {

    private EditText edtBuscarMedicamento;
    private Button btnBuscar;
    private TextView tvResultado;
    private ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_medicamento);

        // Inicializar DBHelper
        dbHelper = new ClinicaDbHelper(this);

        // Vincular vistas
        edtBuscarMedicamento = findViewById(R.id.edtBuscarMedicamento);
        btnBuscar = findViewById(R.id.btnBuscar);
        tvResultado = findViewById(R.id.tvResultado);

        // Configurar listener del botón
        btnBuscar.setOnClickListener(v -> buscarMedicamento());
    }

    private void buscarMedicamento() {
        String busqueda = edtBuscarMedicamento.getText().toString().trim();

        if (busqueda.isEmpty()) {
            Toast.makeText(this, "Ingrese un nombre o ID para buscar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Primero intentar buscar por ID (directamente como String)
        Cursor cursor = dbHelper.obtenerMedicamentoPorId(busqueda);

        if (cursor != null && cursor.moveToFirst()) {
            mostrarResultado(cursor);
            cursor.close();
        } else {
            // Si no se encontró por ID, buscar por nombre
            cursor = dbHelper.buscarMedicamentosPorNombre(busqueda);

            if (cursor != null && cursor.moveToFirst()) {
                mostrarResultado(cursor);
                cursor.close();
            } else {
                tvResultado.setText("No se encontraron medicamentos con: " + busqueda);
                tvResultado.setVisibility(View.VISIBLE);
            }
        }
    }

    private void mostrarResultado(Cursor cursor) {
        // Obtener datos del cursor
        String id = cursor.getString(cursor.getColumnIndexOrThrow("ID_MEDICAMENTO"));
        String nombre = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_MEDICAMENTO"));
        String fecha = cursor.getString(cursor.getColumnIndexOrThrow("FECHA_VENCIMIENTO"));
        double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIO_MEDICAMENTO"));

        // Formatear resultado
        String resultado = String.format(
                "ID: %s\nNombre: %s\nFecha Vencimiento: %s\nPrecio: $%.2f",
                id, nombre, fecha, precio
        );

        // Mostrar resultado
        tvResultado.setText(resultado);
        tvResultado.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}