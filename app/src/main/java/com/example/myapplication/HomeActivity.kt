package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import com.google.firebase.firestore.FirebaseFirestore

class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModels()
    private val db = FirebaseFirestore.getInstance()
    
    private lateinit var adapterAclamados: LivroAdapter
    private lateinit var adapterEducacao: LivroAdapter
    private lateinit var adapterMinhaLista: LivroAdapter
    private lateinit var adapterComedias: LivroAdapter
    private lateinit var adapterSuspense: LivroAdapter
    private lateinit var adapterFiccao: LivroAdapter
    private lateinit var adapterTerror: LivroAdapter
    private lateinit var adapterRomance: LivroAdapter
    private lateinit var adapterAventura: LivroAdapter
    private lateinit var adapterDocumentarios: LivroAdapter
    private lateinit var adapterAnimes: LivroAdapter
    private lateinit var adapterClassicos: LivroAdapter
    private lateinit var adapterFantasia: LivroAdapter

    private val listaAclamados = mutableListOf<Livro>()
    private val listaEducacao = mutableListOf<Livro>()
    private val listaMinhaLista = mutableListOf<Livro>()
    private val listaComedias = mutableListOf<Livro>()
    private val listaSuspense = mutableListOf<Livro>()
    private val listaFiccao = mutableListOf<Livro>()
    private val listaTerror = mutableListOf<Livro>()
    private val listaRomance = mutableListOf<Livro>()
    private val listaAventura = mutableListOf<Livro>()
    private val listaDocumentarios = mutableListOf<Livro>()
    private val listaAnimes = mutableListOf<Livro>()
    private val listaClassicos = mutableListOf<Livro>()
    private val listaFantasia = mutableListOf<Livro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)
        atualizarSaudacaoUsuario()

        configurarNavegacao()
        setupRecyclerViews()
        observeViewModel()
        setupInfiniteScroll()
        configurarCliquesVerTudo()
        configurarBotaoTema()

        // Firebase1
        viewModel.fetchBooks(isFirstPage = true)
        
        if (userId != null) {
            viewModel.fetchFavorites(userId)
        }
    }

    override fun onResume() {
        super.onResume()
        atualizarSaudacaoUsuario()
        // Atualiza favoritos toda vez que volta para a Home (ex: após favoritar/desfavoritar nos Detalhes)
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)
        if (userId != null) {
            viewModel.fetchFavorites(userId)
        }
    }

    private fun atualizarSaudacaoUsuario() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val txtUsuario = findViewById<TextView>(R.id.txtUsuario)
        val nomeSalvo = sharedPref.getString("USER_NAME", null)?.takeIf { it.isNotBlank() }
        val userId = sharedPref.getString("USER_ID", null)

        txtUsuario.text = getString(R.string.home_saudacao, nomeSalvo ?: "Usuário")

        if (userId.isNullOrBlank()) {
            return
        }

        db.collection("Usuários").document(userId).get()
            .addOnSuccessListener { document ->
                val nomeAtualizado = document.getString("nome")?.takeIf { it.isNotBlank() }
                    ?: return@addOnSuccessListener

                if (nomeAtualizado != nomeSalvo) {
                    sharedPref.edit().putString("USER_NAME", nomeAtualizado).apply()
                    txtUsuario.text = getString(R.string.home_saudacao, nomeAtualizado)
                }
            }
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    private fun setupRecyclerViews() {
        // Inicializa os adapters
        adapterAclamados = LivroAdapter(listaAclamados, itemLayoutRes = R.layout.item_livro_home)
        adapterEducacao = LivroAdapter(listaEducacao, itemLayoutRes = R.layout.item_livro_home)
        adapterMinhaLista = LivroAdapter(listaMinhaLista, itemLayoutRes = R.layout.item_livro_home)
        adapterComedias = LivroAdapter(listaComedias, itemLayoutRes = R.layout.item_livro_home)
        adapterSuspense = LivroAdapter(listaSuspense, itemLayoutRes = R.layout.item_livro_home)
        adapterFiccao = LivroAdapter(listaFiccao, itemLayoutRes = R.layout.item_livro_home)
        adapterTerror = LivroAdapter(listaTerror, itemLayoutRes = R.layout.item_livro_home)
        adapterRomance = LivroAdapter(listaRomance, itemLayoutRes = R.layout.item_livro_home)
        adapterAventura = LivroAdapter(listaAventura, itemLayoutRes = R.layout.item_livro_home)
        adapterDocumentarios = LivroAdapter(listaDocumentarios, itemLayoutRes = R.layout.item_livro_home)
        adapterAnimes = LivroAdapter(listaAnimes, itemLayoutRes = R.layout.item_livro_home)
        adapterClassicos = LivroAdapter(listaClassicos, itemLayoutRes = R.layout.item_livro_home)
        adapterFantasia = LivroAdapter(listaFantasia, itemLayoutRes = R.layout.item_livro_home)

        configurarRV(R.id.rvAclamados, adapterAclamados)
        configurarRV(R.id.rvEducacao, adapterEducacao)
        configurarRV(R.id.rvMinhaLista, adapterMinhaLista)
        configurarRV(R.id.rvComedias, adapterComedias)
        configurarRV(R.id.rvSuspense, adapterSuspense)
        configurarRV(R.id.rvFiccao, adapterFiccao)
        configurarRV(R.id.rvTerror, adapterTerror)
        configurarRV(R.id.rvRomance, adapterRomance)
        configurarRV(R.id.rvAventura, adapterAventura)
        configurarRV(R.id.rvDocumentarios, adapterDocumentarios)
        configurarRV(R.id.rvAnimes, adapterAnimes)
        configurarRV(R.id.rvClassicos, adapterClassicos)
        configurarRV(R.id.rvFantasia, adapterFantasia)
    }

    private fun configurarRV(id: Int, adapter: LivroAdapter) {
        val rv = findViewById<RecyclerView>(id) ?: return
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.clipToPadding = false
        rv.setPadding(16.dp(), 0, 40.dp(), 0)
        rv.overScrollMode = View.OVER_SCROLL_NEVER
        rv.adapter = adapter
        if (rv.onFlingListener == null) {
            LinearSnapHelper().attachToRecyclerView(rv)
        }
    }

    private fun configurarRecyclerViewEstatico(id: Int) {
        val rv = findViewById<RecyclerView>(id) ?: return
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.clipToPadding = false
        rv.setPadding(16.dp(), 0, 40.dp(), 0)
        rv.adapter = LivroAdapter(emptyList(), itemLayoutRes = R.layout.item_livro_home)
    }

    private fun observeViewModel() {
        val progressBar = findViewById<ProgressBar>(R.id.progressLoading)
        val txtVazio = findViewById<TextView>(R.id.txtListaVazia)

        //Firebase2
        viewModel.books.observe(this, Observer { livros ->
            if (livros.isNullOrEmpty()) {
                if (listaAclamados.isEmpty()) txtVazio?.visibility = View.VISIBLE
            } else {
                txtVazio?.visibility = View.GONE

                //Os livros vão para “Aclamados”
                listaAclamados.clear()
                listaAclamados.addAll(livros)
                adapterAclamados.notifyDataSetChanged() //recicleview

                atualizarCategoria(livros, "Educação", listaEducacao, adapterEducacao)
                // Removido atualizarCategoria manual para "Lista" pois agora vem do fetchFavorites
                atualizarCategoria(livros, "Comédia", listaComedias, adapterComedias)
                atualizarCategoria(livros, "Suspense", listaSuspense, adapterSuspense)
                atualizarCategoria(livros, "Ficção", listaFiccao, adapterFiccao)
                atualizarCategoria(livros, "Terror", listaTerror, adapterTerror)
                atualizarCategoria(livros, "Romance", listaRomance, adapterRomance)
                atualizarCategoria(livros, "Aventura", listaAventura, adapterAventura)
                atualizarCategoria(livros, "Documentário", listaDocumentarios, adapterDocumentarios)
                atualizarCategoria(livros, "Anime", listaAnimes, adapterAnimes)
                atualizarCategoria(livros, "Clássico", listaClassicos, adapterClassicos)
                atualizarCategoria(livros, "Fantasia", listaFantasia, adapterFantasia)
            }
        })

        viewModel.favorites.observe(this, Observer { favoritos ->
            listaMinhaLista.clear()
            listaMinhaLista.addAll(favoritos)
            
            val headerMinhaLista = findViewById<View>(R.id.headerMinhaLista)
            val rvMinhaLista = findViewById<View>(R.id.rvMinhaLista)
            val visibilidade = if (favoritos.isNotEmpty()) View.VISIBLE else View.GONE
            
            headerMinhaLista?.visibility = visibilidade
            rvMinhaLista?.visibility = visibilidade
            
            adapterMinhaLista.notifyDataSetChanged()
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.error.observe(this, Observer { errorMsg ->
            if (errorMsg != null) {
            }
        })
    }

    private fun atualizarCategoria(todosLivros: List<Livro>, genero: String, listaLocal: MutableList<Livro>, adapter: LivroAdapter) {
        listaLocal.clear()
        
        // Tenta filtrar por gênero (ignorando maiúsculas/minúsculas)
        // Mapeamos os nomes das categorias para os termos que buscamos no Firebase
        val termoBusca = when(genero) {
            "Educação" -> "Educação"
            "Comédia" -> "Comédia"
            "Suspense" -> "Suspense"
            "Ficção" -> "Ficção"
            "Terror" -> "Terror"
            "Romance" -> "Romance"
            "Aventura" -> "Aventura"
            "Documentário" -> "Documentário"
            "Anime" -> "Anime"
            "Clássico" -> "Clássico"
            "Fantasia" -> "Fantasia"
            else -> genero
        }

        val filtrados = todosLivros.filter { it.genero.contains(termoBusca, ignoreCase = true) }
        
        listaLocal.addAll(filtrados)
        
        // Esconde o header e o recyclerview se não houver livros para essa categoria
        val headerId = when(genero) {
            "Educação" -> R.id.headerEducacao
            "Comédia" -> R.id.headerComedias
            "Suspense" -> R.id.headerSuspense
            "Ficção" -> R.id.headerFiccao
            "Terror" -> R.id.headerTerror
            "Romance" -> R.id.headerRomance
            "Aventura" -> R.id.headerAventura
            "Documentário" -> R.id.headerDocumentarios
            "Anime" -> R.id.headerAnimes
            "Clássico" -> R.id.headerClassicos
            "Fantasia" -> R.id.headerFantasia
            else -> null
        }

        val rvId = when(genero) {
            "Educação" -> R.id.rvEducacao
            "Comédia" -> R.id.rvComedias
            "Suspense" -> R.id.rvSuspense
            "Ficção" -> R.id.rvFiccao
            "Terror" -> R.id.rvTerror
            "Romance" -> R.id.rvRomance
            "Aventura" -> R.id.rvAventura
            "Documentário" -> R.id.rvDocumentarios
            "Anime" -> R.id.rvAnimes
            "Clássico" -> R.id.rvClassicos
            "Fantasia" -> R.id.rvFantasia
            else -> null
        }

        val visibilidade = if (filtrados.isNotEmpty()) View.VISIBLE else View.GONE
        headerId?.let { findViewById<View>(it)?.visibility = visibilidade }
        rvId?.let { findViewById<View>(it)?.visibility = visibilidade }

        adapter.notifyDataSetChanged()
    }


    private fun setupInfiniteScroll() {
        val nestedScrollView = findViewById<NestedScrollView>(R.id.nestedScrollView)
        nestedScrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            // Se o usuário scrollar até o fim do conteúdo
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                viewModel.fetchBooks(isFirstPage = false)
            }
        })
    }

    private fun abrirGenero(nome: String) {
        val intent = Intent(this, PesquisaGeneroActivity::class.java)
        intent.putExtra("GENERO_NOME", nome)
        startActivity(intent)
    }

    private fun configurarCliquesVerTudo() {
        findViewById<TextView>(R.id.tvVerTudoAclamados)?.setOnClickListener { abrirGenero("Sugeridos") }
        findViewById<TextView>(R.id.tvVerTudoEducacao)?.setOnClickListener { abrirGenero("Educação") }
        findViewById<TextView>(R.id.tvVerTudo)?.setOnClickListener { abrirGenero("Minha Lista") }
        findViewById<TextView>(R.id.tvVerTudoComedias)?.setOnClickListener { abrirGenero("Comédia") }
        findViewById<TextView>(R.id.tvVerTudoSuspense)?.setOnClickListener { abrirGenero("Suspense") }
        findViewById<TextView>(R.id.tvVerTudoFiccao)?.setOnClickListener { abrirGenero("Ficção Científica") }
        findViewById<TextView>(R.id.tvVerTudoTerror)?.setOnClickListener { abrirGenero("Terror") }
        findViewById<TextView>(R.id.tvVerTudoRomance)?.setOnClickListener { abrirGenero("Romance") }
        findViewById<TextView>(R.id.tvVerTudoAventura)?.setOnClickListener { abrirGenero("Aventura") }
        findViewById<TextView>(R.id.tvVerTudoDocumentarios)?.setOnClickListener { abrirGenero("Documentário") }
        findViewById<TextView>(R.id.tvVerTudoAnimes)?.setOnClickListener { abrirGenero("Anime") }
        findViewById<TextView>(R.id.tvVerTudoClassicos)?.setOnClickListener { abrirGenero("Clássico") }
        findViewById<TextView>(R.id.tvVerTudoFantasia)?.setOnClickListener { abrirGenero("Fantasia") }
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        btnTema?.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema?.setOnClickListener {
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

        val corDestaque = "#5B7FFF".toColorInt()
        findViewById<ImageView>(R.id.iv_home)?.setColorFilter(corDestaque)
        findViewById<TextView>(R.id.tv_home)?.setTextColor(corDestaque)

        btnBiblioteca.setOnClickListener { startActivity(Intent(this, TelaBibliotecaActivity::class.java)) }
        btnChatbot.setOnClickListener { startActivity(Intent(this, ChatbotActivity::class.java)) }
        btnBuscar.setOnClickListener { startActivity(Intent(this, BuscaActivity::class.java)) }
        btnPerfil.setOnClickListener { startActivity(Intent(this, PerfilActivity::class.java)) }

        // Lupa no topo (Header)
        findViewById<ImageView>(R.id.btnBuscarTop)?.setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java))
        }
    }

}
