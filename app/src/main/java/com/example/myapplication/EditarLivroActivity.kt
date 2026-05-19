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

class EditarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var livroId: String? = null

    private lateinit var imgCapa: ImageView
    private lateinit var edtUrlCapa: EditText
    private lateinit var edtNome: EditText
    private lateinit var edtAutor: EditText
    private lateinit var edtGenero: EditText
    private lateinit var edtSinopse: EditText
    private lateinit var btnSalvar: Button
    private lateinit var txtErro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_livro)

        // Ajustando o título da tela para "Editar"
        findViewById<TextView>(R.id.txtTituloCriar).text = "Editar Livro"

        // Inicialização
        imgCapa = findViewById(R.id.imgCapaLivro)
        edtUrlCapa = findViewById(R.id.edtUrlCapa)
        edtNome = findViewById(R.id.edtNome)
        edtAutor = findViewById(R.id.edtAutor)
        edtGenero = findViewById(R.id.edtGenero)
        edtSinopse = findViewById(R.id.edtSinopse)
        btnSalvar = findViewById(R.id.btnCriar)
        btnSalvar.text = "Salvar Alterações"
        txtErro = findViewById(R.id.txtErro)

        findViewById<View>(R.id.btnVoltar).setOnClickListener { finish() }

        // 1. Pegar o ID do livro enviado pela tela anterior (ex: GerenciarLivrosActivity)
        livroId = intent.getStringExtra("LIVRO_ID")

        if (livroId != null) {
            carregarDadosDoLivro(livroId!!)
        } else {
            Toast.makeText(this, "Erro: ID do livro não encontrado.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Listener para carregar a imagem da capa quando o usuário sai do campo URL
        edtUrlCapa.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val url = edtUrlCapa.text.toString().trim()
                if (url.isNotEmpty()) {
                    carregarImagemCapa(url)
                }
            }
        }

        // 2. Lógica de update no Firebase
        btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }
    }

    private fun carregarDadosDoLivro(id: String) {
        // Busca o documento específico dentro da sua coleção "livros"
        db.collection("Livros").document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Preenche os campos do XML com o que veio do Firebase
                    edtNome.setText(document.getString("Titulo"))
                    edtAutor.setText(document.getString("Autor"))
                    edtGenero.setText(document.getString("Genero"))
                    edtSinopse.setText(document.getString("Sinopse"))

                    val urlCapa = document.getString("CapaUrl") ?: ""
                    edtUrlCapa.setText(urlCapa)
                    if (urlCapa.isNotEmpty()) {
                        carregarImagemCapa(urlCapa)
                    }
                } else {
                    Toast.makeText(this, "Livro não encontrado no banco.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                txtErro.text = "Erro ao carregar dados: ${exception.message}"
                txtErro.visibility = View.VISIBLE
            }
    }

    private fun salvarAlteracoes() {
        val titulo = edtNome.text.toString().trim()
        val autor = edtAutor.text.toString().trim()
        val genero = edtGenero.text.toString().trim()
        val sinopse = edtSinopse.text.toString().trim()
        val capaUrl = edtUrlCapa.text.toString().trim()

        // Validação simples antes de mandar pro Firebase
        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || sinopse.isEmpty()) {
            txtErro.text = "Preencha todos os campos obrigatórios."
            txtErro.visibility = View.VISIBLE
            return
        }

        txtErro.visibility = View.GONE

        // Mapeia os novos dados
        val dadosAtualizados = hashMapOf(
            "Titulo" to titulo,
            "Autor" to autor,
            "Genero" to genero,
            "Sinopse" to sinopse,
            "CapaUrl" to capaUrl
        )

        // Atualiza o documento no Firestore
        livroId?.let { id ->
            db.collection("livros").document(id)
                .update(dadosAtualizados as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Fecha a tela de edição e volta
                }
                .addOnFailureListener { exception ->
                    txtErro.text = "Erro ao atualizar: ${exception.message}"
                    txtErro.visibility = View.VISIBLE
                }
        }
    }

    private fun carregarImagemCapa(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.logo_nome) // mude para o seu placeholder se necessário
            .error(R.drawable.logo_nome)
            .into(imgCapa)
    }
}