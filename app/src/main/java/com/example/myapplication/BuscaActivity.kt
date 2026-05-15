package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class BuscaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca)

        // 1. Inicializar os componentes
        val editBusca = findViewById<EditText>(R.id.edtBusca) // ID do seu campo de texto
        val btnLupa = findViewById<ImageView>(R.id.btnLupa) // ID da imagem da lupa

// 2. Ação ao clicar na lupa
        btnLupa.setOnClickListener {
            val textoBusca = editBusca.text.toString()

            if (textoBusca.isNotEmpty()) {
                val intent = Intent(this, BuscaFiltradaActivity::class.java)
                // Passa o que foi digitado para a próxima tela
                intent.putExtra("TERMO_BUSCA", textoBusca)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Digite algo para buscar", Toast.LENGTH_SHORT).show()
            }
        }

// 3. (Opcional) Ação ao clicar no "Enter" do teclado
        editBusca.setOnEditorActionListener { _, _, _ ->
            btnLupa.performClick() // Simula o clique na lupa
            true
        }

        // Menu de Navegação
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        // Destacar tela atual (Busca)
        findViewById<ImageView>(R.id.iv_search).setColorFilter(Color.parseColor("#2196F3"))
        findViewById<TextView>(R.id.tv_search).setTextColor(Color.parseColor("#2196F3"))

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




        // Botão de Alternar Tema
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        btnTema.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema.setOnClickListener {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

        // Escolher Gênero
        val btnRomances = findViewById<TextView>(R.id.genRomance)

        btnRomances.setOnClickListener {
            val intent = Intent(this, PesquisaGeneroActivity::class.java)
            startActivity(intent)
        }
}
}