package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class TelaBibliotecaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_biblioteca)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNavigation)) { v, insets ->
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
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBibliotecaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnBuscar.setOnClickListener {
            val intent = Intent(this, BuscaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        // Botões de Modo de Tema
        val btnLight = findViewById<ImageView>(R.id.btnSun)
        val btnNight = findViewById<ImageView>(R.id.btnMoon)

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

        val listaDeLivros = listOf(
            Livro("Teste",R.drawable.logo_nome),
            Livro("Teste",R.drawable.logo_nome),
            Livro("Teste",R.drawable.logo_nome),
            Livro("Teste",R.drawable.logo_nome),
            Livro("Teste",R.drawable.logo_nome),
            Livro("Teste",R.drawable.logo_nome),
        )

        val rvLivros = findViewById<RecyclerView>(R.id.rvLivros)
        rvLivros.adapter = LivroAdapter(listaDeLivros)
    }
    }
