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

class EditarLivroActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private var livroId: String? = null

    private lateinit var imgCapa: ImageView
    private lateinit var edtUrlCapa: EditText
    private lateinit var edtNome: EditText
    private lateinit var edtAutor: EditText
    private lateinit var edtGenero: EditText
    private lateinit var edtSinopse: EditText
    private lateinit var btnRemover: Button
    private lateinit var btnSalvar: Button
    private lateinit var txtErro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_livro)

        // Ajustando o título da tela para "Editar"
        findViewById<TextView>(R.id.txtTituloEditar).text = "Editar Livro"

        // Inicialização
        imgCapa = findViewById(R.id.imgCapaLivro)
        edtUrlCapa = findViewById(R.id.edtUrlCapa)
        edtNome = findViewById(R.id.edtNome)
        edtAutor = findViewById(R.id.edtAutor)
        edtGenero = findViewById(R.id.edtGenero)
        edtSinopse = findViewById(R.id.edtSinopse)
        btnRemover = findViewById(R.id.btnRemover)
        btnSalvar = findViewById(R.id.btnEditar)
        txtErro = findViewById(R.id.txtErro)

        findViewById<View>(R.id.btnVoltar).setOnClickListener { finish() }

        // 1. Pegar o ID do livro enviado pela tela anterior (ex: GerenciarLivrosActivity)
        livroId = intent.getStringExtra("LIVRO_ID")

        if (livroId != null) {
            carregarDadosDoLivro(livroId!!)
        } else {
            Toast.makeText(this, "Erro: ID do livro não encontrado.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Listener para carregar a imagem da capa quando o usuário sai do campo URL
        edtUrlCapa.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val url = edtUrlCapa.text.toString().trim()
                if (url.isNotEmpty()) {
                    carregarImagemCapa(url)
                }
            }
        }

        // 2. Lógica de update no Firebase
        btnSalvar.setOnClickListener {
            salvarAlteracoes()
        }

        btnRemover.setOnClickListener {
            mostrarDialogConfirmacao()
        }
    }

    private fun carregarDadosDoLivro(id: String) {
        // Busca o documento específico dentro da sua coleção "livros"
        db.collection("Livros").document(id)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Preenche os campos do XML com o que veio do Firebase
                    edtNome.setText(document.getString("Titulo"))
                    edtAutor.setText(document.getString("Autor"))
                    edtGenero.setText(document.getString("Genero"))
                    edtSinopse.setText(document.getString("Sinopse"))

                    val urlCapa = document.getString("CapaUrl") ?: ""
                    edtUrlCapa.setText(urlCapa)
                    if (urlCapa.isNotEmpty()) {
                        carregarImagemCapa(urlCapa)
                    }
                } else {
                    Toast.makeText(this, "Livro não encontrado no banco.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                txtErro.text = "Erro ao carregar dados: ${exception.message}"
                txtErro.visibility = View.VISIBLE
            }
    }

    private fun salvarAlteracoes() {
        val titulo = edtNome.text.toString().trim()
        val autor = edtAutor.text.toString().trim()
        val genero = edtGenero.text.toString().trim()
        val sinopse = edtSinopse.text.toString().trim()
        val capaUrl = edtUrlCapa.text.toString().trim()

        // Validação simples antes de mandar pro Firebase
        if (titulo.isEmpty() || autor.isEmpty() || genero.isEmpty() || sinopse.isEmpty()) {
            txtErro.text = "Preencha todos os campos obrigatórios."
            txtErro.visibility = View.VISIBLE
            return
        }

        txtErro.visibility = View.GONE

        // Mapeia os novos dados
        val dadosAtualizados = hashMapOf(
            "Titulo" to titulo,
            "Autor" to autor,
            "Genero" to genero,
            "Sinopse" to sinopse,
            "CapaUrl" to capaUrl
        )

        // Atualiza o documento no Firestore
        livroId?.let { id ->
            db.collection("Livros").document(id)
                .update(dadosAtualizados as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Alterações salvas com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Fecha a tela de edição e volta
                }
                .addOnFailureListener { exception ->
                    txtErro.text = "Erro ao atualizar: ${exception.message}"
                    txtErro.visibility = View.VISIBLE
                }
        }
    }

    private fun carregarImagemCapa(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.logo_nome1) // mude para o seu placeholder se necessário
            .error(R.drawable.logo_nome1)
            .into(imgCapa)
    }

    private fun mostrarDialogConfirmacao() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirmar_exclusao, null)
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setView(dialogView)

        val dialog = builder.create()
        // Define o fundo como transparente para que os cantos arredondados do CardView apareçam
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnSim = dialogView.findViewById<Button>(R.id.layoutExcluir)
        val btnNao = dialogView.findViewById<Button>(R.id.layoutVoltar)

        btnSim.setOnClickListener {
            removerLivroDoFirestore()
            dialog.dismiss()
        }

        btnNao.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun removerLivroDoFirestore() {
        // Verifica se temos o ID do livro antes de tentar deletar
        livroId?.let { id ->
            // Aponta para a coleção "Livros" (L maiúsculo) e deleta o documento
            db.collection("Livros").document(id)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Livro removido com sucesso!", Toast.LENGTH_SHORT).show()
                    finish() // Fecha a tela de edição e volta para a listagem
                }
                .addOnFailureListener { exception ->
                    txtErro.text = "Erro ao remover livro: ${exception.message}"
                    txtErro.visibility = View.VISIBLE
                }
        } ?: run {
            Toast.makeText(this, "Erro: ID do livro inválido.", Toast.LENGTH_SHORT).show()
        }
    }
}