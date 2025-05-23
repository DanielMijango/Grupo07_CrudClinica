package com.example.grupo07_crudcinica;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class ClinicaDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Clinica.db";
    private static final int DATABASE_VERSION = 2;
    private final Context context;

    public ClinicaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tablas en orden correcto
        db.execSQL("CREATE TABLE departamento (" +
                "id_departamento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL);");

        db.execSQL("CREATE TABLE municipio (" +
                "id_municipio INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "id_departamento INTEGER," +
                "FOREIGN KEY (id_departamento) REFERENCES departamento(id_departamento));");

        db.execSQL("CREATE TABLE distrito (" +
                "id_distrito INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "id_municipio INTEGER," +
                "FOREIGN KEY (id_municipio) REFERENCES municipio(id_municipio));");


        db.execSQL("CREATE TABLE Clinica (" +
                "idClinica INTEGER PRIMARY KEY," +
                "nombre TEXT," +
                "direccion TEXT," +
                "id_distrito INTEGER," +
                "FOREIGN KEY (id_distrito) REFERENCES distrito(id_distrito));");

        db.execSQL("CREATE TABLE ESPECIALIDAD (" +
                "ID_ESPECIALIDAD CHAR(4) PRIMARY KEY," +
                "NOMBRE_ESPECIALIDAD VARCHAR(50));");

        db.execSQL("CREATE TABLE DOCTOR (" +
                "ID_DOCTOR CHAR(4) PRIMARY KEY," +
                "NOMBRE_DOC VARCHAR(50)," +
                "APELLIDO_DOC VARCHAR(50)," +
                "ID_ESPECIALIDAD CHAR(4)," +
                "FOREIGN KEY (ID_ESPECIALIDAD) REFERENCES ESPECIALIDAD(ID_ESPECIALIDAD));");

        //falta corregir relacion a paciente

        db.execSQL("CREATE TABLE ASEGURADORA (" +
                "ID_ASEGURADORA CHAR(4) PRIMARY KEY," +
                "NOMBRE_ASEGURADORA VARCHAR(50) NOT NULL);");

        db.execSQL("CREATE TABLE PACIENTE (" +
                "ID_PACIENTE CHAR(4) PRIMARY KEY," +
                "ID_ASEGURADORA CHAR(4)," +
                "NOMBRE_PACIENTE VARCHAR(50)," +
                "APELLIDO_PACIENTE VARCHAR(50)," +
                "DUI_PACIENTE CHAR(12));");

        db.execSQL("CREATE TABLE Clinica_Especialidad (" +
                "idClinica INTEGER NOT NULL, " +
                "ID_ESPECIALIDAD TEXT NOT NULL, " + // <-- aquí
                "PRIMARY KEY (idClinica, ID_ESPECIALIDAD), " +
                "FOREIGN KEY (idClinica) REFERENCES Clinica(idClinica), " +
                "FOREIGN KEY (ID_ESPECIALIDAD) REFERENCES ESPECIALIDAD(ID_ESPECIALIDAD));");

        db.execSQL("CREATE TABLE MEDICAMENTO (" +
                "ID_MEDICAMENTO TEXT PRIMARY KEY," +
                "NOMBRE_MEDICAMENTO TEXT," +
                "FECHA_VENCIMIENTO TEXT," + // Usamos TEXT para fechas en SQLite
                "PRECIO_MEDICAMENTO REAL)");

        // Crear tabla RELATIONSHIP_14 (Medicamento-DetalleFactura)
        db.execSQL("CREATE TABLE MEDICAMENTO_DETALLE (" +
                "ID_DETALLE TEXT," +
                "ID_MEDICAMENTO TEXT," +
                "PRIMARY KEY (ID_DETALLE, ID_MEDICAMENTO)," +
                "FOREIGN KEY (ID_DETALLE) REFERENCES DETALLE_FACTURA(ID_DETALLE)," +
                "FOREIGN KEY (ID_MEDICAMENTO) REFERENCES MEDICAMENTO(ID_MEDICAMENTO))");

        db.execSQL("CREATE TABLE DETALLE_FACTURA (" +
                "ID_DETALLE TEXT PRIMARY KEY," +
                "ID_FACTURA TEXT," +
                "MONTO_DETALLE REAL," +
                "FORMA_DE_PAGO TEXT)");


        db.execSQL("CREATE TABLE TRATAMIENTO (" +
                "ID_TRATAMIENTO CHAR(4) PRIMARY KEY, " +
                "ID_CONSULTA CHAR(4), " +
                "FECHA_TRATAMIENTO TEXT, " +
                "DESCRIPCION VARCHAR(100));");

        db.execSQL("CREATE TABLE HOSPITAL (" +
                "id_hospital INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "direccion TEXT NOT NULL, " +
                "telefono TEXT)");

        db.execSQL("CREATE TABLE HOSPITALIZACION (" +
                "ID_HOSPITALIZACION CHAR(6) PRIMARY KEY, " +
                "ID_PACIENTE CHAR(4) NOT NULL, " +
                "FECHA_INGRESO TEXT NOT NULL, " +
                "FECHA_ALTA TEXT, " +
                "FECHA_SALIDA TEXT," +
                "FOREIGN KEY(ID_PACIENTE) REFERENCES PACIENTE(ID_PACIENTE));");

        db.execSQL("CREATE TABLE CONSULTA (" +
                "ID_CONSULTA CHAR(4) PRIMARY KEY, " +
                "ID_FACTURA CHAR(4), " +
                "ID_DOCTOR CHAR(4), " +
                "ID_PACIENTE CHAR(4), " +
                "FECHA_CONSULTA TEXT, " +
                "EMERGENCIA VARCHAR(100), " +
                "CUOTA REAL, " +
                "DIAGNOSTICO VARCHAR(100), " +
                "FOREIGN KEY (ID_DOCTOR) REFERENCES DOCTOR(ID_DOCTOR), " +
                "FOREIGN KEY (ID_PACIENTE) REFERENCES PACIENTE(ID_PACIENTE), " +
                "FOREIGN KEY (ID_FACTURA) REFERENCES FACTURA(ID_FACTURA));");


        db.execSQL("CREATE TABLE FACTURA (" +
                "ID_FACTURA CHAR(4) PRIMARY KEY," +
                "ID_CONSULTA CHAR(4)," +
                "FECHA_FACTURA DATE" +
                ");");

        cargarDatosDesdeJSON(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Clinica_Especialidad;");
        db.execSQL("DROP TABLE IF EXISTS Clinica;");
        db.execSQL("DROP TABLE IF EXISTS municipio;");
        db.execSQL("DROP TABLE IF EXISTS distrito;");
        db.execSQL("DROP TABLE IF EXISTS departamento;");
        db.execSQL("DROP TABLE IF EXISTS ESPECIALIDAD;");
        db.execSQL("DROP TABLE IF EXISTS DOCTOR;");
        db.execSQL("DROP TABLE IF EXISTS PACIENTE;");
        db.execSQL("DROP TABLE IF EXISTS MEDICAMENTO");
        db.execSQL("DROP TABLE IF EXISTS MEDICAMENTO_DETALLE");
        db.execSQL("DROP TABLE IF EXISTS DETALLE_FACTURA");
        db.execSQL("DROP TABLE IF EXISTS TRATAMIENTO;");
        db.execSQL("DROP TABLE IF EXISTS FACTURA;");
        db.execSQL("DROP TABLE IF EXISTS HOSPITALIZACION;");
        db.execSQL("DROP TABLE IF EXISTS HOSPITAL;");
        db.execSQL("DROP TABLE IF EXISTS CONSULTA;");
        db.execSQL("DROP TABLE IF EXISTS ASEGURADORA;");

        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    // ------------------------- CRUD ESPECIALIDAD -------------------------
    public Cursor obtenerEspecialidadPorId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM ESPECIALIDAD WHERE ID_ESPECIALIDAD = ?", new String[]{id});
    }

    public long insertarEspecialidad(String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_ESPECIALIDAD", generarId("ESP"));
        values.put("NOMBRE_ESPECIALIDAD", nombre);
        return db.insert("ESPECIALIDAD", null, values);
    }

    public Cursor consultarEspecialidades() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM ESPECIALIDAD", null);
    }

    public boolean actualizarEspecialidad(String id, String nuevoNombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE_ESPECIALIDAD", nuevoNombre);
        return db.update("ESPECIALIDAD", values, "ID_ESPECIALIDAD = ?", new String[]{id}) > 0;
    }

    public boolean eliminarEspecialidad(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ESPECIALIDAD", "ID_ESPECIALIDAD = ?", new String[]{id}) > 0;
    }

    public Cursor obtenerTodasEspecialidades() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT ID_ESPECIALIDAD, NOMBRE_ESPECIALIDAD FROM ESPECIALIDAD", null);
    }

    // ------------------------- CRUD DOCTOR -------------------------
    public Cursor obtenerDoctorPorId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM DOCTOR WHERE ID_DOCTOR = ?", new String[]{id});
    }

    public long insertarDoctor(String nombre, String apellido, String idEspecialidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_DOCTOR", generarId("DOC"));
        values.put("NOMBRE_DOC", nombre);
        values.put("APELLIDO_DOC", apellido);
        values.put("ID_ESPECIALIDAD", idEspecialidad);
        return db.insert("DOCTOR", null, values);
    }

    public Cursor consultarDoctores() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM DOCTOR", null);
    }

    public boolean actualizarDoctor(String id, String nuevoNombre, String nuevoApellido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE_DOC", nuevoNombre);
        values.put("APELLIDO_DOC", nuevoApellido);
        return db.update("DOCTOR", values, "ID_DOCTOR = ?", new String[]{id}) > 0;
    }

    public boolean eliminarDoctor(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("DOCTOR", "ID_DOCTOR = ?", new String[]{id}) > 0;
    }

    // ------------------------- CRUD PACIENTE -------------------------
    public Cursor obtenerPacientePorId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM PACIENTE WHERE ID_PACIENTE = ?", new String[]{id});
    }

    public long insertarPaciente(String nombre, String apellido, String dui, String idAseguradora) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_PACIENTE", generarId("PAC"));
        values.put("NOMBRE_PACIENTE", nombre);
        values.put("APELLIDO_PACIENTE", apellido);
        values.put("DUI_PACIENTE", dui);
        values.put("ID_ASEGURADORA", idAseguradora);
        return db.insert("PACIENTE", null, values);
    }

    public Cursor consultarPacientes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM PACIENTE", null);
    }

    public boolean actualizarPaciente(String id, String nombre, String apellido, String dui, String idAseguradora) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE_PACIENTE", nombre);
        values.put("APELLIDO_PACIENTE", apellido);
        values.put("DUI_PACIENTE", dui);
        values.put("ID_ASEGURADORA", idAseguradora);
        return db.update("PACIENTE", values, "ID_PACIENTE = ?", new String[]{id}) > 0;
    }

    public int eliminarPaciente(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("PACIENTE", "ID_PACIENTE = ?", new String[]{id});
    }

    // ------------------------- Utilidades -------------------------
    private String generarId(String prefijo) {
        return prefijo + String.format("%03d", (int) (Math.random() * 1000));
    }
    //-------------------------------------- CRUD FACTURA ------------------------------------


    public List<String> consultarIds(String tabla, String columna) {
        List<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + columna + " FROM " + tabla, null);
        if (cursor.moveToFirst()) {
            do {
                ids.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ids;
    }

    public boolean insertarFactura(String idConsulta, String fechaFactura) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction(); // Iniciar transacción

        try {
            String idFactura = generarId("FAC");

            ContentValues valuesFactura = new ContentValues();
            valuesFactura.put("ID_FACTURA", idFactura);
            valuesFactura.put("ID_CONSULTA", idConsulta);
            valuesFactura.put("FECHA_FACTURA", fechaFactura);

            long resultadoFactura = db.insert("FACTURA", null, valuesFactura);

            if (resultadoFactura == -1) {
                return false; // Inserción fallida
            }

            ContentValues valoresConsulta = new ContentValues();
            valoresConsulta.put("ID_FACTURA", idFactura);

            int filasAfectadas = db.update(
                    "CONSULTA",
                    valoresConsulta,
                    "ID_CONSULTA = ?",
                    new String[]{idConsulta}
            );

            if (filasAfectadas <= 0) {
                return false; // Actualización fallida
            }

            db.setTransactionSuccessful(); // Marca la transacción como exitosa
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Opcional: log para debugging
            return false;
        } finally {
            db.endTransaction(); // Finaliza la transacción (commit o rollback)
        }
    }

    public List<String> consultarFacturasConDetallesPorFecha(String fecha) {
        List<String> resultados = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Consulta todas las facturas con la fecha proporcionada
        String queryFactura = "SELECT ID_FACTURA, FECHA_FACTURA FROM FACTURA WHERE FECHA_FACTURA = ?";
        Cursor cursorFactura = db.rawQuery(queryFactura, new String[]{fecha});

        if (cursorFactura.moveToFirst()) {
            do {
                String idFactura = cursorFactura.getString(cursorFactura.getColumnIndex("ID_FACTURA"));
                String fechaFactura = cursorFactura.getString(cursorFactura.getColumnIndex("FECHA_FACTURA"));
                StringBuilder facturaInfo = new StringBuilder();
                facturaInfo.append("ID Factura: ").append(idFactura)
                        .append("\nFecha: ").append(fechaFactura);

                // Consulta detalles relacionados con la factura actual
                String queryDetalle = "SELECT ID_DETALLE, MONTO_DETALLE, FORMA_DE_PAGO FROM DETALLE_FACTURA WHERE ID_FACTURA = ?";
                Cursor cursorDetalle = db.rawQuery(queryDetalle, new String[]{idFactura});

                if (cursorDetalle.moveToFirst()) {
                    do {
                        String idDetalle = cursorDetalle.getString(cursorDetalle.getColumnIndex("ID_DETALLE"));
                        double monto = cursorDetalle.getDouble(cursorDetalle.getColumnIndex("MONTO_DETALLE"));
                        String formaPago = cursorDetalle.getString(cursorDetalle.getColumnIndex("FORMA_DE_PAGO"));
                        facturaInfo.append("\n  - Detalle: ").append(idDetalle)
                                .append(", Monto: ").append(monto)
                                .append(", Pago: ").append(formaPago);
                    } while (cursorDetalle.moveToNext());
                } else {
                    facturaInfo.append("\n  - Sin detalles asociados.");
                }

                cursorDetalle.close();
                resultados.add(facturaInfo.toString());

            } while (cursorFactura.moveToNext());
        }

        cursorFactura.close();
        db.close();

        return resultados;
    }

    public boolean eliminarFacturaYDetalles(String idFactura) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Eliminar los detalles asociados
            db.delete("DETALLE_FACTURA", "ID_FACTURA = ?", new String[]{idFactura});

            // Eliminar la factura
            int filasAfectadas = db.delete("FACTURA", "ID_FACTURA = ?", new String[]{idFactura});

            db.setTransactionSuccessful();
            return filasAfectadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            db.endTransaction();
            db.close();
        }
    }
    public List<String> consultarFacturasYDetallesPorFecha(String fecha) {
        List<String> resultados = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorFacturas = db.rawQuery(
                "SELECT * FROM FACTURA WHERE FECHA_FACTURA = ?", new String[]{fecha});

        if (cursorFacturas.moveToFirst()) {
            do {
                // Datos de la factura
                String idFactura = cursorFacturas.getString(cursorFacturas.getColumnIndexOrThrow("ID_FACTURA"));
                String idConsulta = cursorFacturas.getString(cursorFacturas.getColumnIndexOrThrow("ID_CONSULTA"));
                String fechaFactura = cursorFacturas.getString(cursorFacturas.getColumnIndexOrThrow("FECHA_FACTURA"));

                resultados.add("Factura ID: " + idFactura +
                        " | ID Consulta: " + idConsulta +
                        " | Fecha: " + fechaFactura);

                // Buscar detalles para esta factura
                Cursor cursorDetalles = db.rawQuery(
                        "SELECT * FROM DETALLE_FACTURA WHERE ID_FACTURA = ?", new String[]{idFactura});

                if (cursorDetalles.moveToFirst()) {
                    do {
                        String idDetalle = cursorDetalles.getString(cursorDetalles.getColumnIndexOrThrow("ID_DETALLE"));
                        double monto = cursorDetalles.getDouble(cursorDetalles.getColumnIndexOrThrow("MONTO_DETALLE"));
                        String formaPago = cursorDetalles.getString(cursorDetalles.getColumnIndexOrThrow("FORMA_DE_PAGO"));

                        resultados.add("   → Detalle ID: " + idDetalle +
                                " | Monto: $" + monto +
                                " | Pago: " + formaPago);
                    } while (cursorDetalles.moveToNext());
                } else {
                    resultados.add("   → No hay detalles para esta factura.");
                }

                cursorDetalles.close();

            } while (cursorFacturas.moveToNext());
        }

        cursorFacturas.close();
        db.close();
        return resultados;
    }
    public boolean actualizarFechaFactura(String idFactura, String nuevaFecha) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Preparar la sentencia SQL
        ContentValues values = new ContentValues();
        values.put("FECHA_FACTURA", nuevaFecha);

        // Condición para encontrar la factura por ID
        String selection = "ID_FACTURA = ?";
        String[] selectionArgs = {idFactura};

        // Ejecutar la actualización
        int rowsAffected = db.update("FACTURA", values, selection, selectionArgs);

        // Si se actualizó al menos una fila, devolvemos verdadero
        return rowsAffected > 0;
    }
    //-------------------------------------- CRUD MEDICAMENTO --------------------------------
    public long insertarMedicamento(String nombre, String fechaVencimiento, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_MEDICAMENTO", generarId("MED")); // ← AÑADIR ESTO
        values.put("NOMBRE_MEDICAMENTO", nombre);
        values.put("FECHA_VENCIMIENTO", fechaVencimiento);
        values.put("PRECIO_MEDICAMENTO", precio);

        long id = db.insert("MEDICAMENTO", null, values);
        db.close();
        return id;
    }

    public Cursor obtenerTodosLosMedicamentos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM MEDICAMENTO", null);
    }

    public Cursor obtenerMedicamentoPorId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("MEDICAMENTO",  // Nombre de la tabla
                new String[]{"ID_MEDICAMENTO", "NOMBRE_MEDICAMENTO", "FECHA_VENCIMIENTO", "PRECIO_MEDICAMENTO"}, // Columnas
                "ID_MEDICAMENTO = ?",   // Where clause
                new String[]{id},       // Parámetros del where (ya es String)
                null, null, null);      // Group by, having, order by
    }

    public int actualizarMedicamento(String id, String nombre, String fechaVencimiento, double precio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE_MEDICAMENTO", nombre);
        values.put("FECHA_VENCIMIENTO", fechaVencimiento);
        values.put("PRECIO_MEDICAMENTO", precio);

        int rowsAffected = db.update("MEDICAMENTO", values,
                "ID_MEDICAMENTO = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected;
    }


    public int eliminarMedicamento(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("MEDICAMENTO",
                "ID_MEDICAMENTO = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted;
    }

    public Cursor buscarMedicamentosPorNombre(String nombre) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("MEDICAMENTO",
                new String[]{"ID_MEDICAMENTO", "NOMBRE_MEDICAMENTO", "FECHA_VENCIMIENTO", "PRECIO_MEDICAMENTO"},
                "NOMBRE_MEDICAMENTO LIKE ?",
                new String[]{"%" + nombre + "%"},
                null, null,
                "NOMBRE_MEDICAMENTO ASC");
    }

    public boolean insertarMedicamentoDetalle(String idDetalle, String idMedicamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_DETALLE", idDetalle);
        values.put("ID_MEDICAMENTO", idMedicamento);

        long resultado = db.insert("MEDICAMENTO_DETALLE", null, values);
        db.close();
        return resultado != -1;
    }

    public int contarMedicamentos() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM MEDICAMENTO", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    //
    public boolean agregarMedicamentoADetalle(String idDetalle, String idMedicamento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_DETALLE", idDetalle);
        values.put("ID_MEDICAMENTO", String.valueOf(idMedicamento)); // Asegúrate que sea String

        long result = db.insert("MEDICAMENTO_DETALLE", null, values);
        db.close();
        return result != -1;
    }

    //------------------------------------ CRUD HOSPITAL-----------------------------------

    // Insertar un hospital
    public long insertarHospital(String nombre, String direccion, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("direccion", direccion);
        valores.put("telefono", telefono);
        return db.insert("HOSPITAL", null, valores);
    }

    // Obtener todos los hospitales (para Spinner)
    public ArrayList<Integer> obtenerIdsHospitales() {
        ArrayList<Integer> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_hospital FROM HOSPITAL", null);
        while (cursor.moveToNext()) {
            lista.add(cursor.getInt(0));
        }
        cursor.close();
        return lista;
    }

    // Consultar hospital por ID
    public Cursor consultarHospitalPorId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM HOSPITAL WHERE id_hospital = ?", new String[]{String.valueOf(id)});
    }

    // Actualizar hospital
    public int actualizarHospital(int id, String nombre, String direccion, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("direccion", direccion);
        valores.put("telefono", telefono);
        return db.update("HOSPITAL", valores, "id_hospital = ?", new String[]{String.valueOf(id)});
    }

    // Eliminar hospital
    public int eliminarHospital(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("HOSPITAL", "id_hospital = ?", new String[]{String.valueOf(id)});
    }








    //------------------------------------ CRUD DETALLE_FACTURA-----------------------------------
    public String insertarDetalleFactura(String idFactura, double montoDetalle, String formaPago) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String idDetalle = generarId("DET");  // Genera el ID con prefijo "DET"
        values.put("ID_DETALLE", idDetalle);
        values.put("ID_FACTURA", idFactura);
        values.put("MONTO_DETALLE", montoDetalle);
        values.put("FORMA_DE_PAGO", formaPago);

        long resultado = db.insert("DETALLE_FACTURA", null, values);
        db.close();
        return (resultado != -1) ? idDetalle : null;  // Devolver el ID generado como String
    }

    public Cursor obtenerDetallesConMedicamento() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT d.ID_DETALLE, d.MONTO_DETALLE, d.FORMA_DE_PAGO, m.NOMBRE_MEDICAMENTO, d.ID_FACTURA " +
                "FROM DETALLE_FACTURA d " +
                "JOIN MEDICAMENTO_DETALLE md ON d.ID_DETALLE = md.ID_DETALLE " +
                "JOIN MEDICAMENTO m ON md.ID_MEDICAMENTO = m.ID_MEDICAMENTO";
        return db.rawQuery(query, null);
    }

    public Cursor obtenerTodosLosDetalles() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM DETALLE_FACTURA", null);
    }

    public int actualizarDetalleFactura(String idDetalle, double montoDetalle, String formaPago, List<String> nuevosMedicamentos) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Actualizar campos del detalle
        ContentValues values = new ContentValues();
        values.put("MONTO_DETALLE", montoDetalle);
        values.put("FORMA_DE_PAGO", formaPago);
        int filasActualizadas = db.update("DETALLE_FACTURA", values, "ID_DETALLE = ?", new String[]{idDetalle});

        // Verificar si el detalle fue actualizado (es decir, si existe)
        if (filasActualizadas == 0) {
            Log.e("DB_ERROR", "No existe el ID_DETALLE: " + idDetalle + " en DETALLE_FACTURA.");
            return 0;
        }

        // Insertar nuevos medicamentos relacionados
        for (String idMedicamento : nuevosMedicamentos) {
            ContentValues rel = new ContentValues();
            rel.put("ID_DETALLE", idDetalle);
            rel.put("ID_MEDICAMENTO", idMedicamento);

            try {
                long result = db.insertOrThrow("MEDICAMENTO_DETALLE", null, rel);
                Log.d("DB_INSERT", "Medicamento relacionado insertado: " + idMedicamento);
            } catch (SQLiteConstraintException e) {
                Log.e("DB_ERROR", "Error al insertar relación: ID_MEDICAMENTO=" + idMedicamento + " ID_DETALLE=" + idDetalle + " -> " + e.getMessage());
            }
        }

        return filasActualizadas;
    }


    public int eliminarDetalleFactura(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Primero eliminar relaciones en MEDICAMENTO_DETALLE
        db.delete("MEDICAMENTO_DETALLE", "ID_DETALLE = ?", new String[]{id});

        // Luego eliminar de DETALLE_FACTURA
        int rowsDeleted = db.delete("DETALLE_FACTURA", "ID_DETALLE = ?", new String[]{id});

        db.close();
        return rowsDeleted;
    }

    // CRUD HOSPITALIZACION




    public List<String> obtenerTodosLosIdPacientes() {
        List<String> listaIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_PACIENTE FROM PACIENTE", null);

        if (cursor.moveToFirst()) {
            do {
                listaIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaIds;
    }


    public List<String> obtenerIdsHospitalizacion() {
        List<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_HOSPITALIZACION FROM hospitalizacion", null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("ID_HOSPITALIZACION"));
                ids.add(id);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ids;
    }
    // Metodo para obtener los IDs de los pacientes
    public List<String> obtenerIdsPacientes() {
        List<String> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_paciente FROM paciente", null);

        if (cursor.moveToFirst()) {
            do {
                lista.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return lista;
    }

    public long insertarHospitalizacion(String idPaciente, String fechaIngreso, String fechaSalida) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_HOSPITALIZACION", generarId("HOS"));
        values.put("ID_PACIENTE", idPaciente);
        values.put("FECHA_INGRESO", fechaIngreso);
        values.put("FECHA_SALIDA", fechaSalida);
        return db.insert("HOSPITALIZACION", null, values);
    }

    public Cursor consultarHospitalizacion(String idHospitalizacion) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM hospitalizacion WHERE ID_HOSPITALIZACION = ?",
                new String[]{idHospitalizacion}
        );
    }



    public boolean actualizarHospitalizacion(String id, String idPaciente, String fechaIngreso, String fechaAlta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_PACIENTE", idPaciente);         // En mayúsculas
        values.put("FECHA_INGRESO", fechaIngreso);
        values.put("FECHA_SALIDA", fechaAlta);
        int filas = db.update("hospitalizacion", values, "ID_HOSPITALIZACION = ?", new String[]{id});
        return filas > 0;
    }
    public boolean eliminarHospitalizacion(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filas = db.delete("hospitalizacion", "ID_HOSPITALIZACION = ?", new String[]{id});
        return filas > 0;
    }


    public List<String> obtenerTodosLosIdHospitales() {
        List<String> listaIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_HOSPITAL, NOMBRE FROM HOSPITAL", null);

        if (cursor.moveToFirst()) {
            do {
                // Esto mostrará "1 - Hospital Nacional", etc.
                String id = cursor.getString(0);
                String nombre = cursor.getString(1);
                listaIds.add(id + " - " + nombre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaIds;
    }
    //-------------------------------------- CRUD ASEGURADORA --------------------------------
    public long insertarAseguradora(String idAseguradora, String nombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_ASEGURADORA", idAseguradora);
        values.put("NOMBRE_ASEGURADORA", nombre);
        return db.insert("ASEGURADORA", null, values);
    }

    public Cursor consultarAseguradoras() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM ASEGURADORA", null);
    }

    public Cursor obtenerAseguradoraPorId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM ASEGURADORA WHERE ID_ASEGURADORA = ?", new String[]{id});
    }

    public boolean actualizarAseguradora(String idAseguradora, String nuevoNombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NOMBRE_ASEGURADORA", nuevoNombre);
        return db.update("ASEGURADORA", values, "ID_ASEGURADORA = ?", new String[]{idAseguradora}) > 0;
    }

    public boolean eliminarAseguradora(String idAseguradora) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ASEGURADORA", "ID_ASEGURADORA = ?", new String[]{idAseguradora}) > 0;
    }

    // ------------------------- tratamiento crud -------------------------

    public long insertarTratamiento(String idConsulta, String fechaTratamiento, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_TRATAMIENTO", generarId("TRA"));
        values.put("ID_CONSULTA", idConsulta);
        values.put("FECHA_TRATAMIENTO", fechaTratamiento);
        values.put("DESCRIPCION", descripcion);
        return db.insert("TRATAMIENTO", null, values);
    }

    public Cursor consultarTratamientos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TRATAMIENTO", null);
    }

    public Cursor obtenerTratamientoPorId(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM TRATAMIENTO WHERE ID_TRATAMIENTO = ?", new String[]{id});
    }

    public boolean actualizarTratamiento(String id, String idConsulta, String fecha, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_CONSULTA", idConsulta);
        values.put("FECHA_TRATAMIENTO", fecha);
        values.put("DESCRIPCION", descripcion);
        return db.update("TRATAMIENTO", values, "ID_TRATAMIENTO = ?", new String[]{id}) > 0;
    }

    public boolean eliminarTratamiento(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("TRATAMIENTO", "ID_TRATAMIENTO = ?", new String[]{id}) > 0;
    }

    public List<String> obtenerIdsTratamientos() {
        List<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_TRATAMIENTO FROM TRATAMIENTO", null);
        while (cursor.moveToNext()) {
            ids.add(cursor.getString(0));
        }
        cursor.close();
        return ids;
    }

// ------------------------- crud consulta el mas paloma -------------------------

    public long insertarConsulta(String idDoctor, String idPaciente, String fechaConsulta, String emergencia, double cuota, String diagnostico) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_CONSULTA", generarId("CON"));
        values.put("ID_DOCTOR", idDoctor);
        values.put("ID_PACIENTE", idPaciente);
        values.put("FECHA_CONSULTA", fechaConsulta);
        values.put("EMERGENCIA", emergencia);
        values.put("CUOTA", cuota);
        values.put("DIAGNOSTICO", diagnostico);
        return db.insert("CONSULTA", null, values);
    }

    public Cursor obtenerConsultaPorId(String idConsulta) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM CONSULTA WHERE ID_CONSULTA = ?", new String[]{idConsulta});
    }

    public boolean actualizarConsulta(String idConsulta, String idDoctor, String idPaciente, String fechaConsulta, String emergencia, double cuota, String diagnostico) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID_DOCTOR", idDoctor);
        values.put("ID_PACIENTE", idPaciente);
        values.put("FECHA_CONSULTA", fechaConsulta);
        values.put("EMERGENCIA", emergencia);
        values.put("CUOTA", cuota);
        values.put("DIAGNOSTICO", diagnostico);
        return db.update("CONSULTA", values, "ID_CONSULTA = ?", new String[]{idConsulta}) > 0;
    }

    public boolean eliminarConsulta(String idConsulta) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("CONSULTA", "ID_CONSULTA = ?", new String[]{idConsulta}) > 0;
    }

    public List<String> obtenerIdsConsultas() {
        List<String> ids = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID_CONSULTA FROM CONSULTA", null);
        while (cursor.moveToNext()) {
            ids.add(cursor.getString(0));
        }
        cursor.close();
        return ids;
    }

    // ------------------------- Cargar ubicaciones desde JSON -------------------------
    private void cargarDatosDesdeJSON(SQLiteDatabase db) {
        try {
            InputStream is = context.getAssets().open("el_salvador.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
            JSONArray departamentosArray = jsonObject.getJSONArray("departamentos");

            for (int i = 0; i < departamentosArray.length(); i++) {
                JSONObject departamentoObj = departamentosArray.getJSONObject(i);
                String nombreDepartamento = departamentoObj.getString("nombre");

                // Insertar departamento
                ContentValues deptoValues = new ContentValues();
                deptoValues.put("nombre", nombreDepartamento);
                long departamentoId = db.insert("departamento", null, deptoValues);

                JSONArray municipiosArray = departamentoObj.getJSONArray("municipios");

                for (int j = 0; j < municipiosArray.length(); j++) {
                    JSONObject municipioObj = municipiosArray.getJSONObject(j);
                    String nombreMunicipio = municipioObj.getString("nombre");

                    // Insertar municipio
                    ContentValues municipioValues = new ContentValues();
                    municipioValues.put("nombre", nombreMunicipio);
                    municipioValues.put("id_departamento", departamentoId);
                    long municipioId = db.insert("municipio", null, municipioValues);

                    // Insertar distritos dentro del municipio
                    JSONArray distritosArray = municipioObj.getJSONArray("distritos");
                    for (int k = 0; k < distritosArray.length(); k++) {
                        JSONObject distritoObj = distritosArray.getJSONObject(k);
                        String nombreDistrito = distritoObj.getString("nombre");

                        ContentValues distritoValues = new ContentValues();
                        distritoValues.put("nombre", nombreDistrito);
                        distritoValues.put("id_municipio", municipioId);
                        db.insert("distrito", null, distritoValues);
                    }
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
