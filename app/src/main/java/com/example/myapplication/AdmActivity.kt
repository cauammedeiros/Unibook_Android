package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate

class AdmActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adm)

        // RF20.1 & RF20.3 - Gerenciar Livros com Pop-up
        val btnGerenciar = findViewById<Button>(R.id.btnGerenciarLivros)
        btnGerenciar.setOnClickListener {
            showGerenciarLivrosPopup()
        }

        // RF24 - Procurar Usuários
        val btnUsuarios = findViewById<Button>(R.id.btnProcurarUsuarios)
        btnUsuarios.setOnClickListener {
            val intent = Intent(this, ProcurarUsuariosActivity::class.java)
            startActivity(intent)
        }

        // RF26 - Verificar Multas
        val btnMultas = findViewById<Button>(R.id.btnVerificarMultas)
        btnMultas.setOnClickListener {
            val intent = Intent(this, VerificarMultasActivity::class.java)
            startActivity(intent)
        }

        // RF27 - Configurações
        val btnConfig = findViewById<Button>(R.id.btnConfiguracoesAdm)
        btnConfig.setOnClickListener {
            val intent = Intent(this, ConfiguracoesActivity::class.java)
            startActivity(intent)
        }

        // Botão Voltar
        findViewById<ImageView>(R.id.btnVoltar)?.setOnClickListener {
            finish()
        }

        configurarBotaoTema()
    }

    private fun configurarBotaoTema() {
        val btnTema = findViewById<ImageView>(R.id.btnTema)
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        
        // Define o ícone inicial baseado no tema atual
        btnTema?.setImageResource(if (isDarkMode) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)

        btnTema?.setOnClickListener {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun showGerenciarLivrosPopup() {
        val mView = layoutInflater.inflate(R.layout.dialog_gerenciar_livros, null)
        val mBuilder = AlertDialog.Builder(this).setView(mView)
        val mDialog = mBuilder.create()

        mDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mDialog.show()

        val layoutCriar = mView.findViewById<android.view.View>(R.id.layoutOpcaoCriar)
        val layoutProcurar = mView.findViewById<android.view.View>(R.id.layoutOpcaoProcurar)

        layoutCriar.setOnClickListener {
            mDialog.dismiss()
            startActivity(Intent(this, CriarLivroActivity::class.java))
        }

        layoutProcurar.setOnClickListener {
            mDialog.dismiss()
            startActivity(Intent(this, ProcurarLivrosActivity::class.java))
        }
    }
}