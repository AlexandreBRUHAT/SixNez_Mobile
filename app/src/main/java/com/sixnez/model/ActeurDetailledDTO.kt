package com.sixnez.model

class ActeurDetailledDTO(var id: String,
                         var nomPrenom: String,
                         var naissance: Short,
                         var mort: Short,
                         var metier: List<String>,
                         var filmsDTO: List<FilmActeurDTO>)