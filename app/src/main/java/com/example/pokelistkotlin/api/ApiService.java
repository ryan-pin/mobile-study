package com.example.pokelistkotlin.api;

import com.example.pokelistkotlin.models.Pokemon;
import com.example.pokelistkotlin.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface ApiService {
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
