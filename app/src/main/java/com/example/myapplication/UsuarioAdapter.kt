package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(
    private val listaUsuarios: List<Usuario>,
    private val onLapisClick: (Usuario) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNome: TextView = view.findViewById(R.id.txtNomeUsuario)
        val txtEmail: TextView = view.findViewById(R.id.txtEmailUsuario)
        val btnEditar: ImageButton = view.findViewById(R.id.btnEditar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario_lista, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]
        holder.txtNome.text = usuario.nome
        holder.txtEmail.text = usuario.email

        holder.btnEditar.setOnClickListener {
            onLapisClick(usuario)
        }
    }

    override fun getItemCount(): Int = listaUsuarios.size
}