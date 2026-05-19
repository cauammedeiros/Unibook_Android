package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PesquisaGeneroActivity : AppCompatActivity() {

    private lateinit var rvLivrosGenero: RecyclerView
    private lateinit var btnVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesquisa_genero)

        rvLivrosGenero = findViewById(R.id.rvLivrosGenero)
        btnVoltar = findViewById(R.id.btnVoltar)

        btnVoltar.setOnClickListener { finish() }

        setupRecyclerView()

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

    private fun setupRecyclerView() {
        val titulos = listOf(
            "O Senhor dos Anéis", "1984", "Dom Casmurro", "O Pequeno Príncipe", "Harry Potter",
            "O Alquimista", "A Menina que Roubava Livros", "O Código Da Vinci", "Orgulho e Preconceito",
            "O Hobbit", "Cem Anos de Solidão", "A Culpa é das Estrelas", "Diário de um Banana",
            "A Guerra dos Tronos", "O Morro dos Ventos Uivantes", "A Revolução dos Bichos",
            "Crime e Castigo", "O Retrato de Dorian Gray", "O Sol é para Todos", "Ensaio sobre a Cegueira",
            "O Caçador de Pipas", "Admirável Mundo Novo", "Memórias Póstumas", "Grande Sertão: Veredas",
            "O Nome da Rosa", "A Metamorfose", "O Estrangeiro", "Os Miseráveis", "Anna Karenina",
            "Guerra e Paz", "Sherlock Holmes", "Drácula", "Frankenstein", "Moby Dick", "O Processo",
            "Ulisses", "A Divina Comédia", "Ilíada", "Odisseia", "Fausto", "Dom Quixote", "Hamlet",
            "Macbeth", "O Rei Lear", "A Tempestade", "Romeu e Julieta", "Otelo", "A Megera Domada",
            "O Mercador de Veneza", "Sonho de uma Noite de Verão", "Muito Barulho por Nada",
            "As Alegres Comadres", "Medida por Medida", "Noite de Reis", "Conto de Inverno",
            "Cimbelino", "Péricles", "Ricardo III", "Henrique V", "Júlio César", "Antônio e Cleópatra",
            "Coriolano", "Tito Andrônico", "Timão de Atenas", "A Comédia dos Erros", "Trabalhos de Amor Perdidos",
            "Tudo Bem quando Termina Bem", "A Divina Comédia", "Paraíso Perdido", "Odisseia", "Eneida",
            "Os Lusíadas", "O Corvo", "Flores do Mal", "Folhas de Relva", "Antologia Poética",
            "O Guardador de Rebanhos", "Mensagem", "Livro do Desassossego", "Auto da Barca do Inferno",
            "Os Maias", "A Relíquia", "O Primo Basílio", "O Crime do Padre Amaro", "Sertões",
            "Iracema", "O Guarani", "Ubirajara", "A Moreninha", "O Cortiço", "O Ateneu",
            "Quincas Borba", "Esaú e Jacó", "Memorial de Aires", "Vidas Secas", "Capitães da Areia",
            "O Velho e o Mar", "Por Quem os Sinos Dobram", "Adeus às Armas", "As Vinhas da Ira",
            "A Leste do Éden", "Ratos e Homens", "O Som e a Fúria", "Enquanto Agonizo", "Luz em Agosto",
            "O Grande Gatsby", "Suave é a Noite", "O Apanhador no Campo de Centeio", "Franny e Zooey",
            "Pé na Estrada", "Os Vagabundos Iluminados", "Almoço Nu", "Misto-Quente", "Mulheres",
            "O Carteiro", "Factótum", "A Sangue Frio", "Bonequinha de Luxo", "Admirável Mundo Novo",
            "O Hobbit", "O Silmarillion", "Contos Inacabados", "A Torre Negra", "It: A Coisa", "O Iluminado",
            "Duna", "Messias de Duna", "Filhos de Duna", "Fundação", "Eu, Robô", "Neuromancer",
            "Blade Runner", "Fahrenheit 451", "Crônicas Marcianas", "O Homem Ilustrado", "A Estrada",
            "Onde os Velhos Não Têm Vez", "Meridiano de Sangue", "Santuário", "Absalão, Absalão!",
            "O Ruído e a Fúria", "Enquanto Agonizo", "A Sangue Frio", "In Cold Blood", "Breakfast at Tiffany's"
        )

        val livros = titulos.map { Livro(titulo = it, capaUrl = "") }

        val adapter = LivroGridAdapter(livros)
        rvLivrosGenero.adapter = adapter
        rvLivrosGenero.layoutManager = GridLayoutManager(this, 3)
    }
}