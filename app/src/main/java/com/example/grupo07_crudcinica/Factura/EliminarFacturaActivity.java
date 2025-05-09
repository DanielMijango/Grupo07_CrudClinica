package com.example.grupo07_crudcinica.Factura;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo07_crudcinica.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

public class EliminarFacturaActivity extends AppCompatActivity {

    private EditText edtFacturaIDEliminar;
    private Button btnEliminarFactura;
    private TextView txtEliminarResultado;
    private ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_factura);

        // Inicialización de la base de datos
        dbHelper = new ClinicaDbHelper(this);

        // Referencias a los elementos de la interfaz
        edtFacturaIDEliminar = findViewById(R.id.edtFacturaIDEliminar);
        btnEliminarFactura = findViewById(R.id.btnEliminarFactura);
        txtEliminarResultado = findViewById(R.id.txtEliminarResultado);

        // Configuramos el botón para eliminar la factura
        btnEliminarFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idFactura = edtFacturaIDEliminar.getText().toString().trim();

                // Verificar que el ID no esté vacío
                if (idFactura.isEmpty()) {
                    Toast.makeText(EliminarFacturaActivity.this, "Por favor ingrese el ID de la factura", Toast.LENGTH_SHORT).show();
                } else {
                    // Llamar al método para eliminar la factura y sus detalles
                    boolean eliminado = dbHelper.eliminarFacturaYDetalles(idFactura);

                    if (eliminado) {
                        txtEliminarResultado.setText("Factura y detalles eliminados correctamente.");
                    } else {
                        txtEliminarResultado.setText("No se encontró la factura o no se pudo eliminar.");
                    }
                }
            }
        });
    }
}