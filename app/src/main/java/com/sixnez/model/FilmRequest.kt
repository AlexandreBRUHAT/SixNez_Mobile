package com.sixnez.model

class FilmRequest() {
    //recherche
    var query: String? = null

    //tri
    var genre: String? = null
    var annee: String? = null

    //pages
    var page: Int = 0
    var rows: Int = 20

    override fun toString(): String {
        var message = "Refine type : "+ this.query + "\n"+
                "Métier : "+this.genre+"\n"+
                "Année : "+this.annee+"\n"+
                "Page : "+this.page+"\n"+
                "Rows : "+this.rows+"\n"

        return message
    }
}