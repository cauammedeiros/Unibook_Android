package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetalhesLivroActivity : AppCompatActivity() {

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

        inicializarViews()
        carregarDadosLivro()
        configurarBotoes()
        configurarMenuNavegacao()
    }

    private fun carregarDadosLivro() {
        val titulo = intent.getStringExtra("TITULO")
        val autor = intent.getStringExtra("AUTOR")
        val genero = intent.getStringExtra("GENERO")
        val sinopse = intent.getStringExtra("SINOPSE")
        val capaUrl = intent.getStringExtra("CAPA_URL")

        txtNomeLivro.text = titulo ?: "Título Indisponível"
        txtAutor.text = autor ?: "Autor Desconhecido"
        txtGenero.text = genero ?: "Gênero não informado"
        txtSinopse.text = sinopse ?: "Sem sinopse disponível."

        Glide.with(this)
            .load(capaUrl)
            .placeholder(R.drawable.logo_nome1)
            .into(imgCapaLivro)
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
        btnVoltar.setOnClickListener { finish() }

        btnBaixar.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de Download", Toast.LENGTH_SHORT).show()
        }

        btnFavoritar.setOnClickListener {
            Toast.makeText(this, "Livro favoritado!", Toast.LENGTH_SHORT).show()
        }

        btnAlugar.setOnClickListener {
            Toast.makeText(this, "Solicitando Empréstimo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configurarMenuNavegacao() {
        // IDs sincronizados com layout_bottom_nav.xml
        findViewById<LinearLayout>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.nav_library)?.setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.nav_chatbot)?.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.nav_search)?.setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
    }
}
