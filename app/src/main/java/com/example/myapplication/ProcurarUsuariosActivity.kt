package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procurar_usuarios)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        rvUsuarios = findViewById(R.id.rvUsuarios)
        rvUsuarios.layoutManager = LinearLayoutManager(this)

        escutarUsuariosNoFirestore()
    }

    private fun escutarUsuariosNoFirestore() {
        db.collection("Usuários")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Toast.makeText(this, "Erro ao carregar: ${error.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val listaUsuarios = mutableListOf<Usuario>()
                    val listaIds = mutableListOf<String>()

                    for (document in snapshot.documents) {
                        val usuario = document.toObject(Usuario::class.java)
                        if (usuario != null) {
                            listaUsuarios.add(usuario)
                            listaIds.add(document.id) // Guarda o e-mail/id do documento separadamente
                        }
                    }

                    // Passamos a posição do clique para sabermos qual ID deletar ou visualizar
                    rvUsuarios.adapter = UsuarioAdapter(listaUsuarios) { usuarioClicado ->
                        val index = listaUsuarios.indexOf(usuarioClicado)
                        val idDoDocumento = listaIds[index]
                        mostrarDialogAcaoUsuario(usuarioClicado, idDoDocumento)
                    }
                }
            }
    }

    private fun mostrarDialogAcaoUsuario(usuario: Usuario, idDoDocumento: String) {
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_acao_usuario, null)

        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(dialogView)

        val dialog = builder.create()

        val btnVisualizar = dialogView.findViewById<LinearLayout>(R.id.layoutVisualizarPerfil)
        val btnExcluir = dialogView.findViewById<LinearLayout>(R.id.layoutExcluirPerfil)
        val btnVoltar = dialogView.findViewById<Button>(R.id.layoutVoltarAcao)

        // Visualizar Perfil
        btnVisualizar.setOnClickListener {
            dialog.dismiss()
            val intent = Intent(this, VisualizarPerfilActivity::class.java)
            intent.putExtra("USUARIO_ID", idDoDocumento)
            intent.putExtra("USUARIO_NOME", usuario.nome)
            intent.putExtra("USUARIO_EMAIL", usuario.email)
            startActivity(intent)
        }

        // Excluir Perfil
        btnExcluir.setOnClickListener {
            dialog.dismiss()
            db.collection("Usuários").document(idDoDocumento)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Usuário removido com sucesso!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Erro ao deletar: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        btnVoltar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}