package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
            val intent = Intent(holder.itemView.context, DetalhesLivroActivity::class.java)

            // Passando informações para a próxima tela (opcional)
            intent.putExtra("TITULO_LIVRO", "Livro Favoritado")

            holder.itemView.context.startActivity(intent)
        }
        //holder.capa.setImageResource(livro.imagem)
    }

    override fun getItemCount() = listaLivros.size

    class LivroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val capa: ImageView = view.findViewById(R.id.imgCapa)
        val titulo: TextView = view.findViewById(R.id.txtTituloLivro)
    }
}