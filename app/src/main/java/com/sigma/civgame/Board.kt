package com.sigma.civgame

import android.graphics.PointF

class Board () {

    var Pieces = Array(32) { Piece.GetEmptyPiece() }

    //board features?

    constructor(InitialPosString: String): this()
    {
        //TODO: setup pieces here
    }

    fun Draw(canvas: Canvas, paint: Paint)
    {
        //TODO: DRAW THE BOARD HERE!
        for (k in 0..2)
        {
            var pos = PointF(0f, k * 20f)
            canvas.drawLine(pos.x, pos.y, pos.x + 100, pos.y, paint)

            canvas.drawLine(pos.y, pos.x, pos.y, pos.x + 100, paint)
        }

        canvas.drawText("debug", 50f, 50f, paint)


        //----------------------------------------------

        for (piece in Pieces)
        {
            piece.Draw(canvas, paint)
        }


        //TODO: DRAW FREE MOVE POS
    }

    fun GetPieceByPos(position: PointF): Piece
    {
        for (piece in Pieces)
        {
            if(piece.Pos == position)
            {
                return piece
            }
        }
        return Piece.GetEmptyPiece()
    }

    fun GetFreePositions(positions: ArrayList<PointF>): ArrayList<PointF>
    {
        var freePositions = ArrayList<PointF> ()

        for (position in positions)
        {
            var isFree = true
            for (piece in Pieces)
            {
                if (piece.IsAlive && piece.Pos == position)
                {
                    isFree = false
                    break
                }
            }

            if(isFree)
            {
                freePositions.add(position)
            }
        }

        return freePositions
    }
}