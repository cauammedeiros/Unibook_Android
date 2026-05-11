package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BuscaFiltradaAdapter(private val listaLivros: List<String>) : 
    RecyclerView.Adapter<BuscaFiltradaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Você pode mapear mais campos aqui depois (autor, sinopse, etc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_livro_busca_filtrada, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Por enquanto apenas exibe o layout que você criou
    }

    override fun getItemCount() = listaLivros.size
}