package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class BuscaFiltradaAdapter(private val listaLivros: List<Livro>) : 
    RecyclerView.Adapter<BuscaFiltradaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCapa: ImageView = view.findViewById(R.id.imgCapaBusca)
        val txtTitulo: TextView = view.findViewById(R.id.txtTituloBusca)
        val txtAutor: TextView = view.findViewById(R.id.txtAutorBusca)
        val txtGenero: TextView = view.findViewById(R.id.txtGeneroBusca)
        val txtSinopse: TextView = view.findViewById(R.id.txtSinopseBusca)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_livro_busca_filtrada, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val livro = listaLivros[position]
        
        holder.txtTitulo.text = livro.titulo
        holder.txtAutor.text = livro.autor
        holder.txtGenero.text = livro.genero
        holder.txtSinopse.text = livro.sinopse

        // Carregamento da imagem com Glide
        Glide.with(holder.itemView.context)
            .load(livro.capaUrl)
            .placeholder(R.drawable.logo_nome1)
            .into(holder.imgCapa)

        // Configuração do clique para ir para Detalhes
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetalhesLivroActivity::class.java).apply {
                putExtra("LIVRO_ID", livro.id)
                putExtra("TITULO", livro.titulo)
                putExtra("AUTOR", livro.autor)
                putExtra("GENERO", livro.genero)
                putExtra("SINOPSE", livro.sinopse)
                putExtra("CAPA_URL", livro.capaUrl)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = listaLivros.size
}