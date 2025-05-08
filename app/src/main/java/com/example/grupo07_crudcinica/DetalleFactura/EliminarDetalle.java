package com.example.grupo07_crudcinica.DetalleFactura;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import com.example.grupo07_crudcinica.R;

public class EliminarDetalle extends AppCompatActivity {

    EditText edtIdDetalleEliminar;
    Button btnEliminarDetalle;
    private ClinicaDbHelper dbHelper; // Tu clase que maneja la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_detalle);

        // Vinculación con vistas
        edtIdDetalleEliminar = findViewById(R.id.edtIdDetalleEliminar);
        btnEliminarDetalle = findViewById(R.id.btnEliminarDetalle);

        // Inicializar el helper de base de datos
        dbHelper = new ClinicaDbHelper(this);

        // Acción del botón para eliminar
        btnEliminarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idDetalle = edtIdDetalleEliminar.getText().toString().trim();

                if (idDetalle.isEmpty()) {
                    Toast.makeText(EliminarDetalle.this, "Por favor, ingresa el ID del detalle a eliminar", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamada al método de eliminación
                int resultado = dbHelper.eliminarDetalleFactura(idDetalle);
                if (resultado > 0) {
                    Toast.makeText(EliminarDetalle.this, "Detalle eliminado correctamente", Toast.LENGTH_SHORT).show();
                    finish(); // Opcional: Cerrar la pantalla después de eliminar
                } else {
                    Toast.makeText(EliminarDetalle.this, "Error al eliminar el detalle", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}