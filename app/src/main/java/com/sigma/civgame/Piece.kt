package com.sigma.civgame

import android.graphics.PointF

class Piece () {
    var Name = String()
    var Key = String()

    var Pos = PointF(0f, 0f)
    var Type = -1
    var Color = -1

    var IsAlive = false
    var IsSelected = false

    var MovementPattern = ArrayList<PointF> ()

    constructor(name: String, type: Int, color: Int) : this() {
        Name = name
        Key = " " //This should be a random string
        Type = type
        Color = color
    }


    fun Draw()
    {
        //TODO: DRAW THE PIECE HERE!
    }

    fun IsEmpty(): Boolean
    {
        if(Name == "Empty")
            return true
        else
            return false
    }


    companion object
    {
        const val TYPE_ROOK = 0


        const val COLOR_WHITE = 0
        const val COLOR_BLACK = 1


        fun GetEmptyPiece(): Piece
        {
            return Piece("EMPTY", -1, -1)
        }
    }
}