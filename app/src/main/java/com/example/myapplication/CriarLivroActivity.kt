package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class CriarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    // Declaração das Views
    private lateinit var imgCapa: ImageView
    private lateinit var edtUrlCapa: EditText
    private lateinit var edtNome: EditText
    private lateinit var edtAutor: EditText
    private lateinit var edtGenero: EditText
    private lateinit var edtSinopse: EditText
    private lateinit var btnCriar: Button
    private lateinit var txtErro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_livro)

        // Inicialização das Views (IDs sincronizados com o seu novo XML)
        imgCapa = findViewById(R.id.imgCapaLivro)
        edtUrlCapa = findViewById(R.id.edtUrlCapa)
        edtNome = findViewById(R.id.edtNome)
        edtAutor = findViewById(R.id.edtAutor)
        edtGenero = findViewById(R.id.edtGenero)
        edtSinopse = findViewById(R.id.edtSinopse)
        btnCriar = findViewById(R.id.btnCriar)
        txtErro = findViewById(R.id.txtErro)

        // Configuração do botão voltar
        findViewById<View>(R.id.btnVoltar).setOnClickListener {
            finish()
        }

        // Lógica para mostrar prévia da imagem quando o link for colado
        edtUrlCapa.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val url = edtUrlCapa.text.toString().trim()
                if (url.isNotEmpty()) {
                    Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.logo_nome1)
                        .error(R.drawable.logo_nome1)
                        .into(imgCapa)
                }
            }
        }

        // Lógica do botão Criar
        btnCriar.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            val autor = edtAutor.text.toString().trim()
            val genero = edtGenero.text.toString().trim()
            val sinopse = edtSinopse.text.toString().trim()
            val urlCapa = edtUrlCapa.text.toString().trim()

            if (nome.isEmpty() || autor.isEmpty() || urlCapa.isEmpty()) {
                txtErro.text = "Preencha o nome, autor e a URL da capa."
                txtErro.visibility = View.VISIBLE
            } else {
                txtErro.visibility = View.GONE
                salvarLivroNoFirebase(nome, autor, genero, sinopse, urlCapa)
            }
        }
    }

    private fun salvarLivroNoFirebase(nome: String, autor: String, genero: String, sinopse: String, capaUrl: String) {
        // Padroniza o gênero: primeira letra maiúscula, restante minúscula (ex: "terror" -> "Terror")
        val generoPadronizado = genero.trim().lowercase().replaceFirstChar { it.uppercase() }

        val novoLivro = Livro(
            titulo = nome,
            autor = autor,
            genero = generoPadronizado,
            sinopse = sinopse,
            capaUrl = capaUrl
        )

        db.collection("Livros")
            .add(novoLivro)
            .addOnSuccessListener {
                Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar no banco: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
