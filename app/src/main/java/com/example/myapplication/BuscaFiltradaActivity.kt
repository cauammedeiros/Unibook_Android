package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BuscaFiltradaActivity : AppCompatActivity() {

    private lateinit var rvBuscaFiltrada: RecyclerView
    private lateinit var btnVoltar: ImageButton
    private lateinit var txtNomeBusca: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca_filtrada)

        // 1. Inicialização dos componentes
        rvBuscaFiltrada = findViewById(R.id.rvBuscaFiltrada)
        btnVoltar = findViewById(R.id.btnVoltar)
        txtNomeBusca = findViewById(R.id.txtNomeBusca)

        // 2. Recupera o termo da busca e mantém o título (que você gostou)
        val termo = intent.getStringExtra("TERMO_BUSCA")
        if (termo != null) {
            txtNomeBusca.text = termo
        }

        // 3. Configura a lista (RecyclerView) com dados de exemplo para aparecer na tela
        setupRecyclerView()

        // 4. Configura o botão voltar
        btnVoltar.setOnClickListener {
            finish()
        }

        // 5. Configura a navegação inferior
        setupBottomNavigation()
    }

    private fun setupRecyclerView() {
        // Criamos uma lista fictícia para que o layout 'item_livro_busca_filtrada' apareça
        val listaExemplo = listOf("Livro 1", "Livro 2", "Livro 3")
        
        val adapter = BuscaFiltradaAdapter(listaExemplo)
        rvBuscaFiltrada.adapter = adapter
        rvBuscaFiltrada.layoutManager = LinearLayoutManager(this)
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