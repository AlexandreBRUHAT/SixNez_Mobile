package com.sixnez.model

class ActeurDetailledDTO(var id: String,
                         var nomPrenom: String?,
                         var naissance: Int?,
                         var mort: Int?,
                         var metier: List<String>?,
                         var filmDTOS: List<FilmActeurDTO>?)