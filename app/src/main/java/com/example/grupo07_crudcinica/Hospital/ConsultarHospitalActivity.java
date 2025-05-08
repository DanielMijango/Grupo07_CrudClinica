package com.example.grupo07_crudcinica.Hospital;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo07_crudcinica.ClinicaDbHelper;
import com.example.grupo07_crudcinica.R;
import android.database.Cursor;
import android.view.View;
import android.widget.*;


public class ConsultarHospitalActivity extends AppCompatActivity {

    Spinner spinner;
    TextView tvResultado;
    ClinicaDbHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_hospital);

        spinner = findViewById(R.id.spinnerIdHospital);
        tvResultado = findViewById(R.id.tvResultadoHospital);
        dbHelper = new ClinicaDbHelper(this);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dbHelper.obtenerIdsHospitales());
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int hospitalId = (int) parent.getItemAtPosition(position);
                Cursor cursor = dbHelper.consultarHospitalPorId(hospitalId);
                if (cursor.moveToFirst()) {
                    String info = "ID: " + cursor.getInt(0) +
                            "\nNombre: " + cursor.getString(1) +
                            "\nDirección: " + cursor.getString(2) +
                            "\nTeléfono: " + cursor.getString(3);
                    tvResultado.setText(info);
                }
                cursor.close();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}