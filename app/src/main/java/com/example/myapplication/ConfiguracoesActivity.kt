package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ConfiguracoesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracoes)

        // Configuração do botão de voltar
        val btnVoltar = findViewById<ImageView>(R.id.btnBack)
        btnVoltar.setOnClickListener { finish() }
    }
}