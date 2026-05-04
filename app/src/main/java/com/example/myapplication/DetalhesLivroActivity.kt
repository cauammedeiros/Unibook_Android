package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalhesLivroActivity : AppCompatActivity() {

    // Declaração das views
    private lateinit var btnVoltar: ImageButton
    private lateinit var txtNomeLivro: TextView
    private lateinit var imgCapaLivro: ImageView
    private lateinit var btnBaixar: Button
    private lateinit var btnFavoritar: Button
    private lateinit var btnAlugar: Button
    private lateinit var txtGenero: TextView
    private lateinit var txtAutor: TextView
    private lateinit var txtSinopse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_livro)

        // Inicializar as views
        inicializarViews()

        // Configurar os cliques dos botões
        configurarBotoes()

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

    private fun inicializarViews() {
        btnVoltar = findViewById(R.id.btnVoltar)
        txtNomeLivro = findViewById(R.id.txtNomeLivro)
        imgCapaLivro = findViewById(R.id.imgCapaLivro)
        btnBaixar = findViewById(R.id.btnBaixar)
        btnFavoritar = findViewById(R.id.btnFavoritar)
        btnAlugar = findViewById(R.id.btnAlugar)
        txtGenero = findViewById(R.id.txtGenero)
        txtAutor = findViewById(R.id.txtAutor)
        txtSinopse = findViewById(R.id.txtSinopse)
    }

    private fun configurarBotoes() {
        // Botão Voltar
        btnVoltar.setOnClickListener {
            finish() // Fecha a tela e volta para a anterior
        }

        // Botão Baixar
        btnBaixar.setOnClickListener {
            showPopUpTermos()
        }

        // Botão Favoritar
        btnFavoritar.setOnClickListener {
            Toast.makeText(this, "Livro favoritado!", Toast.LENGTH_SHORT).show()
            // Aqui você adiciona a lógica para favoritar/desfavoritar
        }

        // Botão Alugar
        btnAlugar.setOnClickListener {
            Toast.makeText(this, "Alugando livro...", Toast.LENGTH_SHORT).show()
            // Aqui você adiciona a lógica para alugar/emprestar
        }
    }

    private fun showPopUpTermos() {
        // 1. Infla o layout do pop-up que você criou
        val mDialogView = layoutInflater.inflate(R.layout.dialog_termos, null)

        // 2. Constrói o AlertDialog usando o seu layout
        val mBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(mDialogView)

        val mAlertDialog = mBuilder.show()

        // 3. Mapeia os componentes que estão DENTRO do pop-up
        val btnConfirmar = mDialogView.findViewById<Button>(R.id.btnConfirmarBaixar)
        val btnVoltarPopUp = mDialogView.findViewById<TextView>(R.id.btnVoltar)
        val checkAceitar = mDialogView.findViewById<android.widget.CheckBox>(R.id.checkAceitar)

        // Opcional: Começa com o botão desativado até aceitarem os termos
        btnConfirmar.isEnabled = false
        checkAceitar.setOnCheckedChangeListener { _, isChecked ->
            btnConfirmar.isEnabled = isChecked
        }

        // Configura o clique do "Voltar" dentro do Pop-up
        btnVoltarPopUp.setOnClickListener {
            mAlertDialog.dismiss()
        }

        // Configura o clique final de "Baixar" dentro do Pop-up
        btnConfirmar.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this, "Iniciando download do livro...", Toast.LENGTH_SHORT).show()
        }
    }
}
