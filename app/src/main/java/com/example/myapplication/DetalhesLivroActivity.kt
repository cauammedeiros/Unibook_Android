package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class DetalhesLivroActivity : AppCompatActivity() {

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

        inicializarViews()
        configurarBotoes()
        configurarMenuNavegacao() // Organizei o menu em uma função separada
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
        btnVoltar.setOnClickListener { finish() }

        btnBaixar.setOnClickListener {
            showPopUpTermos()
        }

        btnFavoritar.setOnClickListener {
            Toast.makeText(this, "Livro favoritado!", Toast.LENGTH_SHORT).show()
        }

        // Ajustado para abrir o popup de escolha que vimos no seu design
        btnAlugar.setOnClickListener {
            showPopUpEscolhaEmprestimo()
        }
    }

    private fun showPopUpTermos() {
        val mDialogView = layoutInflater.inflate(R.layout.dialog_termos, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.create() // Usei create() para poder aplicar o fundo transparente

        // Ajuste para respeitar os cantos arredondados do seu XML
        mAlertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mAlertDialog.show()

        val btnConfirmar = mDialogView.findViewById<Button>(R.id.btnConfirmarBaixar)
        val btnVoltarPopUp = mDialogView.findViewById<TextView>(R.id.btnVoltar) // Ajustado para LinearLayout como no seu print
        val checkAceitar = mDialogView.findViewById<CheckBox>(R.id.checkAceitar)

        btnConfirmar.isEnabled = false
        checkAceitar.setOnCheckedChangeListener { _, isChecked ->
            btnConfirmar.isEnabled = isChecked
        }

        btnVoltarPopUp.setOnClickListener { mAlertDialog.dismiss() }

        btnConfirmar.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this, "Download iniciado!", Toast.LENGTH_SHORT).show()
        }
    }

    // Novo popup baseado no seu print "Escolha como deseja prosseguir"
    private fun showPopUpEscolhaEmprestimo() {
        val mView = layoutInflater.inflate(R.layout.dialog_prosseguir, null)
        val mBuilder = AlertDialog.Builder(this).setView(mView)
        val mDialog = mBuilder.create()

        mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialog.show()

        val btnVoltar = mView.findViewById<TextView>(R.id.btnDialogVoltar)
        val btnIrEmprestimo = mView.findViewById<TextView>(R.id.btnDialogEmprestimo)

        btnVoltar.setOnClickListener { mDialog.dismiss() }

        btnIrEmprestimo.setOnClickListener {
            mDialog.dismiss()
            val intent = Intent(this, EmprestimoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configurarMenuNavegacao() {
        findViewById<LinearLayout>(R.id.navInicio).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navBiblioteca).setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navChatbot).setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navBuscar).setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.navPerfil).setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
    }
}