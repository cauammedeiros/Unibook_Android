package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class EditarLivroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_livro)

        // RF22.1 - Botão Voltar
        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        // RF22.4 - Botão Salvar
        val btnSalvar = findViewById<Button>(R.id.btnSalvarLivro)
        btnSalvar.setOnClickListener {
            Toast.makeText(this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // RF22.4 & RF22.5 - Botão Excluir com Pop-up de confirmação
        val btnExcluir = findViewById<Button>(R.id.btnExcluirLivro)
        btnExcluir.setOnClickListener {
            showConfirmacaoExclusao()
        }
    }

    private fun showConfirmacaoExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Exclusão")
            .setMessage("Tem certeza que deseja excluir este livro do sistema?")
            .setPositiveButton("Excluir") { _, _ ->
                Toast.makeText(this, "Livro excluído com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Voltar", null)
            .show()
    }
}