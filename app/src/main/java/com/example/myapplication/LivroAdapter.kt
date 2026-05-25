package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Adicionamos o "isAdmin" no construtor. Por padrão, ele é false (assim não quebra as outras telas se você esquecer de passar)
class LivroAdapter(
    private val listaLivros: List<Livro>,
    private val isAdmin: Boolean = false
) : RecyclerView.Adapter<LivroAdapter.LivroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_livro, parent, false)
        return LivroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LivroViewHolder, position: Int) {
        val livro = listaLivros[position]
        val context = holder.itemView.context

        // Lógica do Clique Dinâmica baseada no isAdmin
        holder.itemView.setOnClickListener {
            if (isAdmin) {
                // Se for Administrador, vai para a tela de Editar
                val intent = Intent(context, EditarLivroActivity::class.java)
                intent.putExtra("LIVRO_ID", livro.id)
                context.startActivity(intent)
            } else {
                // Se for Aluno, vai para a tela de Detalhes padrão
                val intent = Intent(context, DetalhesLivroActivity::class.java)
                // Passando todos os dados que a DetalhesLivroActivity espera
                intent.putExtra("LIVRO_ID", livro.id)
                intent.putExtra("TITULO", livro.titulo)
                intent.putExtra("AUTOR", livro.autor)
                intent.putExtra("GENERO", livro.genero)
                intent.putExtra("SINOPSE", livro.sinopse)
                intent.putExtra("CAPA_URL", livro.capaUrl)
                context.startActivity(intent)
            }
        }

        holder.titulo.text = livro.titulo
        Glide.with(context)
            .load(livro.capaUrl)
            .placeholder(R.drawable.logo_nome1)
            .into(holder.capa)
    }

    override fun getItemCount() = listaLivros.size

    class LivroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val capa: ImageView = view.findViewById(R.id.imgCapa)
        val titulo: TextView = view.findViewById(R.id.txtTituloLivro)
    }
}