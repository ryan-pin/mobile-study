package com.example.pokelistkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokelistkotlin.adapter.PokemonAdapter
import com.example.pokelistkotlin.api.ApiService
import com.example.pokelistkotlin.models.PokemonResponse
import com.example.pokelistkotlin.models.PokemonResult
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class SegundaTela : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var rvPokemonList: RecyclerView
    private lateinit var adapter: PokemonAdapter

    companion object {
        private const val TAG = "SegundaTela"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_segunda_tela) // XML deve conter um RecyclerView com ID rv_pokemon_list

        rvPokemonList = findViewById(R.id.ListPoke)

        rvPokemonList.layoutManager = LinearLayoutManager(this)

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiService::class.java)

        val call = service.getPokemonList(151, 0)

        call.enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(call: Call<PokemonResponse>, response: Response<PokemonResponse>) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "Erro na resposta: ${response.code()}")
                    return
                }

                val pokemonItems: List<PokemonResult> = response.body()?.results ?: emptyList()


                adapter = PokemonAdapter(pokemonItems) { selectedItem ->
                    val url = selectedItem.url
                    val id = url.trimEnd('/').split("/").last().toIntOrNull() ?: return@PokemonAdapter

                    val intent = Intent(this@SegundaTela, TerceiraTela::class.java)
                    intent.putExtra("pokemon_id", id)
                    startActivity(intent)
                }

                rvPokemonList.adapter = adapter
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                Log.e(TAG, "Falha ao carregar lista: ${t.message}")
            }
        })
    }
}