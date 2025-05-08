package com.example.grupo07_crudcinica.Medicamento;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;

public class InsertarMedicamento extends AppCompatActivity {

    private EditText edtNombre, edtDescripcion, edtPrecio, edtStock, edtFechaVencimiento;
    private Button btnGuardar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_medicamento);

        // Inicializar DBHelper
        dbHelper = new ClinicaDbHelper(this);

        // Vincular vistas
        edtNombre = findViewById(R.id.edtNombre);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtStock = findViewById(R.id.edtStock);
        edtFechaVencimiento = findViewById(R.id.edtFechaVencimiento);
        btnGuardar = findViewById(R.id.btnGuardarMedicamento);

        // Configurar el campo de fecha para mostrar un DatePicker al hacer clic
        edtFechaVencimiento.setOnClickListener(v -> mostrarDatePicker());

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener(v -> guardarMedicamento());
    }

    private void mostrarDatePicker() {
        // Implementación del DatePickerDialog
        final java.util.Calendar calendario = java.util.Calendar.getInstance();
        int año = calendario.get(java.util.Calendar.YEAR);
        int mes = calendario.get(java.util.Calendar.MONTH);
        int dia = calendario.get(java.util.Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Formatear la fecha seleccionada como dd/MM/yyyy
                    String fechaSeleccionada = String.format(Locale.getDefault(),
                            "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                    edtFechaVencimiento.setText(fechaSeleccionada);
                },
                año, mes, dia);

        datePickerDialog.show();
    }

    private void guardarMedicamento() {
        // Validar campos obligatorios
        if (edtNombre.getText().toString().trim().isEmpty()) {
            edtNombre.setError("Ingrese el nombre del medicamento");
            return;
        }

        if (edtPrecio.getText().toString().trim().isEmpty()) {
            edtPrecio.setError("Ingrese el precio");
            return;
        }

        if (edtFechaVencimiento.getText().toString().trim().isEmpty()) {
            edtFechaVencimiento.setError("Seleccione la fecha de vencimiento");
            return;
        }

        try {
            // Obtener valores de los campos
            String nombre = edtNombre.getText().toString().trim();
            String descripcion = edtDescripcion.getText().toString().trim();
            double precio = Double.parseDouble(edtPrecio.getText().toString().trim());
            int stock = edtStock.getText().toString().isEmpty() ? 0 :
                    Integer.parseInt(edtStock.getText().toString().trim());

            // Convertir fecha de dd/MM/yyyy a yyyy-MM-dd para la base de datos
            String fechaVencimiento = convertirFormatoFecha(edtFechaVencimiento.getText().toString().trim());

            // Insertar en la base de datos
            long id = dbHelper.insertarMedicamento(nombre, fechaVencimiento, precio);

            if (id != -1) {
                Toast.makeText(this, "Medicamento guardado correctamente", Toast.LENGTH_SHORT).show();
                limpiarCampos();
            } else {
                Toast.makeText(this, "Error al guardar el medicamento", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato de número inválido", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ParseException e) {
            Toast.makeText(this, "Formato de fecha inválido (use dd/mm/aaaa)", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private String convertirFormatoFecha(String fechaOriginal) throws ParseException {
        // Convertir de dd/MM/yyyy a yyyy-MM-dd
        SimpleDateFormat formatoOriginal = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat formatoDestino = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date fecha = formatoOriginal.parse(fechaOriginal);
        return formatoDestino.format(fecha);
    }

    private void limpiarCampos() {
        edtNombre.setText("");
        edtDescripcion.setText("");
        edtPrecio.setText("");
        edtStock.setText("");
        edtFechaVencimiento.setText("");
        edtNombre.requestFocus();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}