package com.example.myapplication

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.Date

class VisualizarPerfilActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var layoutLivrosEmprestimo: LinearLayout
    private lateinit var txtSemEmprestimos: TextView
    private lateinit var btnVerificarMulta: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_perfil)

        val edtNome = findViewById<TextView>(R.id.txtNomeUsuario)
        val edtEmail = findViewById<TextView>(R.id.txtEmail)
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        layoutLivrosEmprestimo = findViewById(R.id.layoutLivrosEmprestimo)
        txtSemEmprestimos = findViewById(R.id.txtSemEmprestimos)
        btnVerificarMulta = findViewById(R.id.btnVerificarMulta)
        btnVerificarMulta.visibility = View.GONE

        btnVoltar?.setOnClickListener {
            finish()
        }

        val usuarioId = intent.getStringExtra("USUARIO_ID").orEmpty()
        val nomeUsuario = intent.getStringExtra("USUARIO_NOME")
        val emailUsuario = intent.getStringExtra("USUARIO_EMAIL")

        if (nomeUsuario != null) {
            edtNome.text = nomeUsuario
        }
        if (emailUsuario != null) {
            edtEmail.text = emailUsuario
        }

        carregarLivrosEmprestimo(usuarioId)

        btnVerificarMulta.setOnClickListener {
            val intent = Intent(this, VerificarMultasActivity::class.java)
            intent.putExtra("USER_NAME", nomeUsuario)
            startActivity(intent)
        }
    }

    private fun carregarLivrosEmprestimo(usuarioId: String) {
        layoutLivrosEmprestimo.removeAllViews()
        txtSemEmprestimos.visibility = View.VISIBLE
        btnVerificarMulta.visibility = View.GONE

        if (usuarioId.isBlank()) {
            return
        }

        db.collection("Historico")
            .whereEqualTo("userId", usuarioId)
            .get()
            .addOnSuccessListener { documentos ->
                val emprestimos = documentos.documents
                    .filter { documento ->
                        documento.getString("tipoAcao")
                            .orEmpty()
                            .contains("Empr", ignoreCase = true)
                    }
                    .sortedByDescending { documento ->
                        documento.getTimestamp("data")?.toDate()?.time ?: 0L
                    }

                if (emprestimos.isEmpty()) {
                    return@addOnSuccessListener
                }

                txtSemEmprestimos.visibility = View.GONE

                val possuiAtraso = emprestimos.fold(false) { atrasoEncontrado, documento ->
                    val titulo = documento.getString("titulo")
                        ?.takeIf { it.isNotBlank() }
                        ?: getString(R.string.livro_desconhecido)
                    val atrasado = estaAtrasado(documento.getTimestamp("data"))
                    val status = if (atrasado) "Atrasado" else "Ativo"

                    adicionarLivroEmprestimo(titulo, status, atrasado)
                    atrasoEncontrado || atrasado
                }

                btnVerificarMulta.visibility = if (possuiAtraso) View.VISIBLE else View.GONE
            }
    }

    private fun adicionarLivroEmprestimo(titulo: String, status: String, atrasado: Boolean) {
        val item = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, if (layoutLivrosEmprestimo.childCount == 0) 0 else 16.dp(), 0, 16.dp())
        }

        item.addView(criarLinha(getString(R.string.label_nome), titulo, getColor(R.color.texto_principal)))
        item.addView(
            criarLinha(
                getString(R.string.label_status_v2),
                status,
                getColor(if (atrasado) R.color.vermelho_botao else R.color.verde_sucesso)
            )
        )

        layoutLivrosEmprestimo.addView(item)
    }

    private fun criarLinha(rotulo: String, valor: String, corValor: Int): LinearLayout {
        val linha = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val txtRotulo = TextView(this).apply {
            text = rotulo
            setTextColor(getColor(R.color.texto_secundario))
            textSize = 14f
        }

        val txtValor = TextView(this).apply {
            text = valor
            setTextColor(corValor)
            textSize = 14f
            setTypeface(null, Typeface.BOLD)
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                marginStart = 8.dp()
            }
        }

        linha.addView(txtRotulo)
        linha.addView(txtValor)
        return linha
    }

    private fun estaAtrasado(dataEmprestimo: Timestamp?): Boolean {
        val data = dataEmprestimo?.toDate() ?: return false
        val dataDevolucao = Calendar.getInstance().apply {
            time = data
            add(Calendar.DAY_OF_YEAR, DIAS_PARA_DEVOLUCAO)
        }.time

        return Date().after(dataDevolucao)
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    companion object {
        private const val DIAS_PARA_DEVOLUCAO = 14
    }
}
