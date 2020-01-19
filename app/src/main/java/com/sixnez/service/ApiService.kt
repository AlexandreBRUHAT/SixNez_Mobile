package com.sixnez.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sixnez.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val BASE_URL =
    "https://sixnez.herokuapp.com"
    //"localhost:8080"

private const val TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZXJsb3QiLCJleHAiOjE1NzkzNzAzOTF9.gj7HWZfkuoPpgCqSz11Ny6O9DgSeFkfVkJUWuG7UggQ"

private lateinit var token : String

fun setToken(tok: String) {
    token = tok
}

fun getToken(): String {
    return token
}

val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BASIC
}

val client : OkHttpClient = OkHttpClient.Builder().apply {
    this.addInterceptor(interceptor)
}.build()


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(BASE_URL)
    .build()

interface MyApiService {
    //PUBLIC METHODS
    @GET("login")
    fun login(@Query("username") login: String,
              @Query("password") password: String) : Call<ResponseBody>

    @POST("register")
    fun register(@Query("username") login: String,
                 @Query("password") password: String): Call<ResponseBody>

    //PRIVATE METHODS

    //Films
    @GET("films")
    fun getFilms(@Query("page") pageNumber: Int,
                 @Query("genre") genre: String,
                 @Query("like") like: String,
                 @Query("annee") annee: Int,
                 @Header("Authorization")token: String): List<FilmDTO>

    @GET("films/{id}")
    fun getFilm(@Path("id") id: String) : FilmDetailledDTO

    @GET("pictures") // TODO vérif RequestBody
    fun getPictures(@Query("ids") ids: List<FilmIdDTO>) : List<FilmURLDTO>

    //Genres
    @GET("genres")
    fun getGenres() : List<String>

    //Acteurs
    @GET("acteurs")
    fun getActeurs(@Query("page") pageNumber: Int,
                   @Query("like") like: String,
                   @Query("metier") metier: String,
                   @Header("Authorization")token: String) : Deferred<List<ActeurDTO>>

    @GET("acteurs/{id}")
    fun getActeur(@Path("id") id: String,
                  @Header("Authorization")token: String) : Deferred<ActeurDetailledDTO>

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