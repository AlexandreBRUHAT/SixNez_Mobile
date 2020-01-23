package com.sixnez.model

class ActeurRequest() {
    //recherche
    var query: String? = null

    //tri
    var metier: String? = null

    //pages
    var page: Int = 0
    var rows: Int = 10

    override fun toString(): String {
        var message = "Refine type : "+ this.query + "\n"+
                "Métier : "+this.metier+"\n"+
                "Page : "+this.page+"\n"+
                "Rows : "+this.rows+"\n"

        return message
    }
}