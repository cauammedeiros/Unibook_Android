package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class LivroGridAdapter(private val listaLivros: List<Livro>) :
    RecyclerView.Adapter<LivroGridAdapter.LivroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_livro, parent, false)
        return LivroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = listaLivros[position]
        
        holder.txtNome.text = livro.titulo

        // Carregamento da imagem com Glide (usando a URL do Firestore)
        Glide.with(holder.itemView.context)
            .load(livro.capaUrl)
            .placeholder(R.drawable.logo_nome1)
            .error(R.drawable.logo_nome1)
            .into(holder.imgCapa)

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

    class LivroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCapa: ImageView = view.findViewById(R.id.imgCapa)
        val txtNome: TextView = view.findViewById(R.id.txtTituloLivro)
    }
}