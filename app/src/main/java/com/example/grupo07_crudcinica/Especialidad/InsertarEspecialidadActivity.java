package com.example.grupo07_crudcinica.Especialidad;
import com.example.grupo07_crudcinica.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;

public class InsertarEspecialidadActivity extends AppCompatActivity {

    EditText editNombreEspecialidad;
    Button btnGuardar;
    ClinicaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_especialidad);

        editNombreEspecialidad = findViewById(R.id.editNombreEspecialidad);
        btnGuardar = findViewById(R.id.btnGuardarEspecialidad);
        dbHelper = new ClinicaDbHelper(this);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombreEspecialidad.getText().toString().trim();

                if (!nombre.isEmpty()) {
                    long resultado = dbHelper.insertarEspecialidad(nombre);
                    if (resultado != -1) {
                        Toast.makeText(InsertarEspecialidadActivity.this, "Especialidad guardada correctamente", Toast.LENGTH_SHORT).show();
                        editNombreEspecialidad.setText("");
                    } else {
                        Toast.makeText(InsertarEspecialidadActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InsertarEspecialidadActivity.this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}