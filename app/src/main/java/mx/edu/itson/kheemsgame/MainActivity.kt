package com.example.kheems
import ItemPuntuacion
import JuegoActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import mx.edu.itson.kheemsgame.R

class MainActivity : AppCompatActivity() {
    private lateinit var btnJugar: Button
    private lateinit var btnPuntuacion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnJugar = findViewById(R.id.btn_jugar)
        btnPuntuacion = findViewById(R.id.btn_puntuacion)

        btnJugar.setOnClickListener {
            val intent = Intent(this, JuegoActivity ::class.java)
            startActivity(intent)
        }

        btnPuntuacion.setOnClickListener {
            val intent = Intent(this, ItemPuntuacion ::class.java)
            startActivity(intent)
        }
    }
}
