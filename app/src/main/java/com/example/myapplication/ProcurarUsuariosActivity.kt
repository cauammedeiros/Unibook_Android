package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ProcurarUsuariosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurar_usuarios)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        // Configurar botões de edição de usuário para abrir o pop-up de ação
        val editButtons = listOf(
            R.id.btnEditUser1, R.id.btnEditUser2, R.id.btnEditUser3,
            R.id.btnEditUser4, R.id.btnEditUser5, R.id.btnEditUser6
        )

        editButtons.forEach { id ->
            findViewById<ImageView>(id)?.setOnClickListener {
                showUserActionDialog()
            }
        }

        val edtBusca = findViewById<EditText>(R.id.edtBuscaUsuarios)
        // Simulação de busca
        edtBusca.setOnEditorActionListener { _, _, _ ->
            Toast.makeText(this, "Buscando: ${edtBusca.text}", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun showUserActionDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_acao_usuario, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.findViewById<LinearLayout>(R.id.layoutVisualizarPerfil).setOnClickListener {
            val intent = Intent(this, VisualizarPerfilActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        dialogView.findViewById<LinearLayout>(R.id.layoutExcluirPerfil).setOnClickListener {
            dialog.dismiss()
            showConfirmDeleteDialog()
        }

        dialogView.findViewById<LinearLayout>(R.id.layoutVoltarAcao).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showConfirmDeleteDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmar_exclusao, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.findViewById<LinearLayout>(R.id.layoutVoltar).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<LinearLayout>(R.id.layoutExcluir).setOnClickListener {
            Toast.makeText(this, "Usuário excluído com sucesso!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }
}
