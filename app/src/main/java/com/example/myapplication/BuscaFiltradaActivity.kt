package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class BuscaFiltradaActivity : BaseActivity() {

    private lateinit var rvBuscaFiltrada: RecyclerView
    private lateinit var btnVoltar: ImageButton
    private lateinit var txtNomeBusca: TextView
    private lateinit var progressLoading: ProgressBar
    private val db = FirebaseFirestore.getInstance()
    private val listaResultados = mutableListOf<Livro>()
    private lateinit var adapter: BuscaFiltradaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca_filtrada)

        rvBuscaFiltrada = findViewById(R.id.rvBuscaFiltrada)
        btnVoltar = findViewById(R.id.btnVoltar)
        txtNomeBusca = findViewById(R.id.txtNomeBusca)
        progressLoading = findViewById(R.id.progressLoadingBusca)

        val termo = intent.getStringExtra("TERMO_BUSCA") ?: ""
        txtNomeBusca.text = termo

        setupRecyclerView()
        
        if (termo.isNotEmpty()) {
            realizarBusca(termo)
        }

        btnVoltar.setOnClickListener { finish() }
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        adapter = BuscaFiltradaAdapter(listaResultados)
        rvBuscaFiltrada.adapter = adapter
        rvBuscaFiltrada.layoutManager = LinearLayoutManager(this)
    }

    private fun realizarBusca(termo: String) {
        progressLoading.visibility = View.VISIBLE
        
        // Buscamos todos os livros e filtramos localmente para permitir busca parcial/case-insensitive
        // (Firestore não suporta busca parcial nativa sem serviços externos)
        db.collection("Livros")
            .get()
            .addOnSuccessListener { documents ->
                listaResultados.clear()
                for (doc in documents) {
                    val livro = doc.toObject(Livro::class.java)
                    livro.id = doc.id
                    
                    // Filtra por título ou autor (case-insensitive)
                    if (livro.titulo.contains(termo, ignoreCase = true) || 
                        livro.autor.contains(termo, ignoreCase = true)) {
                        listaResultados.add(livro)
                    }
                }
                adapter.notifyDataSetChanged()
                progressLoading.visibility = View.GONE
                
                if (listaResultados.isEmpty()) {
                    Toast.makeText(this, "Nenhum resultado encontrado para \"$termo\"", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                progressLoading.visibility = View.GONE
                Toast.makeText(this, "Erro na busca: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupBottomNavigation() {
        findViewById<LinearLayout>(R.id.nav_home)?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.nav_library)?.setOnClickListener {
            val intent = Intent(this, TelaBibliotecaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.nav_chatbot)?.setOnClickListener {
            val intent = Intent(this, ChatbotActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.nav_search)?.setOnClickListener {
            val intent = Intent(this, BuscaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.nav_profile)?.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }
    }
}