package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class CriarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_livro)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val edtNome = findViewById<EditText>(R.id.edtNomeLivro)
        val edtAutor = findViewById<EditText>(R.id.edtAutorLivro)
        val edtGenero = findViewById<EditText>(R.id.edtGeneroLivro)
        val txtErro = findViewById<TextView>(R.id.txtErroCriarLivro)
        val btnCriar = findViewById<Button>(R.id.btnCriarLivro)

        btnVoltar.setOnClickListener { finish() }

        btnCriar.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            val autor = edtAutor.text.toString().trim()
            val genero = edtGenero.text.toString().trim()

            if (nome.isEmpty() || autor.isEmpty() || genero.isEmpty()) {
                txtErro.visibility = View.VISIBLE
            } else {
                txtErro.visibility = View.GONE
                salvarLivroNoFirebase(nome, autor, genero)
            }
        }
    }

    private fun salvarLivroNoFirebase(nome: String, autor: String, genero: String) {
        val novoLivro = Livro(
            titulo = nome,
            autor = autor,
            genero = genero
        )


        db.collection("Livros")
            .add(novoLivro)
            .addOnSuccessListener {
                Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}