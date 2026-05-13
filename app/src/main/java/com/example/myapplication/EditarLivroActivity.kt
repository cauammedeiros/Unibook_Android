package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var livroId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_livro)

        livroId = intent.getStringExtra("LIVRO_ID")

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarLivro)
        val btnExcluir = findViewById<Button>(R.id.btnExcluirLivro)

        // Campos de edição
        val edtNome = findViewById<EditText>(R.id.edtNomeLivroEditar)
        val edtAutor = findViewById<EditText>(R.id.edtAutorLivroEditar)
        val edtGenero = findViewById<EditText>(R.id.edtGeneroLivroEditar)
        val edtSinopse = findViewById<EditText>(R.id.edtSinopseEditar)

        btnVoltar.setOnClickListener { finish() }

        // Botão Salvar (Alterar no Firebase)
        btnSalvar.setOnClickListener {
            val novoNome = edtNome.text.toString()
            val novoAutor = edtAutor.text.toString()
            val novoGenero = edtGenero.text.toString()
            val novaSinopse = edtSinopse.text.toString()

            if (livroId != null) {
                atualizarLivroNoFirebase(novoNome, novoAutor, novoGenero, novaSinopse)
            }
        }

        // Botão Excluir
        btnExcluir.setOnClickListener {
            showConfirmacaoExclusao()
        }
    }

    private fun atualizarLivroNoFirebase(nome: String, autor: String, genero: String, sinopse: String) {
        val dadosAtualizados = mapOf(
            "Titulo" to nome,
            "Autor" to autor,
            "Genero" to genero,
            "Sinopse" to sinopse
        )

        db.collection("Livros").document(livroId!!)
            .update(dadosAtualizados)
            .addOnSuccessListener {
                Toast.makeText(this, "Livro atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao atualizar: ${e.message}", Toast.LENGTH_SHORT).show()
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