package com.example.grupo07_crudcinica.DetalleFactura;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

import com.example.grupo07_crudcinica.R;

public class ConsultarDetalle extends AppCompatActivity {

    private ListView listViewDetalles;
    private ClinicaDbHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listaDetalles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_detalle);

        listViewDetalles = findViewById(R.id.listViewDetalles);
        dbHelper = new ClinicaDbHelper(this);
        listaDetalles = new ArrayList<>();

        cargarDetalles();
    }

    private void cargarDetalles() {
        // Usamos el método actualizado para obtener los detalles con el medicamento
        Cursor cursor = dbHelper.obtenerDetallesConMedicamento();

        if (cursor != null && cursor.moveToFirst()) {
            // Ahora que sabemos que el cursor tiene datos, procesamos cada fila
            do {
                String idDetalle = cursor.getString(cursor.getColumnIndexOrThrow("ID_DETALLE"));
                String idFactura = cursor.getString(cursor.getColumnIndexOrThrow("ID_FACTURA"));
                float monto = cursor.getFloat(cursor.getColumnIndexOrThrow("MONTO_DETALLE"));
                String formaPago = cursor.getString(cursor.getColumnIndexOrThrow("FORMA_DE_PAGO"));
                String nombreMedicamento = cursor.getString(cursor.getColumnIndexOrThrow("NOMBRE_MEDICAMENTO"));

                // Construimos el string para mostrar
                String detalle = "Detalle ID: " + idDetalle +
                        "\nFactura ID: " + idFactura +
                        "\nMonto: " + monto +
                        "\nForma de Pago: " + formaPago +
                        "\nMedicamento: " + nombreMedicamento;

                listaDetalles.add(detalle);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // Si el cursor está vacío, mostramos un mensaje adecuado o manejamos el caso
           //Log.e("ConsultarDetalle", "No se encontraron detalles con medicamentos.");
        }

        // Establecer el adaptador para el ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDetalles);
        listViewDetalles.setAdapter(adapter);
    }
}