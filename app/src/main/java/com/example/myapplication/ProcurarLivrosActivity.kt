package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.copy
import com.google.firebase.firestore.FirebaseFirestore

class ProcurarLivrosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var rvLivros: RecyclerView
    private lateinit var txtErro: TextView // Caso queira mostrar mensagem de erro na tela

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurar_livros)

        findViewById<ImageButton>(R.id.btnVoltar).setOnClickListener { finish() }

        rvLivros = findViewById(R.id.rvLivros)
        rvLivros.layoutManager = GridLayoutManager(this, 3)

        // Se você tiver um TextView de erro no layout, inicialize aqui (opcional)
        // txtErro = findViewById(R.id.txtErroProcurar)

        // Inicia a escuta em tempo real
        escutarLivrosNoFirestore()
    }

    private fun escutarLivrosNoFirestore() {
        db.collection("Livros")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val listaLivros = mutableListOf<Livro>()

                    for (document in snapshot.documents) {
                        // Converte o documento do Firebase diretamente para a sua classe Livro
                        val livro = document.toObject(Livro::class.java)
                        if (livro != null) {
                            // Injeta o ID do documento do Firebase no objeto livro
                            // (Garante que o 'livro.id' que colocamos no Adapter funcione perfeitamente)
                            val livroComId = livro.copy(id = document.id)
                            listaLivros.add(livroComId)
                        }
                    }

                    // Atualiza o RecyclerView com a lista real do banco (isAdmin = true)
                    rvLivros.adapter = LivroAdapter(listaLivros, isAdmin = true)
                }
            }
    }
}