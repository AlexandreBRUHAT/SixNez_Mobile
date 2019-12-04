package com.sixnez.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sixnez.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val BASE_URL =
    "localhost:8080"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface MyApiService {
    //PUBLIC METHODS
    @GET("login")
    fun login(@Query("login") login: String,
              @Query("password") password: String): String

    @POST("register")
    fun register(@Query("login") login: String,
                 @Query("password") password: String) : String

    //PRIVATE METHODS

    //Films
    @GET("films") // TODO Pageable page
    fun getFilms(@Query("genre") genre: String,
                 @Query("like") like: String,
                 @Query("annee") annee: Int): List<FilmDTO>

    @GET("films/{id}")
    fun getFilm(@Path("id") id: String) : FilmDetailledDTO

    @GET("pictures") // TODO vérif RequestBody
    fun getPictures(@Query("ids") ids: List<FilmIdDTO>) : List<FilmURLDTO>

    //Genres
    @GET("genres")
    fun getGenres() : List<String>

    //Acteurs
    @GET("acteurs") // TODO Pageable page
    fun getActeurs(@Query("like") like: String,
                   @Query("metier") metier: String) : List<ActeurDTO>

    @GET("acteurs/{id}")
    fun getActeur(@Path("id") id: String) : List<ActeurDetailledDTO>

    //Favs
    @POST("favs") // TODO vérif RequestBody
    fun setFavs()

    @DELETE("favs") // TODO vérif RequestBody
    fun deleteFavs()

    @GET("favs") // TODO Pageable page
    fun getFavs() : List<FilmDTO>
}

object MyApi {
    val retrofitService : MyApiService by lazy { retrofit.create(MyApiService::class.java) }
}

class Token(val token: String = "",
    val type: String = "")