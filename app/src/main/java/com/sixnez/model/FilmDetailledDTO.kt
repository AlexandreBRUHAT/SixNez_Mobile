package com.sixnez.model

class FilmDetailledDTO(var title: String,
                       var imgURL: String?,
                       var annee: Int,
                       var acteurs: List<ActeurFilmDTO>?,
                       var categories: List<String>?,
                       var fav: Boolean)