package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
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

        // Configurar saudação dinâmica
        val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
        txtUsuario.text = getString(R.string.home_saudacao, "Carlos")

        configurarNavegacao()
        configurarListasDeLivros()
        configurarBotaoTema()
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        
        // Define o ícone inicial com base no tema atual
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        btnTema.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema.setOnClickListener {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun configurarNavegacao() {
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        // Destacar tela atual (Início)
        val corDestaque = Color.parseColor("#5B7FFF")
        findViewById<ImageView>(R.id.iv_home).setColorFilter(corDestaque)
        findViewById<TextView>(R.id.tv_home).setTextColor(corDestaque)

        btnBiblioteca.setOnClickListener { startActivity(Intent(this, TelaBibliotecaActivity::class.java)) }
        btnChatbot.setOnClickListener { startActivity(Intent(this, ChatbotActivity::class.java)) }
        btnBuscar.setOnClickListener { startActivity(Intent(this, BuscaActivity::class.java)) }
        btnPerfil.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }
    }

    private fun configurarListasDeLivros() {
        // Dados de teste para os livros (muitos itens para garantir o scroll lateral)
        val listaTeste = mutableListOf<Livro>()
        for (i in 1..15) {
            listaTeste.add(Livro("Livro $i", imagem = R.drawable.logo_nome))
        }

        // Popula todas as 12 categorias definidas no XML
        configurarRecyclerView(R.id.rvAclamados, listaTeste)
        configurarRecyclerView(R.id.rvEducacao, listaTeste)
        configurarRecyclerView(R.id.rvMinhaLista, listaTeste)
        configurarRecyclerView(R.id.rvComedias, listaTeste)
        configurarRecyclerView(R.id.rvSuspense, listaTeste)
        configurarRecyclerView(R.id.rvFiccao, listaTeste)
        configurarRecyclerView(R.id.rvTerror, listaTeste)
        configurarRecyclerView(R.id.rvRomance, listaTeste)
        configurarRecyclerView(R.id.rvAventura, listaTeste)
        configurarRecyclerView(R.id.rvDocumentarios, listaTeste)
        configurarRecyclerView(R.id.rvAnimes, listaTeste)
        configurarRecyclerView(R.id.rvClassicos, listaTeste)
    }

    private fun configurarRecyclerView(id: Int, lista: List<Livro>) {
        val rv = findViewById<RecyclerView>(id)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = LivroAdapter(lista)
    }
}
