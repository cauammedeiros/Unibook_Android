package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter para exibir lista de multas no RecyclerView
 */
class MultasAdapter(
    private var multas: List<Multa>,
    private val onItemClick: (Multa) -> Unit = {}
) : RecyclerView.Adapter<MultasAdapter.MultaViewHolder>() {

    /**
     * ViewHolder para cada item de multa
     */
    class MultaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgPerfil: ImageView = view.findViewById(R.id.imgPerfilUsuario)
        val txtNome: TextView = view.findViewById(R.id.txtNomeUsuario)
        val txtValor: TextView = view.findViewById(R.id.txtValorMulta)
        val txtAtraso: TextView = view.findViewById(R.id.txtLivroAtrasado)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_multa, parent, false)
        return MultaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MultaViewHolder, position: Int) {
        val multa = multas[position]

        // Preencher os dados
        holder.txtNome.text = multa.nomeUsuario
        holder.txtValor.text = "Valor da multa: ${multa.getValorFormatado()}"
        holder.txtAtraso.text = multa.getTextoAtraso()
        holder.txtStatus.text = multa.getStatusCompleto()

        // Configurar clique no item
        holder.itemView.setOnClickListener {
            onItemClick(multa)
        }
    }

    override fun getItemCount(): Int = multas.size

    /**
     * Atualiza a lista de multas e notifica o RecyclerView
     */
    fun atualizarLista(novasMultas: List<Multa>) {
        multas = novasMultas
        notifyDataSetChanged()
    }

    /**
     * Filtra a lista de multas com base em uma query de busca
     */
    fun filtrar(query: String): List<Multa> {
        return if (query.isEmpty()) {
            multas
        } else {
            multas.filter { multa ->
                multa.nomeUsuario.contains(query, ignoreCase = true) ||
                        multa.id.contains(query, ignoreCase = true)
            }
        }
    }
}
