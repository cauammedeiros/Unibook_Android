package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProcurarLivrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurar_livros)

        // Botão Voltar (ID sincronizado com o XML)
        findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener {
            finish()
        }

        // Configuração do RecyclerView que agora está no seu XML
        val rvLivros = findViewById<RecyclerView>(R.id.rvLivros)
        rvLivros.layoutManager = GridLayoutManager(this, 3)
        
        // Dados de exemplo para preencher a grade
        val listaExemplo = listOf(
            Livro(titulo = "Exemplo 1", capaUrl = ""),
            Livro(titulo = "Exemplo 2", capaUrl = ""),
            Livro(titulo = "Exemplo 3", capaUrl = "")
        )
        
        rvLivros.adapter = LivroAdapter(listaExemplo)
    }
}
