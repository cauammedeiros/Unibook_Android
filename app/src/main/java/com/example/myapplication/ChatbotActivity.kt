package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import com.example.myapplication.BuildConfig

class ChatbotActivity : AppCompatActivity() {

    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<Message>()
    private lateinit var recyclerViewChat: RecyclerView
    private val db = FirebaseFirestore.getInstance()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-3.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY,
        systemInstruction = content {
            text("Você é um assistente virtual do Unibook. Sua função é ajudar os alunos da Unifor com informações sobre livros. " +
                 "Sempre que o usuário perguntar sobre um livro ou sobre recomendar um livro de uma determinada área, gênero ou livros aclamados pela crítica, responda nesse formato: \n" +
                 "Inicie com uma mensagem de boas vinda bem calorosa dizendo que é o chatbot do Unibook e que está a disposição para ajudar em relação a livros " +
                    "Nome: [Nome do Livro]\n" +
                 "Autor: [Autor]\n" +
                 "Gênero: [Gênero]\n" +
                 "Sinopse: [Breve resumo do livro]\n\n" +
                 "Se o usuário perguntar sobre qualquer assunto que NÃO seja livros ou literatura, responda exatamente: " +
                 "'Desculpe, sou o assistente do Unibook e só posso responder perguntas relacionadas a livros.'")
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatbot)

        val mainLayout = findViewById<LinearLayout>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerViewChat = findViewById(R.id.recyclerViewChat)

        if (savedInstanceState != null) {
            val savedMessages = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelableArrayList("messages_list", Message::class.java)
            } else {
                @Suppress("DEPRECATION")
                savedInstanceState.getParcelableArrayList("messages_list")
            }
            savedMessages?.let {
                messages.clear()
                messages.addAll(it)
            }
        }

        adapter = MessageAdapter(messages)
        recyclerViewChat.adapter = adapter
        recyclerViewChat.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
        }

        val edtPergunta = findViewById<EditText>(R.id.editPergunta)
        val btnEnviar = findViewById<ImageButton>(R.id.btnEnviar)

        btnEnviar.setOnClickListener {
            val texto = edtPergunta.text.toString().trim()
            if (texto.isNotEmpty()) {
                processarMensagem(texto)
                edtPergunta.text.clear()
            }
        }

        configurarMenuNavegacao()
        configurarBotoesTopo()
    }

    private fun processarMensagem(texto: String) {
        adicionarMensagem(Message(texto, true))

        lifecycleScope.launch {
            try {
                if (BuildConfig.GEMINI_API_KEY.isEmpty() || BuildConfig.GEMINI_API_KEY == "UNSPECIFIED") {
                    adicionarMensagem(Message("Erro: Chave de API não configurada.", false))
                    return@launch
                }

                val response = generativeModel.generateContent(texto)
                val respostaBot = response.text ?: "Desculpe, não consegui processar sua pergunta."
                
                // Tenta extrair o nome do livro para vincular o botão "Ver Detalhes"
                vincularLivroEResponder(respostaBot)

            } catch (e: Exception) {
                Log.e("ChatbotError", "Falha na chamada do Gemini: ", e)
                val erroMsg = "Desculpe, tive um problema técnico. Tente novamente."
                adicionarMensagem(Message(erroMsg, false))
            }
        }
    }

    private fun vincularLivroEResponder(respostaBot: String) {
        // Regex para capturar o que vem depois de "Nome: "
        val regex = Regex("Nome:\\s*([^\\n\\r]*)", RegexOption.IGNORE_CASE)
        val match = regex.find(respostaBot)
        val nomeLivro = match?.groupValues?.get(1)?.trim()

        if (nomeLivro != null && nomeLivro.length > 2) {
            // Busca o livro no Firestore para pegar o ID e dados completos
            db.collection("Livros")
                .whereGreaterThanOrEqualTo("Titulo", nomeLivro)
                .limit(1)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        val doc = documents.documents[0]
                        val livro = doc.toObject(Livro::class.java)
                        // Verifica se o título é realmente parecido (simulando um fuzzy match básico)
                        if (livro != null && livro.titulo.contains(nomeLivro, ignoreCase = true)) {
                            val msg = Message(
                                text = respostaBot,
                                isUser = false,
                                livroId = doc.id,
                                livroTitulo = livro.titulo,
                                livroAutor = livro.autor,
                                livroGenero = livro.genero,
                                livroSinopse = livro.sinopse,
                                livroCapaUrl = livro.capaUrl
                            )
                            adicionarMensagem(msg)
                            return@addOnSuccessListener
                        }
                    }
                    adicionarMensagem(Message(respostaBot, false))
                }
                .addOnFailureListener {
                    adicionarMensagem(Message(respostaBot, false))
                }
        } else {
            adicionarMensagem(Message(respostaBot, false))
        }
    }

    private fun adicionarMensagem(message: Message) {
        messages.add(message)
        adapter.notifyItemInserted(messages.size - 1)
        recyclerViewChat.scrollToPosition(messages.size - 1)
    }

    private fun configurarMenuNavegacao() {
        val btnInicio = findViewById<LinearLayout>(R.id.nav_home)
        val btnBiblioteca = findViewById<LinearLayout>(R.id.nav_library)
        val btnChatbot = findViewById<LinearLayout>(R.id.nav_chatbot)
        val btnBuscar = findViewById<LinearLayout>(R.id.nav_search)
        val btnPerfil = findViewById<LinearLayout>(R.id.nav_profile)

        val corDestaque = "#2196F3".toColorInt()
        findViewById<ImageView>(R.id.iv_chatbot)?.setColorFilter(corDestaque)
        findViewById<TextView>(R.id.tv_chatbot)?.setTextColor(corDestaque)

        btnInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        btnBiblioteca.setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        btnChatbot.setOnClickListener {
            // Já estamos nesta atividade
        }
        btnBuscar.setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        btnPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
    }

    private fun configurarBotoesTopo() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK == android.content.res.Configuration.UI_MODE_NIGHT_YES
        btnTema.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema.setOnClickListener {
            val novoModo = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            AppCompatDelegate.setDefaultNightMode(novoModo)
        }

        findViewById<ImageView>(R.id.btnBuscarTop).setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("messages_list", ArrayList(messages))
    }
}
