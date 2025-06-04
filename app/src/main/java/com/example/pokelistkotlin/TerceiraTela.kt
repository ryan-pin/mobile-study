package com.example.pokelistkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
//        override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_terceira_tela)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
    companion object {
    val TAG = TerceiraTela::class.java.simpleName

}

    private lateinit var retrofit: Retrofit
    private var pokeNumber: Int = 1

    private lateinit var btnBack: MaterialButton
    private lateinit var btnNext: MaterialButton
    private lateinit var txtPokeName: TextView
    private lateinit var img: ImageView

    private val imageRequest: String =
        "https://raw.githubusercontent.com" +
                "/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/"


    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terceira_tela)


        btnBack = findViewById(R.id.buttonBack)
        btnNext = findViewById(R.id.buttonNext)
        img = findViewById(R.id.img_poke)
        txtPokeName = findViewById(R.id.name_poke)

        val buttonVoltar = findViewById<MaterialButton>(R.id.buttonVoltar)
        buttonVoltar.setOnClickListener {
            // simplesmente fecha a TerceiraTela e volta para SegundaTela
            finish()
        }

        btnBack.setOnClickListener {
            if (pokeNumber == 1) {
                return@setOnClickListener
            }
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
                    Log.e(TAG, "onResponse failed: ${response.errorBody()}")
                    return
                }

                val pokemon = response.body()
                Log.d(TAG, "poke: ${pokemon?.name}") // ou pokemon?.getName() se estiver usando Java
                txtPokeName.text = pokemon?.getName()
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

