package com.sigma.civgame

import android.graphics.PointF

class Board () {

    var Pieces = Array(32) { Piece.GetEmptyPiece() }
    

    //board features?

    constructor(InitialPosString: String): this()
    {
        //TODO: setup pieces here
    }

    companion object
    {
        var GridSize = PointF(8, 8)
        var GridLength = 12 //pixels

        fun CartesianToGrid(cartesianPos: PointF): PointF {
            var gridPos = PointF()


            return gridPos
        }

        fun GridToCartesian(gridPos: PointF): PointF {
            var cartesianPos = PointF()


            return cartesianPos
        } 
    }

    fun Draw(canvas: Canvas, paint: Paint)
    {
        //TODO: DRAW THE BOARD HERE!
        for (k in 0..2)
        {
            var pos = PointF(0f, k * GridLength)
            canvas.drawLine(pos.x, pos.y, pos.x + 100, pos.y, paint)

            canvas.drawLine(pos.y, pos.x, pos.y, pos.x + 100, paint)
        }

        canvas.drawText("debug", 10f, 10f, paint)


        //----------------------------------------------

        for (piece in Pieces)
        {
            piece.Draw(canvas, paint)
        }


        //TODO: DRAW FREE MOVE POS
    }


    fun DrawFreePos(gridPositions: ArrayList<PointF>, canvas: Canvas, paint: Paint)
    {
        for(gridPosition in gridPositions)
        {
            val cartesianPos = GridToCartesian(gridPosition)
            canvas.drawCircle(cartesianPos.x, cartesianPos.y, GridLength, paint)
        }
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