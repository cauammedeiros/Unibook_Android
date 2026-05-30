package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class HistoricoAdapter(private val listaHistorico: List<Historico>) :
    RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historico, parent, false)
        return HistoricoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val item = listaHistorico[position]
        
        holder.titulo.text = item.titulo
        holder.autor.text = item.autor
        holder.acao.text = item.tipoAcao
        
        // Configura Status e Botão de Atraso
        holder.status.text = item.status
        if (item.status == "Atrasado") {
            holder.status.setTextColor(holder.itemView.context.getColor(R.color.vermelho_botao))
            holder.btnAtraso.visibility = View.VISIBLE
        }
        holder.btnAtraso.setOnClickListener {
            val intent = android.content.Intent(holder.itemView.context, AtrasoActivity::class.java)
            intent.putExtra("TITULO_LIVRO", item.titulo)
            holder.itemView.context.startActivity(intent)
        }
        
        // Formatar data
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.data.text = item.data?.let { sdf.format(it.toDate()) } ?: ""

        Glide.with(holder.itemView.context)
            .load(item.capaUrl)
            .placeholder(R.drawable.logo_nome1)
            .into(holder.capa)
    }

    override fun getItemCount(): Int = listaHistorico.size

    class HistoricoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val capa: ImageView = view.findViewById(R.id.imgCapaHistorico)
        val titulo: TextView = view.findViewById(R.id.txtTituloHistorico)
        val autor: TextView = view.findViewById(R.id.txtAutorHistorico)
        val acao: TextView = view.findViewById(R.id.txtAcaoHistorico)
        val status: TextView = view.findViewById(R.id.txtStatusHistorico)
        val data: TextView = view.findViewById(R.id.txtDataHistorico)
        val btnAtraso: android.widget.Button = view.findViewById(R.id.btnAnalisarAtraso)
    }
}