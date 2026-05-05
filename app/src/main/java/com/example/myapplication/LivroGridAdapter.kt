package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LivroGridAdapter(private val listaLivros: List<Livro>) :
    RecyclerView.Adapter<LivroGridAdapter.LivroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_livro_grid, parent, false)
        return LivroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = listaLivros[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, DetalhesLivroActivity::class.java)
            intent.putExtra("TITULO_LIVRO", livro.titulo)
            it.context.startActivity(intent)
        }
        holder.txtNome.text = livro.titulo
        if (livro.imagem != 0) {
            holder.imgCapa.setImageResource(livro.imagem)
            holder.imgCapa.visibility = View.VISIBLE
        } else {
            holder.imgCapa.visibility = View.GONE
        }
    }

    override fun getItemCount() = listaLivros.size

    class LivroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCapa: ImageView = view.findViewById(R.id.imgLivroGrid)
        val txtNome: TextView = view.findViewById(R.id.txtNomeLivroGrid)
    }
}