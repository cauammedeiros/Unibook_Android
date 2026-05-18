package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Inicialização dos botões de navegação (IDs do layout_bottom_nav.xml)
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)

        // Destaque da aba Perfil usando a extensão toColorInt() do Kotlin
        val corDestaque = "#5B7FFF".toColorInt()
        findViewById<ImageView>(R.id.iv_profile)?.setColorFilter(corDestaque)
        findViewById<TextView>(R.id.tv_profile)?.setTextColor(corDestaque)

        // Botão de Logout (Sincronizado com o ImageView no XML)
        findViewById<ImageView>(R.id.btnLogoutIcon)?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Configuração dos cliques de navegação
        btnInicio?.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
        btnBiblioteca?.setOnClickListener { startActivity(Intent(this, TelaBibliotecaActivity::class.java)) }
        btnChatbot?.setOnClickListener { startActivity(Intent(this, ChatbotActivity::class.java)) }
        btnBuscar?.setOnClickListener { startActivity(Intent(this, BuscaActivity::class.java)) }
    }
}
