package com.sigma.civgame

class Piece () {
    var Name = String()
    var Key = String()

    var Pos = Array (8) { Array(8) {0}}
    var Type = -1
    var Color = -1

    constructor(name: String, type: Int, color: Int) : this() {
        Name = name
        Key = " " //This should be a random string
        Type = type
        Color = color
    }


    companion object
    {
        const val TYPE_ROOK = 0


        const val COLOR_WHITE = 0
        const val COLOR_BLACK = 1
    }
}