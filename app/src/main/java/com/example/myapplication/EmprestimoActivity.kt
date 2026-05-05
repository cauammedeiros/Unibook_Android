package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class EmprestimoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emprestimo)

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish() // Volta para a tela de detalhes do livro
        }

        configurarMenuNavegacao()
    }

    private fun configurarMenuNavegacao() {
        findViewById<LinearLayout>(R.id.navInicio).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navBiblioteca).setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navChatbot).setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navBuscar).setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navPerfil).setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
    }
}