package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
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

        // Menu de Navegação
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        btnInicio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        btnBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBibliotecaActivity::class.java)
            startActivity(intent)
        }

        btnChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            startActivity(intent)
        }

        btnBuscar.setOnClickListener {
            val intent = Intent(this, BuscaActivity::class.java)
            startActivity(intent)
        }

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }


        // Botões de Modo de Tema
        val btnLight = findViewById<ImageButton>(R.id.btnLightMode)
        val btnNight = findViewById<ImageButton>(R.id.btnNightMode)

        // Configuração para o Modo Claro
        btnLight.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            delegate.applyDayNight()
        }

        // Configuração para o Modo Escuro
        btnNight.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            delegate.applyDayNight()
        }

        // Livros de Ação
        val listaDeAcao = listOf(
            Livro("Teste",R.drawable.logo_nome)
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
