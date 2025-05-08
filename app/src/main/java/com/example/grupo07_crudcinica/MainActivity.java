package com.example.grupo07_crudcinica;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

import com.example.grupo07_crudcinica.databinding.ActivityHospitalizacionMenuBinding;

public class MainActivity extends AppCompatActivity {
    Button btnClinica, btnEspecialidad, btnDoctor, btnPaciente, btnConsulta, btnFactura, btnMedicamento, btnSeguro, btnHospital, btnDetalleFactura, btnHospitalizacion, btnAseguradora, btnTratamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClinica = findViewById(R.id.btnClinica);
        btnEspecialidad = findViewById(R.id.btnEspecialidad);
        btnDoctor = findViewById(R.id.btnDoctor);
        btnPaciente = findViewById(R.id.btnPaciente);
        btnConsulta = findViewById(R.id.btnConsulta);
        btnFactura = findViewById(R.id.btnFactura);
        btnMedicamento = findViewById(R.id.btnMedicamento);
        btnSeguro = findViewById(R.id.btnSeguro);
        btnHospital = findViewById(R.id.btnHospital);
        btnDetalleFactura = findViewById(R.id.btnDetalleFactura);
        btnHospitalizacion = findViewById(R.id.btnHospitalizacion);
        btnAseguradora = findViewById(R.id.btnAseguradora);
        btnTratamiento = findViewById(R.id.btnTratamiento);

        btnClinica.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Clinica.ClinicaMenuActivity.class)));
        btnMedicamento.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Medicamento.MedicamentoMenuActivity.class)));
        btnDetalleFactura.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.DetalleFactura.DetalleFacturaMenuActivity.class)));
        btnEspecialidad.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Especialidad.EspecialidadMenuActivity.class)));
        btnDoctor.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Doctor.DoctorMenuActivity.class)));
        btnPaciente.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Paciente.MenuPacienteActivity.class)));
        btnHospitalizacion.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.hospitalizacion.hospitalizacionMenuActivity.class)));
        btnAseguradora = findViewById(R.id.btnAseguradora);
        btnAseguradora.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Aseguradora.AseguradoraMenuActivity.class)));
        btnHospital.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Hospital.MenuHospitalActivity.class)));
        btnTratamiento = findViewById(R.id.btnTratamiento);btnTratamiento.setOnClickListener(v -> startActivity(new Intent(this, com.example.grupo07_crudcinica.Tratamiento.MenuTratamientoActivity.class)));
        // Los demás aún no están implementados, los agregaremos después
    }
}