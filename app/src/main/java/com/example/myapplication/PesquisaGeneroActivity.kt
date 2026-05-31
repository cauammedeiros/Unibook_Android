package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PesquisaGeneroActivity : BaseActivity() {

    private lateinit var rvLivrosGenero: RecyclerView
    private val db = FirebaseFirestore.getInstance()
    private lateinit var adapter: LivroGridAdapter
    private val listaLivros = mutableListOf<Livro>()
    private lateinit var btnVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_genero)

        // Recebe o gênero vindo da outra tela
        val generoSelecionado = intent.getStringExtra("GENERO_NOME") ?: ""

        // Atualiza o título da tela
        val tituloExibicao = when(generoSelecionado) {
            "Sugeridos" -> "Livros Sugeridos"
            "Minha Lista" -> "Minha Lista"
            else -> "Gênero: $generoSelecionado"
        }
        findViewById<TextView>(R.id.txtTitulo).text = tituloExibicao

        rvLivrosGenero = findViewById(R.id.rvLivrosGenero)
        btnVoltar = findViewById(R.id.btnVoltar)

        btnVoltar.setOnClickListener { finish() }

        adapter = LivroGridAdapter(listaLivros)
        rvLivrosGenero.adapter = adapter
        rvLivrosGenero.layoutManager = GridLayoutManager(this, 3)

        // Carrega os livros baseados na categoria/gênero
        if (generoSelecionado.isNotEmpty()) {
            when(generoSelecionado) {
                "Sugeridos" -> carregarTodosLivros()
                "Minha Lista" -> carregarFavoritos()
                else -> carregarLivrosPorGenero(generoSelecionado)
            }
        }

        configurarNavegacao()
        configurarBotaoTema()
    }

    private fun carregarTodosLivros() {
        db.collection("Livros")
            .get()
            .addOnSuccessListener { documents ->
                listaLivros.clear()
                for (document in documents) {
                    val livro = document.toObject(Livro::class.java)
                    livro.id = document.id
                    listaLivros.add(livro)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
            }
    }

    private fun carregarFavoritos() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null) ?: return

        db.collection("Favoritos")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                listaLivros.clear()
                for (document in documents) {
                    val livro = document.toObject(Livro::class.java)
                    livro.id = document.getString("livroId") ?: document.id
                    listaLivros.add(livro)
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun carregarLivrosPorGenero(genero: String) {
        db.collection("Livros")
            .whereEqualTo("Genero", genero)
            .get()
            .addOnSuccessListener { documents ->
                listaLivros.clear()
                for (document in documents) {
                    val livro = document.toObject(Livro::class.java)
                    livro.id = document.id
                    listaLivros.add(livro)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
            }
    }

    private fun configurarNavegacao() {
        findViewById<LinearLayout>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_library)?.setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_chatbot)?.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_search)?.setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        btnTema?.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema?.setOnClickListener {
            val modo = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.setDefaultNightMode(modo)
        }
    }
}
