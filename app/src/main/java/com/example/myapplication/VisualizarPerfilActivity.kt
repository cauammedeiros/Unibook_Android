package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class VisualizarPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_perfil)

        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        val btnVerificarMulta = findViewById<Button>(R.id.btnVerificarMulta)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnVerificarMulta.setOnClickListener {
            // Lógica para verificar multas ou navegar para a tela de multas
        }
    }
}
