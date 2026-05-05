package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout

class ProcurarLivrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurar_livros)

        // RF21.1 - Botão Voltar
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        // RF21.3 - Redirecionar para Editar Livros ao clicar no livro (Simulação)
        val btnLivro1 = findViewById<LinearLayout>(R.id.livro1)
        btnLivro1.setOnClickListener {
            val intent = Intent(this, EditarLivroActivity::class.java)
            startActivity(intent)
        }
    }
}