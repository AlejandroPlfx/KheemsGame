package mx.edu.itson.kheems

import Puntuacion
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PuntuacionesDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Puntuaciones.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${Puntuacion.NOMBRE_TABLA} (" +
                    "${Puntuacion.COLUMNA_ID} INTEGER PRIMARY KEY," +
                    "${Puntuacion.COLUMNA_NOMBRE} TEXT," +
                    "${Puntuacion.COLUMNA_INTENTOS} INTEGER" +
                    ")"

        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${Puntuacion.NOMBRE_TABLA}"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun insertarPuntuacion(nombre: String, puntaje: Int, partidasJugadas: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(Puntuacion.COLUMNA_NOMBRE, nombre)
            put(Puntuacion.COLUMNA_INTENTOS, puntaje)
        }
        db.insert(Puntuacion.NOMBRE_TABLA, null, values)
    }

    fun obtenerPuntuaciones(): MutableList<Puntuacion> {
        val puntuaciones = mutableListOf<Puntuacion>()
        val db = readableDatabase
        val projection = arrayOf(
            Puntuacion.COLUMNA_ID,
            Puntuacion.COLUMNA_NOMBRE,
            Puntuacion.COLUMNA_INTENTOS
        )
        val sortOrder = "${Puntuacion.COLUMNA_INTENTOS} ASC"
        val cursor = db.query(
            Puntuacion.COLUMNA_NOMBRE,
            projection,
            null,
            null,
            null,
            null,
            sortOrder,
            "10"
        )
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(Puntuacion.COLUMNA_ID))
                val nombre =
                    getString(getColumnIndexOrThrow(Puntuacion.COLUMNA_NOMBRE))
                val puntaje =
                    getInt(getColumnIndexOrThrow(Puntuacion.COLUMNA_INTENTOS))
                puntuaciones.add(Puntuacion(nombre, puntaje))
            }
        }
        cursor.close()
        return puntuaciones
    }
}
