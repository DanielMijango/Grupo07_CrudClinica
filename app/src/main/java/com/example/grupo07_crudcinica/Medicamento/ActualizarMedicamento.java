package com.example.grupo07_crudcinica.Medicamento;

import android.os.Bundle;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import com.example.grupo07_crudcinica.R;

public class ActualizarMedicamento extends AppCompatActivity {

    private EditText edtIdMedicamento, edtNuevoNombre, edtNuevoPrecio;
    private Button btnActualizar;
    private ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_medicamento);

        // Inicializar DBHelper
        dbHelper = new ClinicaDbHelper(this);

        // Vincular vistas
        edtIdMedicamento = findViewById(R.id.edtIdMedicamento);
        edtNuevoNombre = findViewById(R.id.edtNuevoNombre);
        edtNuevoPrecio = findViewById(R.id.edtNuevoPrecio);
        btnActualizar = findViewById(R.id.btnActualizar);

        // Configurar listener del botón
        btnActualizar.setOnClickListener(v -> actualizarMedicamento());
    }

    private void actualizarMedicamento() {
        // Obtener valores de los campos
        String id = edtIdMedicamento.getText().toString().trim();
        String nuevoNombre = edtNuevoNombre.getText().toString().trim();
        String nuevoPrecioStr = edtNuevoPrecio.getText().toString().trim();

        // Validaciones básicas
        if (id.isEmpty()) {
            edtIdMedicamento.setError("Ingrese el ID del medicamento");
            return;
        }

        if (nuevoNombre.isEmpty() && nuevoPrecioStr.isEmpty()) {
            Toast.makeText(this, "Ingrese al menos un campo a actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el medicamento existe
        if (!medicamentoExiste(id)) {
            Toast.makeText(this, "No existe un medicamento con ese ID", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Actualizar solo los campos que tienen datos
            boolean nombreActualizado = false;
            boolean precioActualizado = false;

            if (!nuevoNombre.isEmpty()) {
                nombreActualizado = actualizarCampo(id, "NOMBRE_MEDICAMENTO", nuevoNombre);
            }

            if (!nuevoPrecioStr.isEmpty()) {
                // Validar que el precio sea numérico
                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                precioActualizado = actualizarCampo(id, "PRECIO_MEDICAMENTO", nuevoPrecioStr);
            }

            // Mostrar resultado
            if (nombreActualizado || precioActualizado) {
                Toast.makeText(this, "Medicamento actualizado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "No se realizaron cambios", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            // Solo aplica al precio (el ID ya no necesita ser numérico)
            Toast.makeText(this, "El precio debe ser un valor numérico válido", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean medicamentoExiste(String id) {
        Cursor cursor = dbHelper.obtenerMedicamentoPorId(id);
        boolean existe = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return existe;
    }

    private boolean actualizarCampo(String id, String campo, String valor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(campo, valor);

        int filasAfectadas = db.update("MEDICAMENTO", values,
                "ID_MEDICAMENTO = ?",
                new String[]{String.valueOf(id)});
        db.close();

        return filasAfectadas > 0;
    }

    private void limpiarCampos() {
        edtIdMedicamento.setText("");
        edtNuevoNombre.setText("");
        edtNuevoPrecio.setText("");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}