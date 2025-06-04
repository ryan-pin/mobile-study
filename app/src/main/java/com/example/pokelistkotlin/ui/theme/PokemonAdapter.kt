package com.example.pokelistkotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokelistkotlin.R
import com.example.pokelistkotlin.models.PokemonResult

class PokemonAdapter(
    private val pokemonList: List<PokemonResult>,
    private val onItemClick: (PokemonResult) -> Unit
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.pokemon_name)
        val imagem: ImageView = itemView.findViewById(R.id.Pokemon_img_list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list, parent, false)
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.nome.text = pokemon.name.capitalize()

        // Extrai o ID da URL do Pokémon
        val id = pokemon.url.trimEnd('/').split("/").lastOrNull()?.toIntOrNull()
        if (id != null) {
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

            // Carrega a imagem com Glide
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.imagem)
        }

        holder.itemView.setOnClickListener { onItemClick(pokemon) }
    }

    override fun getItemCount(): Int = pokemonList.size
}