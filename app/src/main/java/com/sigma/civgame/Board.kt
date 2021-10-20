package com.sigma.civgame

import android.graphics.PointF

class Board () {

    var Pieces = Array(32) { Piece.GetEmptyPiece() }

    //board features?

    constructor(InitialPosString: String): this()
    {
        //TODO: setup pieces here
    }

    fun Draw()
    {
        //TODO: DRAW THE BOARD HERE!

        for (piece in Pieces)
        {
            piece.Draw()
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