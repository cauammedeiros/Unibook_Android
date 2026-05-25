package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class TelaBibliotecaActivity : BaseActivity() {

    private lateinit var rvLivros: RecyclerView
    private lateinit var adapter: LivroAdapter
    private val listaFavoritos = mutableListOf<Livro>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_biblioteca)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configurarNavegacao()
        setupRecyclerView()
        configurarBotaoTema()
        carregarFavoritos()
    }

    private fun setupRecyclerView() {
        rvLivros = findViewById(R.id.rvLivros)
        adapter = LivroAdapter(listaFavoritos)
        
        // Grid de 3 colunas para os favoritos
        rvLivros.layoutManager = GridLayoutManager(this, 3)
        rvLivros.adapter = adapter
    }

    private fun carregarFavoritos() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)

        if (userId == null) {
            Toast.makeText(this, "Faça login para ver seus favoritos", Toast.LENGTH_SHORT).show()
            return
        }

        // Busca na coleção "Favoritos" apenas os documentos do usuário logado
        db.collection("Favoritos")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                listaFavoritos.clear()
                for (doc in documents) {
                    val livro = doc.toObject(Livro::class.java)
                    listaFavoritos.add(livro)
                }
                
                if (listaFavoritos.isEmpty()) {
                    // Opcional: mostrar uma mensagem de "Nenhum favorito"
                }
                
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar favoritos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        btnTema.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema.setOnClickListener {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun configurarNavegacao() {
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        val corDestaque = "#2196F3".toColorInt()
        findViewById<ImageView>(R.id.iv_library)?.setColorFilter(corDestaque)
        findViewById<TextView>(R.id.tv_library)?.setTextColor(corDestaque)

        btnInicio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
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
        
        findViewById<ImageView>(R.id.btnBuscarTop)?.setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java))
        }
    }
}