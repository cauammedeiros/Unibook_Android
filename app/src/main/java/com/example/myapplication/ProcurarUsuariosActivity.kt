package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ProcurarUsuariosActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var rvUsuarios: RecyclerView
    private lateinit var edtBusca: EditText

    private val listaUsuariosCompleta = mutableListOf<Usuario>()
    private val listaIdsCompleta = mutableMapOf<String, String>() // Email to ID mapping
    private var listaFiltrada = mutableListOf<Usuario>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurar_usuarios)

        val btnVoltar = findViewById<View>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        edtBusca = findViewById(R.id.edtBuscaUsuarios)
        rvUsuarios = findViewById(R.id.rvUsuarios)
        rvUsuarios.layoutManager = LinearLayoutManager(this)

        configurarBusca()
        escutarUsuariosNoFirestore()
    }

    private fun configurarBusca() {
        edtBusca.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarUsuarios(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filtrarUsuarios(texto: String) {
        val busca = texto.lowercase().trim()
        listaFiltrada = if (busca.isEmpty()) {
            listaUsuariosCompleta.toMutableList()
        } else {
            listaUsuariosCompleta.filter {
                it.nome.lowercase().contains(busca) || it.email.lowercase().contains(busca)
            }.toMutableList()
        }

        rvUsuarios.adapter = UsuarioAdapter(listaFiltrada) { usuarioClicado ->
            val idDoDocumento = listaIdsCompleta[usuarioClicado.email]
            if (idDoDocumento != null) {
                mostrarDialogAcaoUsuario(usuarioClicado, idDoDocumento)
            }
        }
    }

    private fun escutarUsuariosNoFirestore() {
        db.collection("Usuários")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    listaUsuariosCompleta.clear()
                    listaIdsCompleta.clear()

                    for (document in snapshot.documents) {
                        val usuario = document.toObject(Usuario::class.java)
                        if (usuario != null) {
                            listaUsuariosCompleta.add(usuario)
                            listaIdsCompleta[usuario.email] = document.id
                        }
                    }
                    filtrarUsuarios(edtBusca.text.toString())
                }
            }
    }

    private fun mostrarDialogAcaoUsuario(usuario: Usuario, idDoDocumento: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_acao_usuario, null)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnVisualizar = dialogView.findViewById<LinearLayout>(R.id.layoutVisualizarPerfil)
        val btnExcluir = dialogView.findViewById<LinearLayout>(R.id.layoutExcluirPerfil)
        val btnVoltarDialog = dialogView.findViewById<Button>(R.id.layoutVoltarAcao)

        btnVisualizar.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, VisualizarPerfilActivity::class.java).apply {
                putExtra("USUARIO_ID", idDoDocumento)
                putExtra("USUARIO_NOME", usuario.nome)
                putExtra("USUARIO_EMAIL", usuario.email)
            }
            startActivity(intent)
        }

        btnExcluir.setOnClickListener {
            dialog.dismiss()
            mostrarDialogConfirmarExclusao(idDoDocumento)
        }

        btnVoltarDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun mostrarDialogConfirmarExclusao(idDoDocumento: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirmar_exclusao, null)
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnConfirmarExcluir = dialogView.findViewById<Button>(R.id.layoutExcluir)
        val btnVoltar = dialogView.findViewById<Button>(R.id.layoutVoltar)

        btnConfirmarExcluir.setOnClickListener {
            dialog.dismiss()
            db.collection("Usuários").document(idDoDocumento).delete()
                .addOnSuccessListener {
                }
                .addOnFailureListener { e ->
                }
        }

        btnVoltar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}