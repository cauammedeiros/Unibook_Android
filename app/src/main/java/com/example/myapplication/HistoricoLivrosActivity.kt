package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class HistoricoLivrosActivity : AppCompatActivity() {

    private lateinit var recyclerHistorico: RecyclerView
    private lateinit var adapter: HistoricoAdapter
    private val listaHistorico = mutableListOf<Historico>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico_livros)

        recyclerHistorico = findViewById(R.id.recyclerHistorico)
        recyclerHistorico.layoutManager = LinearLayoutManager(this)
        adapter = HistoricoAdapter(listaHistorico)
        recyclerHistorico.adapter = adapter

        carregarHistorico()

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        // Menu de Navegação
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        btnInicio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBibliotecaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnChatbot.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnBuscar.setOnClickListener {
            val intent = Intent(this, BuscaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        btnPerfil.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
    }
//
    private fun carregarHistorico() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)

        if (userId == null) {
            return
        }

        val db = FirebaseFirestore.getInstance()
        db.collection("Historico")
            .whereEqualTo("userId", userId)
            .orderBy("data", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                listaHistorico.clear()
                for (doc in value!!) {
                    val item = doc.toObject(Historico::class.java)
                    item.id = doc.id
                    listaHistorico.add(item)
                }

                // Adiciona um exemplo de livro atrasado ao final para demonstração do fluxo de multa
                listaHistorico.add(
                    Historico(
                        id = "exemplo_atraso",
                        titulo = "Dom Casmurro (Exemplo Atraso)",
                        autor = "Machado de Assis",
                        tipoAcao = "Empréstimo",
                        status = "Atrasado"
                    )
                )

                adapter.notifyDataSetChanged()
            }
    }
}
