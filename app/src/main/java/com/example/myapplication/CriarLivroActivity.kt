package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CriarLivroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_livro)

        // Botão Voltar (Canto Esquerdo)
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        val txtErro = findViewById<TextView>(R.id.txtErroCriarLivro)
        val btnCriar = findViewById<Button>(R.id.btnCriarLivro)

        // Lógica de Fluxo solicitada: Mostrar erro ao clicar em Criar
        btnCriar.setOnClickListener {
            txtErro.visibility = View.VISIBLE
        }
    }
}