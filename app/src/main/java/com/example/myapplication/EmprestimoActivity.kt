package com.example.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

class EmprestimoActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emprestimo)

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish() // Volta para a tela de detalhes do livro
        }

        val btnConfirmar = findViewById<Button>(R.id.btnConfirmar)
        val checkTermos = findViewById<CheckBox>(R.id.checkTermos) // Use o ID da sua CheckBox
        val txtErro = findViewById<TextView>(R.id.txtErroTermos)

        btnConfirmar.setOnClickListener {
            if (checkTermos.isChecked) {
                txtErro.visibility = View.GONE
                salvarHistorico()
            } else {
                txtErro.visibility = View.VISIBLE
            }
        }

        configurarMenuNavegacao()
        configurarBotaoTema()
    }

    private fun salvarHistorico() {
        val sharedPref = getSharedPreferences("USER_DATA", MODE_PRIVATE)
        val userId = sharedPref.getString("USER_ID", null)
        val livroId = intent.getStringExtra("LIVRO_ID")

        if (userId == null || livroId == null) {
            Toast.makeText(this, "Erro ao processar empréstimo.", Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "Empréstimo realizado e salvo no histórico!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar no histórico", Toast.LENGTH_SHORT).show()
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