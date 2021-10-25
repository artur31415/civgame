package com.sigma.civgame

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import androidx.core.graphics.plus

class Piece () {
    var Name = String()
    var Key = String()

    var Pos = PointF(-1f, -1f)
    var Type = -1
    var Color = -1

    var IsAlive = false
    var IsSelected = false

    var MovementPattern = ArrayList<PointF> ()

    var IMG = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    constructor(name: String, type: Int, color: Int) : this() {
        Name = name
        Key = " " //This should be a random string
        Type = type
        Color = color
    }


    fun Draw(canvas: Canvas, paint: Paint)
    {
        //TODO: DRAW THE PIECE HERE!
        if(!IsEmpty() && IsAlive)
        {
            val cartesianPos = Board.GridToCartesian(Pos)
            canvas.drawBitmap(IMG, cartesianPos.x, cartesianPos.y, paint)
            Log.d("Piece>Draw", "called for piece '" + Name + "' at pos " + cartesianPos.toString())
        }

    }

    fun GetAbsoluteMovementPattern(): ArrayList<PointF>
    {
        var absMovementPattern = ArrayList<PointF>()
        for (pos in MovementPattern)
        {
            absMovementPattern.add(Pos.plus(pos))
        }

        return absMovementPattern
    }

    fun IsEmpty(): Boolean
    {
        if(Name == "Empty")
            return true
        else
            return false
    }

    fun SetToEmpty()
    {
        Name = "Empty"
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

        fun GetDefaultRook(position: PointF, img: Bitmap): Piece
        {
            var newPiece = GetEmptyPiece()

            newPiece.IsAlive = true
            newPiece.Name = "Rook"
            newPiece.Type = Piece.TYPE_ROOK
            newPiece.Pos = position
            newPiece.IMG = img

            newPiece.MovementPattern.add(PointF(1f, 0f))
            newPiece.MovementPattern.add(PointF(-1f, 0f))
            newPiece.MovementPattern.add(PointF(0f, 1f))
            newPiece.MovementPattern.add(PointF(0f, -1f))

            return newPiece
        }
    }
}