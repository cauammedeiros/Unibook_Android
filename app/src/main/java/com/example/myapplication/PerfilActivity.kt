package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        // Inicialização dos botões de navegação (IDs do layout_bottom_nav.xml)
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        // Destaque da aba Perfil
        findViewById<ImageView>(R.id.iv_profile)?.setColorFilter(Color.parseColor("#5B7FFF"))
        findViewById<TextView>(R.id.tv_profile)?.setTextColor(Color.parseColor("#5B7FFF"))

        // Botão de Logout (Sincronizado com o ImageView no XML)
        findViewById<ImageView>(R.id.btnLogoutIcon)?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Navegação
        btnInicio?.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
        btnBiblioteca?.setOnClickListener { startActivity(Intent(this, TelaBibliotecaActivity::class.java)) }
        btnChatbot?.setOnClickListener { startActivity(Intent(this, ChatbotActivity::class.java)) }
        btnBuscar?.setOnClickListener { startActivity(Intent(this, BuscaActivity::class.java)) }
    }
}
