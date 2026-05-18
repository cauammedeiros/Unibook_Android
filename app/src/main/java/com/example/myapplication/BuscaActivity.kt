package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.toColorInt

class BuscaActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca)

        // 1. Inicializar os componentes
        val editBusca = findViewById<EditText>(R.id.edtBusca) // ID do seu campo de texto
        val btnLupa = findViewById<ImageView>(R.id.btnLupa) // ID da imagem da lupa

// 2. Ação ao clicar na lupa
        btnLupa.setOnClickListener {
            val textoBusca = editBusca.text.toString()

            if (textoBusca.isNotEmpty()) {
                val intent = Intent(this, BuscaFiltradaActivity::class.java)
                // Passa o que foi digitado para a próxima tela
                intent.putExtra("TERMO_BUSCA", textoBusca)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.err_busca_vazia), Toast.LENGTH_SHORT).show()
            }
        }

// 3. (Opcional) Ação ao clicar no "Enter" do teclado
        editBusca.setOnEditorActionListener { _, _, _ ->
            btnLupa.performClick() // Simula o clique na lupa
            true
        }

        // Menu de Navegação
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        // Destacar tela atual (Busca)
        val corDestaque = "#2196F3".toColorInt()
        findViewById<ImageView>(R.id.iv_search)?.setColorFilter(corDestaque)
        findViewById<TextView>(R.id.tv_search)?.setTextColor(corDestaque)

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




        // Botão de Alternar Tema
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

        // --- Lógica de Gêneros e Coleções ---

        val btnVerMais = findViewById<TextView>(R.id.btnVerMais)
        val gridGeneros = findViewById<android.widget.GridLayout>(R.id.gridGeneros)
        
        val genTerror = findViewById<TextView>(R.id.genTerror)
        val genInfantis = findViewById<TextView>(R.id.genInfantis)
        val genSuspense = findViewById<TextView>(R.id.genSuspense)
        val genRomance = findViewById<TextView>(R.id.genRomance)
        val genFiccao = findViewById<TextView>(R.id.genFiccao)
        val genJovens = findViewById<TextView>(R.id.genJovens)

        var expandido = false
        btnVerMais.setOnClickListener {
            expandido = !expandido
            val visibility = if (expandido) android.view.View.VISIBLE else android.view.View.GONE
            
            genTerror.visibility = visibility
            genInfantis.visibility = visibility
            genSuspense.visibility = visibility
            genRomance.visibility = visibility
            genFiccao.visibility = visibility
            genJovens.visibility = visibility

            btnVerMais.text = if (expandido) "Ver menos ⌃" else "Ver mais ⌵"
        }

        // Função auxiliar para abrir gênero
        fun abrirGenero(nome: String) {
            val intent = Intent(this, PesquisaGeneroActivity::class.java)
            intent.putExtra("GENERO_NOME", nome)
            startActivity(intent)
        }

        // Cliques nas Coleções (Conforme seu pedido)
        findViewById<android.view.View>(R.id.itemAclamados).setOnClickListener { 
            abrirGenero(getString(R.string.home_cat_aclamados)) 
        }
        findViewById<android.view.View>(R.id.itemDocumentarios).setOnClickListener { 
            abrirGenero(getString(R.string.home_cat_documentarios)) 
        }
        findViewById<android.view.View>(R.id.itemEducacao).setOnClickListener { 
            abrirGenero(getString(R.string.home_cat_educacao)) 
        }

        // Cliques nos Gêneros da Grade (Exemplos)
        genRomance.setOnClickListener { abrirGenero(getString(R.string.home_cat_romance)) }
        genTerror.setOnClickListener { abrirGenero("Terror") }
        genInfantis.setOnClickListener { abrirGenero("Infantis") }
        genSuspense.setOnClickListener { abrirGenero("Suspense") }
        genFiccao.setOnClickListener { abrirGenero("Ficção Científica") }
        genJovens.setOnClickListener { abrirGenero("Jovens Adultos") }

        findViewById<TextView>(R.id.genAcao).setOnClickListener { abrirGenero("Ação") }
        findViewById<TextView>(R.id.genAnime).setOnClickListener { abrirGenero("Anime") }
        findViewById<TextView>(R.id.genComedia).setOnClickListener { abrirGenero("Comédia") }
        findViewById<TextView>(R.id.genDocumentarios).setOnClickListener { abrirGenero("Documentário") }
        findViewById<TextView>(R.id.genDrama).setOnClickListener { abrirGenero("Drama") }
        findViewById<TextView>(R.id.genFantasia).setOnClickListener { abrirGenero("Fantasia") }
    }
}