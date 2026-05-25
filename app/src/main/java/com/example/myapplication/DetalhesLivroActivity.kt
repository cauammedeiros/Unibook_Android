package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

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

    private var isFavoritado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_livro)

        inicializarViews()
        carregarDadosLivro()
        verificarFavorito()
        configurarBotoes()
        configurarMenuNavegacao()
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

    private fun verificarFavorito() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)
        val livroId = intent.getStringExtra("LIVRO_ID")

        if (userId != null && livroId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("Favoritos")
                .document("${userId}_${livroId}")
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        isFavoritado = true
                        atualizarBotaoFavorito()
                    }
                }
        }
    }

    private fun atualizarBotaoFavorito() {
        if (isFavoritado) {
            btnFavoritar.text = "Favoritado"
            btnFavoritar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_star_filled, 0, 0, 0)
        } else {
            btnFavoritar.text = "Favoritar"
            btnFavoritar.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_star_outline, 0, 0, 0)
        }
    }

    private fun configurarBotoes() {
        btnVoltar.setOnClickListener { finish() }

        btnBaixar.setOnClickListener {
            Toast.makeText(this, "Funcionalidade de Download", Toast.LENGTH_SHORT).show()
        }

        btnFavoritar.setOnClickListener {
            favoritarLivro()
        }

        btnAlugar.setOnClickListener {
            Toast.makeText(this, "Solicitando Empréstimo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun favoritarLivro() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)
        val livroId = intent.getStringExtra("LIVRO_ID")

        if (userId == null || livroId == null) {
            Toast.makeText(this, "Erro ao identificar usuário ou livro.", Toast.LENGTH_SHORT).show()
            return
        }

        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Favoritos").document("${userId}_${livroId}")

        if (isFavoritado) {
            docRef.delete()
                .addOnSuccessListener {
                    isFavoritado = false
                    atualizarBotaoFavorito()
                    Toast.makeText(this, "Removido dos favoritos", Toast.LENGTH_SHORT).show()
                }
        } else {
            val favorito = hashMapOf(
                "userId" to userId,
                "livroId" to livroId,
                "id" to livroId,
                "Titulo" to intent.getStringExtra("TITULO"),
                "CapaUrl" to intent.getStringExtra("CAPA_URL"),
                "Autor" to intent.getStringExtra("AUTOR"),
                "Genero" to intent.getStringExtra("GENERO"),
                "Sinopse" to intent.getStringExtra("SINOPSE"),
                "dataFavoritado" to Timestamp.now()
            )

            docRef.set(favorito)
                .addOnSuccessListener {
                    isFavoritado = true
                    atualizarBotaoFavorito()
                    Toast.makeText(this, "Adicionado aos favoritos!", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun configurarMenuNavegacao() {
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
