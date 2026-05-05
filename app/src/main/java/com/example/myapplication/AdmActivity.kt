package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class AdmActivity : AppCompatActivity() {
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