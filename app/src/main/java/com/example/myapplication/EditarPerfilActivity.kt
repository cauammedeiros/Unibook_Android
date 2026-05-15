package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditarPerfilActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    // No futuro, aqui você pegará o ID do usuário logado via Firebase Auth
    // Por enquanto, usaremos um ID fixo para teste que deve existir no seu Firebase
    private val userId = "usuario_teste_1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_perfil)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarAlteracoes)
        val edtNome = findViewById<EditText>(R.id.edtNomeUsuario)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnSalvar.setOnClickListener {
            val novoNome = edtNome.text.toString()
            val novoEmail = edtEmail.text.toString()

            if (novoNome.isNotEmpty() && novoEmail.isNotEmpty()) {
                salvarAlteracoesNoFirebase(novoNome, novoEmail)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarAlteracoesNoFirebase(nome: String, email: String) {
        val dados = mapOf(
            "nome" to nome,
            "email" to email
        )

        // Tenta atualizar o documento na coleção "Usuários"
        db.collection("Usuários").document(userId)
            .set(dados)
            .addOnSuccessListener {
                Toast.makeText(this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}