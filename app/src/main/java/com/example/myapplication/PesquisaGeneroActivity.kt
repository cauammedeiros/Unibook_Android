package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PesquisaGeneroActivity : AppCompatActivity() {

    private lateinit var rvLivrosGenero: RecyclerView
    private lateinit var btnVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_genero)

        rvLivrosGenero = findViewById(R.id.rvLivrosGenero)
        btnVoltar = findViewById(R.id.btnVoltar)

        btnVoltar.setOnClickListener { finish() }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val livros = listOf(
            Livro("Livro 1", 0),
            Livro("Livro 2", 0),
            Livro("Livro 3", 0),
            Livro("Livro 4", 0),
            Livro("Livro 5", 0),
            Livro("Livro 6", 0)
        )

        val adapter = LivroGridAdapter(livros)
        rvLivrosGenero.adapter = adapter
        rvLivrosGenero.layoutManager = GridLayoutManager(this, 3)
    }
}