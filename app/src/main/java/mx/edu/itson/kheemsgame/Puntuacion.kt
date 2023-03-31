data class Puntuacion(val nombre: String, val intentos: Int) {

    companion object {
        const val NOMBRE_TABLA = "puntuaciones"
        const val COLUMNA_ID = "id"
        const val COLUMNA_NOMBRE = "nombre"
        const val COLUMNA_INTENTOS = "intentos"
        //const val COLUMNA_PARTIDAS = "partidas"

        const val CREAR_TABLA = "CREATE TABLE $NOMBRE_TABLA (" +
                "$COLUMNA_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMNA_NOMBRE TEXT," +
                "$COLUMNA_INTENTOS INTEGER," +
                //"$COLUMNA_PARTIDAS INTEGER" +
                ")"
    }
}
