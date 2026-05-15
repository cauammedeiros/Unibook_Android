package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

// ... imports ...

class CriarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    // Removemos a linha do storage

    private lateinit var imgCapa: ImageView
    private lateinit var edtUrlCapa: EditText // Novo campo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_livro)

        // ... seus outros findViewByid ...
        edtUrlCapa = findViewById(R.id.edtUrlCapa)
        imgCapa = findViewById(R.id.imgCapaLivro)

        // DICA: Quando o usuário terminar de digitar a URL,
        // já podemos mostrar uma prévia na ImageView usando o Glide
        edtUrlCapa.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val url = edtUrlCapa.text.toString()
                if (url.isNotEmpty()) {
                    Glide.with(this).load(url).into(imgCapa)
                }
            }
        }

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
                // Agora chamamos direto a função de salvar, sem passar pelo Upload
                salvarLivroNoFirebase(nome, autor, genero, sinopse, urlCapa)
            }
        }
    }

    private fun salvarLivroNoFirebase(nome: String, autor: String, genero: String, sinopse: String, capaUrl: String) {
        val novoLivro = Livro(
            titulo = nome,
            autor = autor,
            genero = genero,
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
