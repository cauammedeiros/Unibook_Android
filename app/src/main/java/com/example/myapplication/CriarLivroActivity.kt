package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore

class CriarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    // Declaração das Views
    private lateinit var imgCapa: ImageView
    private lateinit var btnAlterarFoto: Button
    private lateinit var edtNome: EditText
    private lateinit var edtAutor: EditText
    private lateinit var edtGenero: EditText
    private lateinit var edtSinopse: EditText
    private lateinit var btnCriar: Button
    private lateinit var txtErro: TextView
    private lateinit var cardErro: androidx.cardview.widget.CardView

    private var urlCapaSelecionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_livro)

        // Inicialização das Views
        imgCapa = findViewById(R.id.imgCapaLivro)
        btnAlterarFoto = findViewById(R.id.btnAlterarFoto)
        edtNome = findViewById(R.id.edtNome)
        edtAutor = findViewById(R.id.edtAutor)
        edtGenero = findViewById(R.id.edtGenero)
        edtSinopse = findViewById(R.id.edtSinopse)
        btnCriar = findViewById(R.id.btnCriar)
        txtErro = findViewById(R.id.txtErro)
        cardErro = findViewById(R.id.cardErroCriarLivro)

        // Configuração do botão voltar
        findViewById<View>(R.id.btnVoltar).setOnClickListener {
            finish()
        }

        // Lógica para abrir o Dialog de Alterar Foto
        btnAlterarFoto.setOnClickListener {
            mostrarDialogAlterarCapa()
        }

        // Lógica do botão Criar
        btnCriar.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            val autor = edtAutor.text.toString().trim()
            val genero = edtGenero.text.toString().trim()
            val sinopse = edtSinopse.text.toString().trim()

            // Validação Rigorosa: Verifica se TODOS os campos foram preenchidos
            if (nome.isEmpty() || autor.isEmpty() || genero.isEmpty() || sinopse.isEmpty() || urlCapaSelecionada.isEmpty()) {
                txtErro.text = "Por favor, preencha todas as informações e adicione uma capa."
                cardErro.visibility = View.VISIBLE
            } else {
                cardErro.visibility = View.GONE
                salvarLivroNoFirebase(nome, autor, genero, sinopse, urlCapaSelecionada)
            }
        }
    }

    private fun mostrarDialogAlterarCapa() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_alterar_capa, null)
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val edtUrl = dialogView.findViewById<EditText>(R.id.edtUrlDialog)
        val btnConfirmar = dialogView.findViewById<Button>(R.id.btnConfirmarUrl)
        val btnCancelar = dialogView.findViewById<Button>(R.id.btnCancelarUrl)

        // Preenche com a URL atual se já existir
        if (urlCapaSelecionada.isNotEmpty()) {
            edtUrl.setText(urlCapaSelecionada)
        }

        btnConfirmar.setOnClickListener {
            val novaUrl = edtUrl.text.toString().trim()
            if (novaUrl.isNotEmpty()) {
                urlCapaSelecionada = novaUrl
                // Atualiza a prévia da imagem na tela principal
                Glide.with(this)
                    .load(urlCapaSelecionada)
                    .placeholder(R.drawable.logo_nome1)
                    .error(R.drawable.logo_nome1)
                    .into(imgCapa)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Insira uma URL válida", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun salvarLivroNoFirebase(nome: String, autor: String, genero: String, sinopse: String, capaUrl: String) {
        // Padroniza o gênero: primeira letra maiúscula, restante minúscula (ex: "terror" -> "Terror")
        val generoPadronizado = genero.trim().lowercase().replaceFirstChar { it.uppercase() }

        val novoLivro = Livro(
            titulo = nome,
            autor = autor,
            genero = generoPadronizado,
            sinopse = sinopse,
            capaUrl = capaUrl
        )

        db.collection("Livros")
            .add(novoLivro)
            .addOnSuccessListener {
                Toast.makeText(this, "Livro cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao salvar no banco: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
