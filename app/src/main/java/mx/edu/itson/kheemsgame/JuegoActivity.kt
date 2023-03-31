import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mx.edu.itson.kheems.PuntuacionesDBHelper
import mx.edu.itson.kheemsgame.R

class JuegoActivity : AppCompatActivity() {

    private lateinit var listaCartas: MutableList<Int>
    private val listaBotones = mutableListOf<ImageButton>()
    private var cartasDestapadas = 0
    private var intentos = 0
    private var partidas = 0
    private var posicionCheems = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        for (i in 1..16) {
            val buttonId = resources.getIdentifier("Card$i", "id", packageName)
            val boton = findViewById<ImageButton>(buttonId)
            listaBotones.add(boton)
        }

        iniciarJuego()
    }

    private fun iniciarJuego() {
        // Crea una lista con los índices de las cartas y la mezcla aleatoriamente
        listaCartas = (0..15).shuffled().toMutableList()

        // Ubica aleatoriamente la carta de Cheems llorando
        posicionCheems = (0..15).random()

        // Asigna la imagen de la carta a cada botón
        for (i in 0..15) {
            listaBotones[i].setImageResource(R.drawable.icon_pregunta)
            listaBotones[i].setOnClickListener { v -> manejarTap(v as ImageButton, i) }
        }

        cartasDestapadas = 0
        intentos = 0
        partidas++
    }

    private fun manejarTap(btn: ImageButton, posicion: Int) {
        if (posicion == posicionCheems) {
            mostrarCarta(btn, R.drawable.icon_cheems_llora)
            mostrarTodasLasCartas(R.drawable.icon_cheems)
            Toast.makeText(this, "¡PERMDISTE!", Toast.LENGTH_SHORT).show()
            terminarPartida()
        } else {
            mostrarCarta(btn, R.drawable.icon_cheems)
            cartasDestapadas++
            intentos++
            if (cartasDestapadas == 16) {
                Toast.makeText(this, "¡GAMNASTE!", Toast.LENGTH_SHORT).show()
                mostrarCarta(listaBotones[posicionCheems], R.drawable.cheems_win)
                terminarPartida()
            }
        }
    }

    private fun mostrarCarta(btn: ImageButton, idImagen: Int) {
        btn.setImageResource(idImagen)
        btn.isClickable = false
    }

    private fun mostrarTodasLasCartas(idImagen: Int) {
        for (i in 0..15) {
            if (i != posicionCheems) {
                mostrarCarta(listaBotones[i], idImagen)
            }
        }
    }

    private fun terminarPartida() {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_ganador)
            dialog.setCancelable(false)

            val tvGanador = dialog.findViewById<TextView>(R.id.tv_ganador)
            val tvNombre = dialog.findViewById<TextView>(R.id.tv_nombre)
            val etNombre = dialog.findViewById<EditText>(R.id.et_nombre)
            val btnGuardar = dialog.findViewById<Button>(R.id.btn_guardar)

            tvGanador.text = "¡Ganaste!"

            btnGuardar.setOnClickListener {
                val nombre = etNombre.text.toString()
                val puntuacion = cartasDestapadas

                guardarPuntuacion(nombre,puntuacion)

                dialog.dismiss()
                finish()
            }

            dialog.show()
        }


    private fun guardarPuntuacion(nombre: String, puntuacion: Int) {
        val dbHelper = PuntuacionesDBHelper(this)
        dbHelper.insertarPuntuacion(nombre, puntuacion, 1)
    }


}

