package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BuscaFiltradaActivity : AppCompatActivity() {

    private lateinit var rvBuscaFiltrada: RecyclerView
    private lateinit var btnVoltar: ImageButton
    private lateinit var txtNomeBusca: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca_filtrada)

        rvBuscaFiltrada = findViewById(R.id.rvBuscaFiltrada)
        btnVoltar = findViewById(R.id.btnVoltar)
        txtNomeBusca = findViewById(R.id.txtNomeBusca)

        val query = intent.getStringExtra("QUERY") ?: "Busca"
        txtNomeBusca.text = query

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
        rvBuscaFiltrada.adapter = adapter
        rvBuscaFiltrada.layoutManager = GridLayoutManager(this, 3)
    }
}