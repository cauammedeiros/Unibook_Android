package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ChatbotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        // Menu de Navegação
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        // Destacar tela atual (Chatbot)
        findViewById<ImageView>(R.id.iv_chatbot).setColorFilter(Color.parseColor("#2196F3"))
        findViewById<TextView>(R.id.tv_chatbot).setTextColor(Color.parseColor("#2196F3"))

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
    }
}