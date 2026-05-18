package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LivroAdapter(private val listaLivros: List<Livro>) :
    RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {

    // 1. Cria o visual do item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_livro, parent, false)
        return LivroViewHolder(view)
    }

    // 2. Coloca os dados (texto e imagem) no item
    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = listaLivros[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetalhesLivroActivity::class.java)
            //intent.putExtra("TITULO_LIVRO", livro.titulo)
            holder.itemView.context.startActivity(intent)
        }
        holder.titulo.text = livro.titulo
        Glide.with(holder.itemView.context)
            .load(livro.capaUrl)
            .placeholder(R.drawable.logo_nome)
            .into(holder.capa)
    }

    override fun getItemCount() = listaLivros.size

    class LivroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val capa: ImageView = view.findViewById(R.id.imgCapa)
        val titulo: TextView = view.findViewById(R.id.txtTituloLivro)
    }
}