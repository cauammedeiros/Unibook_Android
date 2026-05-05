package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VerificarMultasActivity : AppCompatActivity() {

    private lateinit var btnVoltar: ImageButton
    private lateinit var edtBusca: EditText
    private lateinit var btnBuscar: ImageView // Alterado de ImageButton para ImageView para bater com o XML
    private lateinit var rvMultas: RecyclerView
    private lateinit var adapter: MultasAdapter

    private val multasCompletas = mutableListOf<Multa>()
    private val multasFiltradas = mutableListOf<Multa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificar_multas)

        // Inicialização direta para forçar o reconhecimento dos IDs
        btnVoltar = findViewById(R.id.btnVoltar)
        edtBusca = findViewById(R.id.edtBusca)
        btnBuscar = findViewById(R.id.btn_pesquisar_multa)
        rvMultas = findViewById(R.id.rvMultas)

        // Configurar RecyclerView
        adapter = MultasAdapter(multasFiltradas) { multa ->
            Toast.makeText(this, "Usuário: ${multa.nomeUsuario}", Toast.LENGTH_SHORT).show()
        }
        rvMultas.adapter = adapter
        rvMultas.layoutManager = LinearLayoutManager(this)

        carregarDadosExemplo()

        // Verifica se veio um nome da tela de perfil para filtrar automaticamente
        val nomePreDefinido = intent.getStringExtra("USER_NAME")
        if (!nomePreDefinido.isNullOrEmpty()) {
            edtBusca.setText(nomePreDefinido)
            filtrarMultas(nomePreDefinido)
        }

        // Configurar Listeners
        btnVoltar.setOnClickListener { finish() }

        btnBuscar.setOnClickListener {
            filtrarMultas(edtBusca.text.toString())
        }

        edtBusca.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarMultas(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun carregarDadosExemplo() {
        multasCompletas.clear()
        multasCompletas.add(Multa("1", "Nome do Usuário 1", 0.0, 2, false))
        multasCompletas.add(Multa("2", "Nome do Usuário 2", 0.0, 4, false))
        multasCompletas.add(Multa("3", "Nome do Usuário 3", 0.0, 1, false))
        
        multasFiltradas.clear()
        multasFiltradas.addAll(multasCompletas)
        adapter.notifyDataSetChanged()
    }

    private fun filtrarMultas(query: String) {
        val filtrados = multasCompletas.filter { 
            it.nomeUsuario.contains(query, ignoreCase = true) 
        }
        multasFiltradas.clear()
        multasFiltradas.addAll(filtrados)
        adapter.notifyDataSetChanged()
    }
}
