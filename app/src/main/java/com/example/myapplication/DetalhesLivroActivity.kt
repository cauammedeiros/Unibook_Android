package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalhesLivroActivity : AppCompatActivity() {

    // Declaração das views
    private lateinit var btnVoltar: ImageButton
    private lateinit var txtNomeLivro: TextView
    private lateinit var imgCapaLivro: ImageView
    private lateinit var btnBaixar: Button
    private lateinit var btnFavoritar: Button
    private lateinit var btnAlugar: Button
    private lateinit var txtGenero: TextView
    private lateinit var txtAutor: TextView
    private lateinit var txtSinopse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_livro)

        // Inicializar as views
        inicializarViews()

        // Configurar os cliques dos botões
        configurarBotoes()
    }

    private fun inicializarViews() {
        btnVoltar = findViewById(R.id.btnVoltar)
        txtNomeLivro = findViewById(R.id.txtNomeLivro)
        imgCapaLivro = findViewById(R.id.imgCapaLivro)
        btnBaixar = findViewById(R.id.btnBaixar)
        btnFavoritar = findViewById(R.id.btnFavoritar)
        btnAlugar = findViewById(R.id.btnAlugar)
        txtGenero = findViewById(R.id.txtGenero)
        txtAutor = findViewById(R.id.txtAutor)
        txtSinopse = findViewById(R.id.txtSinopse)
    }

    private fun configurarBotoes() {
        // Botão Voltar
        btnVoltar.setOnClickListener {
            finish() // Fecha a tela e volta para a anterior
        }

        // Botão Baixar
        btnBaixar.setOnClickListener {
            Toast.makeText(this, "Baixando livro...", Toast.LENGTH_SHORT).show()
            // Aqui você adiciona a lógica para baixar o livro
        }

        // Botão Favoritar
        btnFavoritar.setOnClickListener {
            Toast.makeText(this, "Livro favoritado!", Toast.LENGTH_SHORT).show()
            // Aqui você adiciona a lógica para favoritar/desfavoritar
        }

        // Botão Alugar
        btnAlugar.setOnClickListener {
            Toast.makeText(this, "Alugando livro...", Toast.LENGTH_SHORT).show()
            // Aqui você adiciona a lógica para alugar/emprestar
        }

        // Bottom Navigation - Início
        findViewById<android.view.View>(R.id.navInicio).setOnClickListener {
            Toast.makeText(this, "Ir para Início", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation - Biblioteca
        findViewById<android.view.View>(R.id.navBiblioteca).setOnClickListener {
            Toast.makeText(this, "Ir para Biblioteca", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation - CHATBOT
        findViewById<android.view.View>(R.id.navChatbot).setOnClickListener {
            Toast.makeText(this, "Ir para CHATBOT", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation - Buscar
        findViewById<android.view.View>(R.id.navBuscar).setOnClickListener {
            Toast.makeText(this, "Ir para Buscar", Toast.LENGTH_SHORT).show()
        }

        // Bottom Navigation - Perfil
        findViewById<android.view.View>(R.id.navPerfil).setOnClickListener {
            Toast.makeText(this, "Ir para Perfil", Toast.LENGTH_SHORT).show()
        }
    }
}
