package com.example.pokelistkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokelistkotlin.api.ApiService
import com.example.pokelistkotlin.models.Pokemon
import com.google.android.material.button.MaterialButton
import retrofit2.converter.gson.GsonConverterFactory

class TerceiraTela : AppCompatActivity() {

    companion object {
        val TAG = TerceiraTela::class.java.simpleName
    }

    private lateinit var retrofit: Retrofit
    private var pokeNumber: Int = 1

    private lateinit var btnBack: MaterialButton
    private lateinit var btnNext: MaterialButton
    private lateinit var txtPokeName: TextView
    private lateinit var txtPokeId: TextView
    private lateinit var txtPokeWeight: TextView
    private lateinit var img: ImageView
    private lateinit var txtPokeHeight: TextView

    private val imageRequest: String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/"

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terceira_tela)

        pokeNumber = intent.getIntExtra("pokemon_id", 1)

        btnBack = findViewById(R.id.buttonBack)
        btnNext = findViewById(R.id.buttonNext)
        img = findViewById(R.id.img_poke)
        txtPokeName = findViewById(R.id.name_poke)
        txtPokeId = findViewById(R.id.pokemon_id)
        txtPokeHeight = findViewById(R.id.pokemon_height)


        val buttonVoltar = findViewById<MaterialButton>(R.id.buttonVoltar)
        buttonVoltar.setOnClickListener {
            finish()
        }

        btnBack.setOnClickListener {
            if (pokeNumber == 1) return@setOnClickListener
            pokeNumber--
            getData()
        }

        btnNext.setOnClickListener {
            pokeNumber++
            getData()
        }

        retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        getData()
    }

    private fun getData() {
        val service = retrofit.create(ApiService::class.java)
        val responseCall = service.getPokemon(pokeNumber)

        responseCall.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if (!response.isSuccessful) {
                    Log.e(TAG, "onResponse failed: ${response.code()}")
                    return
                }

                val pokemon = response.body()
                txtPokeName.text = pokemon?.getName()
                txtPokeId.text = "${pokemon?.getId()} -"
                txtPokeHeight.text = "Altura: ${pokemon?.getHeight()}"
                setPokemonImage()
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setPokemonImage() {
        Glide.with(this)
            .load("${imageRequest}${pokeNumber}.gif")
            .into(img)
    }
}
