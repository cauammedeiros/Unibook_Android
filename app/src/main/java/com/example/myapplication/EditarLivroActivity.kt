package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.bumptech.glide.Glide

class EditarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var livroId: String? = null

    // Referências para os novos campos de imagem
    private lateinit var imgCapa: ImageView
    private lateinit var edtUrlCapa: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_livro)

        livroId = intent.getStringExtra("LIVRO_ID")

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarLivro)
        val btnExcluir = findViewById<Button>(R.id.btnExcluirLivro)

        val edtNome = findViewById<EditText>(R.id.edtNomeLivroEditar)
        val edtAutor = findViewById<EditText>(R.id.edtAutorLivroEditar)
        val edtGenero = findViewById<EditText>(R.id.edtGeneroLivroEditar)
        val edtSinopse = findViewById<EditText>(R.id.edtSinopseEditar)

        // Inicializando os novos campos
        edtUrlCapa = findViewById(R.id.edtUrlCapaEditar)
        imgCapa = findViewById(R.id.imgCapaEditar)

        if (livroId != null) {
            carregarDadosDoLivro(edtNome, edtAutor, edtGenero, edtSinopse)
        }

        btnVoltar.setOnClickListener { finish() }

        // Preview da imagem ao perder o foco do campo de URL
        edtUrlCapa.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val url = edtUrlCapa.text.toString().trim()
                if (url.isNotEmpty()) {
                    Glide.with(this).load(url).into(imgCapa)
                }
            }
        }

        btnSalvar.setOnClickListener {
            val novoNome = edtNome.text.toString().trim()
            val novoAutor = edtAutor.text.toString().trim()
            val novoGenero = edtGenero.text.toString().trim()
            val novaSinopse = edtSinopse.text.toString().trim()
            val novaUrl = edtUrlCapa.text.toString().trim()

            if (novoNome.isEmpty() || novoAutor.isEmpty() || novaUrl.isEmpty()) {
                Toast.makeText(this, "Nome, Autor e URL são obrigatórios", Toast.LENGTH_SHORT).show()
            } else if (livroId != null) {
                atualizarLivroNoFirebase(novoNome, novoAutor, novoGenero, novaSinopse, novaUrl)
            }
        }

        btnExcluir.setOnClickListener { showConfirmacaoExclusao() }
    }

    private fun carregarDadosDoLivro(edtNome: EditText, edtAutor: EditText, edtGenero: EditText, edtSinopse: EditText) {
        db.collection("Livros").document(livroId!!)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Preenche os textos
                    edtNome.setText(document.getString("titulo"))
                    edtAutor.setText(document.getString("autor"))
                    edtGenero.setText(document.getString("genero"))
                    edtSinopse.setText(document.getString("sinopse"))

                    // Preenche a URL e carrega a imagem no ImageView
                    val url = document.getString("capaUrl")
                    edtUrlCapa.setText(url)
                    if (!url.isNullOrEmpty()) {
                        Glide.with(this).load(url).into(imgCapa)
                    }
                }
            }
    }

    private fun atualizarLivroNoFirebase(nome: String, autor: String, genero: String, sinopse: String, url: String) {
        val dadosAtualizados = mapOf(
            "titulo" to nome,
            "autor" to autor,
            "genero" to genero,
            "sinopse" to sinopse,
            "capaUrl" to url
        )

        db.collection("Livros").document(livroId!!)
            .update(dadosAtualizados)
            .addOnSuccessListener {
                Toast.makeText(this, "Livro atualizado!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showConfirmacaoExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Exclusão")
            .setMessage("Tem certeza que deseja excluir este livro?")
            .setPositiveButton("Excluir") { _, _ ->
                excluirLivroNoFirebase()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun excluirLivroNoFirebase() {
        livroId?.let { id ->
            db.collection("Livros").document(id)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Livro excluído!", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}