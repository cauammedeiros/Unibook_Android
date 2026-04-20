package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Livros de Ação
        val listaDeAcao = listOf(
            Livro("Teste",R.drawable.logo)
        )

        val rvAcao = findViewById<RecyclerView>(R.id.rvAcao)
        rvAcao.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvAcao.adapter = LivroAdapter(listaDeAcao)

        // Livros de Suspense
        val listaDeSuspense = listOf(
            Livro("Teste",R.drawable.logo_nome)
        )

        val rvSuspense = findViewById<RecyclerView>(R.id.rvSuspense)
        rvSuspense.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSuspense.adapter = LivroAdapter(listaDeSuspense)

    }
}