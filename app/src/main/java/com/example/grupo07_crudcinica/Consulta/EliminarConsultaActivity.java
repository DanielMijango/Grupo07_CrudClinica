package com.example.grupo07_crudcinica.Consulta;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.grupo07_crudcinica.databinding.ActivityEliminarConsultaBinding;

import com.example.grupo07_crudcinica.R;

public class EliminarConsultaActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEliminarConsultaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar_consulta);


    }


}