package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class EmprestimoActivity : BaseActivity() {

    private lateinit var imgCapaLivro: ImageView
    private lateinit var txtNomeLivro: TextView
    private lateinit var txtAutorLivro: TextView
    private lateinit var btnVoltarTopo: ImageButton
    private lateinit var btnVoltar: Button
    private lateinit var btnConfirmar: Button
    private lateinit var checkTermos: CheckBox
    private lateinit var btnAceitarTermos: TextView
    private lateinit var txtErro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emprestimo)

        inicializarViews()
        carregarDadosLivro()
        configurarBotoes()
        configurarMenuNavegacao()
        configurarBotaoTema()
    }

    private fun inicializarViews() {
        imgCapaLivro = findViewById(R.id.imgCapaLivro)
        txtNomeLivro = findViewById(R.id.txtNomeLivro)
        txtAutorLivro = findViewById(R.id.txtAutorLivro)
        btnVoltarTopo = findViewById(R.id.btnVoltarTopo)
        btnVoltar = findViewById(R.id.btnVoltar)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        checkTermos = findViewById(R.id.checkTermos)
        btnAceitarTermos = findViewById(R.id.btnAceitarTermos)
        txtErro = findViewById(R.id.txtErroTermos)
    }

    private fun carregarDadosLivro() {
        val titulo = intent.getStringExtra("TITULO")
        val autor = intent.getStringExtra("AUTOR")
        val capaUrl = intent.getStringExtra("CAPA_URL")

        txtNomeLivro.text = titulo ?: "Título Indisponível"
        txtAutorLivro.text = autor ?: "Autor Desconhecido"

        Glide.with(this)
            .load(capaUrl)
            .placeholder(R.drawable.logo_nome1)
            .into(imgCapaLivro)
        
        // Datas fictícias para exibição (como na imagem)
        findViewById<TextView>(R.id.txtDataEntrega).text = "10/07/2023"
        findViewById<TextView>(R.id.txtDataDevolucao).text = "24/07/2023"
    }

    private fun configurarBotoes() {
        // Seta de voltar no topo
        btnVoltarTopo.setOnClickListener {
            finish()
        }

        // Botão cancelar (voltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        // Texto ao lado do checkbox para facilitar o clique
        btnAceitarTermos.setOnClickListener {
            checkTermos.isChecked = !checkTermos.isChecked
        }

        btnConfirmar.setOnClickListener {
            if (checkTermos.isChecked) {
                txtErro.visibility = View.GONE
                salvarHistorico()
            } else {
                txtErro.visibility = View.VISIBLE
            }
        }
    }

    private fun salvarHistorico() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)
        val livroId = intent.getStringExtra("LIVRO_ID")

        if (userId == null || livroId == null) {
            return
        }

        val db = FirebaseFirestore.getInstance()
        val historico = hashMapOf(
            "userId" to userId,
            "livroId" to livroId,
            "titulo" to intent.getStringExtra("TITULO"),
            "autor" to intent.getStringExtra("AUTOR"),
            "capaUrl" to intent.getStringExtra("CAPA_URL"),
            "data" to Timestamp.now(),
            "tipoAcao" to "Empréstimo"
        )

        db.collection("Historico")
            .add(historico)
            .addOnSuccessListener {
                finish()
            }
            .addOnFailureListener {
            }
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

    private fun configurarMenuNavegacao() {
        findViewById<LinearLayout>(R.id.nav_home).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_library).setOnClickListener {
            startActivity(Intent(this, TelaBibliotecaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_chatbot).setOnClickListener {
            startActivity(Intent(this, ChatbotActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_search).setOnClickListener {
            startActivity(Intent(this, BuscaActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
        findViewById<LinearLayout>(R.id.nav_profile).setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            })
        }
    }
}