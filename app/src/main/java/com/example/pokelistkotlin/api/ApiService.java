package com.example.pokelistkotlin.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface ApiService {
    @GET("pokemon/{id}")
    Call<Pokemon> getPokemon(@Path("id") int id);

    baseUrl= https://pokeapi.co/api/v2/
}
