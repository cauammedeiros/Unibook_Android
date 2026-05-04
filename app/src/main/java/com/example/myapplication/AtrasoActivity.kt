package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class AtrasoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atraso)

        // Configuração do botão de voltar
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        val btnGerarQrCode = findViewById<Button>(R.id.btnGerarQrCode)
        btnGerarQrCode.setOnClickListener {
            val intent = Intent(this, QrCodePagamentoActivity::class.java)
            startActivity(intent)
        }
    }
    }
