package com.example.grupo07_crudcinica.Clinica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.grupo07_crudcinica.ClinicaDbHelper;

import java.util.ArrayList;
import java.util.List;

public class ClinicaDAO {

    private ClinicaDbHelper dbHelper;

    public ClinicaDAO(Context context) {
        dbHelper = new ClinicaDbHelper(context);
    }

    // Obtener lista de especialidades
    public List<Especialidad> obtenerEspecialidades() {
        List<Especialidad> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT ID_ESPECIALIDAD, NOMBRE_ESPECIALIDAD FROM ESPECIALIDAD", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(0);
                    String nombre = cursor.getString(1);
                    lista.add(new Especialidad(id, nombre));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }

    // Obtener lista de distritos
    public List<Distrito> obtenerDistritos() {
        List<Distrito> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT id_distrito, nombre FROM distrito", null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String nombre = cursor.getString(1);
                    lista.add(new Distrito(id, nombre));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }

    // Insertar clínica
    public boolean insertarClinica(int id, String nombre, String direccion, int idDistrito) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("idClinica", id);
        valores.put("nombre", nombre);
        valores.put("direccion", direccion);
        valores.put("id_distrito", idDistrito);

        long resultado = db.insert("Clinica", null, valores);
        db.close();
        return resultado != -1;
    }

    // Insertar relación clínica-especialidad

    public boolean insertarClinicaEspecialidad(int idClinica, String idEspecialidad) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean resultado = false;

        try {
            // Verificar si ya existe la relación
            Cursor cursor = db.rawQuery(
                    "SELECT 1 FROM Clinica_Especialidad WHERE idClinica = ? AND ID_ESPECIALIDAD = ?",
                    new String[]{String.valueOf(idClinica), idEspecialidad}
            );

            if (cursor.moveToFirst()) {
                Log.w("INSERT_CLINICA_ESP", "Ya existe la relación: Clínica " + idClinica + " - Especialidad " + idEspecialidad);
                cursor.close();
                return false;
            }
            cursor.close();

            // Verificar existencia de idClinica
            Cursor cursorClinica = db.rawQuery(
                    "SELECT 1 FROM Clinica WHERE idClinica = ?",
                    new String[]{String.valueOf(idClinica)}
            );
            if (!cursorClinica.moveToFirst()) {
                Log.e("INSERT_CLINICA_ESP", "ID Clínica no encontrado: " + idClinica);
                cursorClinica.close();
                return false;
            }
            cursorClinica.close();

            // Verificar existencia de idEspecialidad
            Cursor cursorEspecialidad = db.rawQuery(
                    "SELECT 1 FROM ESPECIALIDAD WHERE ID_ESPECIALIDAD = ?",
                    new String[]{idEspecialidad}
            );
            if (!cursorEspecialidad.moveToFirst()) {
                Log.e("INSERT_CLINICA_ESP", "ID Especialidad no encontrado: " + idEspecialidad);
                cursorEspecialidad.close();
                return false;
            }
            cursorEspecialidad.close();

            // Insertar la relación
            ContentValues valores = new ContentValues();
            valores.put("idClinica", idClinica);
            valores.put("ID_ESPECIALIDAD", idEspecialidad); // ← importante: nombre de columna correcto

            long insertResult = db.insertOrThrow("Clinica_Especialidad", null, valores);
            resultado = insertResult != -1;
            Log.d("INSERT_CLINICA_ESP", "Inserción exitosa: Clínica " + idClinica + " - Especialidad " + idEspecialidad);

        } catch (Exception e) {
            Log.e("INSERT_CLINICA_ESP", "Error al insertar relación", e);
        } finally {
            db.close();
        }

        return resultado;
    }


    // Consultar clínica por ID
    public Cursor consultarClinicaPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Clinica WHERE idClinica = ?", new String[]{String.valueOf(id)});
    }

    // Actualizar clínica
    public boolean actualizarClinica(int id, String nuevoNombre, String nuevaDireccion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nuevoNombre);
        valores.put("direccion", nuevaDireccion);

        int filas = db.update("Clinica", valores, "idClinica = ?", new String[]{String.valueOf(id)});
        db.close();
        return filas > 0;
    }

    // Eliminar clínica
    public boolean eliminarClinica(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = db.delete("Clinica", "idClinica = ?", new String[]{String.valueOf(id)});
        db.close();
        return filas > 0;
    }
}
