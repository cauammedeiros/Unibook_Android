package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class BuscaFiltradaActivity : BaseActivity() {

    private lateinit var rvBuscaFiltrada: RecyclerView
    private lateinit var btnVoltar: ImageButton
    private lateinit var txtNomeBusca: TextView
    private val db = FirebaseFirestore.getInstance()
    private val listaResultados = mutableListOf<Livro>()
    private lateinit var adapter: BuscaFiltradaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca_filtrada)

        // 1. Inicialização dos componentes
        rvBuscaFiltrada = findViewById(R.id.rvBuscaFiltrada)
        btnVoltar = findViewById(R.id.btnVoltar)
        txtNomeBusca = findViewById(R.id.txtNomeBusca)

        // 2. Recupera o termo da busca e limpa espaços
        val termoBusca = intent.getStringExtra("TERMO_BUSCA")?.trim() ?: ""
        txtNomeBusca.text = termoBusca

        // 3. Configura a lista (RecyclerView)
        adapter = BuscaFiltradaAdapter(listaResultados)
        rvBuscaFiltrada.adapter = adapter
        rvBuscaFiltrada.layoutManager = LinearLayoutManager(this)

        // 4. Executa a busca
        if (termoBusca.isNotEmpty()) {
            buscarLivrosNoFirestore(termoBusca)
        }

        btnVoltar.setOnClickListener { finish() }
        configurarBotaoTema()
        setupBottomNavigation()
    }

    private fun buscarLivrosNoFirestore(termo: String) {
        // Tenta buscar com a primeira letra maiúscula (Padrão mais comum)
        val termoCapitalizado = termo.lowercase().replaceFirstChar { it.uppercase() }
        
        db.collection("Livros")
            .whereGreaterThanOrEqualTo("Titulo", termoCapitalizado)
            .whereLessThanOrEqualTo("Titulo", termoCapitalizado + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                listaResultados.clear()
                for (document in documents) {
                    val livro = document.toObject(Livro::class.java)
                    livro.id = document.id
                    listaResultados.add(livro)
                }

                if (listaResultados.isEmpty() && termo != termoCapitalizado) {
                     db.collection("Livros")
                        .whereGreaterThanOrEqualTo("Titulo", termo)
                        .whereLessThanOrEqualTo("Titulo", termo + "\uf8ff")
                        .get()
                        .addOnSuccessListener { docs ->
                            for (doc in docs) {
                                val livro = doc.toObject(Livro::class.java)
                                livro.id = doc.id
                                listaResultados.add(livro)
                            }
                            adapter.notifyDataSetChanged()
                            verificarResultados(termo)
                        }
                } else {
                    adapter.notifyDataSetChanged()
                    verificarResultados(termo)
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro na busca: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun verificarResultados(termo: String) {
        if (listaResultados.isEmpty()) {
            Toast.makeText(this, "Nenhum livro encontrado para: $termo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBottomNavigation() {
        findViewById<LinearLayout>(R.id.nav_home)?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_library)?.setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_chatbot)?.setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_search)?.setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_profile)?.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        btnTema?.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema?.setOnClickListener {
            val modo = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.setDefaultNightMode(modo)
        }
    }
}
