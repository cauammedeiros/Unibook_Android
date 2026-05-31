package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class MessageAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.txtMessage)
        val label: TextView = view.findViewById(R.id.txtLabel)
        val btnVerLivro: Button? = view.findViewById(R.id.btnVerLivro)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layout = if (viewType == 1) {
            R.layout.item_message_user
        } else {
            R.layout.item_message_bot
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.text
        holder.label.text = if (message.isUser) "Você" else "Unibook Bot"

        // Lógica para o botão de redirecionamento (apenas no lado do Bot)
        if (!message.isUser && holder.btnVerLivro != null) {
            if (message.livroId != null) {
                holder.btnVerLivro.visibility = View.VISIBLE
                holder.btnVerLivro.setOnClickListener {
                    val context = holder.itemView.context
                    val intent = Intent(context, DetalhesLivroActivity::class.java).apply {
                        putExtra("LIVRO_ID", message.livroId)
                        putExtra("TITULO", message.livroTitulo)
                        putExtra("AUTOR", message.livroAutor)
                        putExtra("GENERO", message.livroGenero)
                        putExtra("SINOPSE", message.livroSinopse)
                        putExtra("CAPA_URL", message.livroCapaUrl)
                    }
                    context.startActivity(intent)
                }
            } else {
                holder.btnVerLivro.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = messages.size
}
