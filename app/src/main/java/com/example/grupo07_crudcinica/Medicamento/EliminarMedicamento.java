package com.example.grupo07_crudcinica.Medicamento;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import android.database.Cursor;

public class EliminarMedicamento extends AppCompatActivity {

    private EditText edtIdEliminar;
    private Button btnEliminar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_medicamento);



        // Inicializar DBHelper
        dbHelper = new ClinicaDbHelper(this);

        // Vincular vistas
        edtIdEliminar = findViewById(R.id.edtIdEliminar);
        btnEliminar = findViewById(R.id.btnEliminar);

        // Configurar listener del botón
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarMedicamento();
            }
        });
    }

    private void eliminarMedicamento() {
        // Obtener y validar ID ingresado
        String id = edtIdEliminar.getText().toString().trim();
        if (id.isEmpty()) {
            edtIdEliminar.setError("Ingrese un ID válido");
            return;
        }

        // Verificar si el medicamento existe antes de eliminar
        if (!existeMedicamento(id)) {
            Toast.makeText(this, "No existe un medicamento con ese ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ejecutar eliminación
        int filasAfectadas = dbHelper.eliminarMedicamento(id);

        if (filasAfectadas > 0) {
            Toast.makeText(this, "Medicamento eliminado correctamente", Toast.LENGTH_SHORT).show();
            edtIdEliminar.setText("");
        } else {
            Toast.makeText(this, "Error al eliminar el medicamento", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean existeMedicamento(String id) {
        // Verificar si el medicamento existe en la base de datos
        Cursor cursor = dbHelper.obtenerMedicamentoPorId(id);
        boolean existe = cursor != null && cursor.getCount() > 0;
        if (cursor != null) {
            cursor.close();
        }
        return existe;
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}