package com.sigma.civgame

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import androidx.core.graphics.plus
import kotlin.math.floor

class Board () {

    var Pieces = Array(32) { Piece.GetEmptyPiece() }
    

    //board features?

    constructor(InitialPosString: String): this()
    {
        //TODO: setup pieces here
        for (piece in Pieces)
        {
            piece.SetToEmpty()
        }

        Pieces[0].IsAlive = true
        Pieces[0].Name = "Rooky"
        Pieces[0].Type = Piece.TYPE_ROOK
        Pieces[0].Pos = PointF(1f, 1f)

        Pieces[0].MovementPattern.add(PointF(1f, 0f))
        Pieces[0].MovementPattern.add(PointF(-1f, 0f))
        Pieces[0].MovementPattern.add(PointF(0f, 1f))
        Pieces[0].MovementPattern.add(PointF(0f, -1f))
    }

    companion object
    {
        var GridSize = PointF(8f, 8f)
        var WorldW = 1500
        var GridLength = WorldW / 8 //pixels

        fun IsCartesianPosWithinRange(cartesianPos: PointF): Boolean
        {
            if((cartesianPos.x < 0 && cartesianPos.x > WorldW) || (cartesianPos.y < 0 && cartesianPos.y > WorldW))
                return false
            return true
        }

        fun CartesianToGrid(cartesianPos: PointF): PointF {
            var gridPos = PointF()

            gridPos.x = floor(cartesianPos.x / GridLength)
            gridPos.y = floor(cartesianPos.y / GridLength)

            return gridPos
        }

        fun GridToCartesian(gridPos: PointF): PointF {
            var cartesianPos = PointF()

            cartesianPos.x = gridPos.x * GridLength
            cartesianPos.y = gridPos.y * GridLength

            return cartesianPos
        } 
    }

    fun SelectPieceAt(gridPos: PointF): Boolean
    {
        for(piece in Pieces)
        {
            if(piece.IsAlive && !piece.IsSelected && piece.Pos.equals(gridPos))
            {
                piece.IsSelected = true
                return true
            }
        }
        return false
    }

    fun IsAnyPieceSelected(): Boolean
    {
        for(piece in Pieces)
        {
            if(piece.IsAlive && piece.IsSelected)
            {
                return true
            }
        }
        return false
    }

    fun UnselectPiece()
    {
        for(piece in Pieces)
        {
            if(piece.IsAlive && piece.IsSelected)
            {
                piece.IsSelected = false
                break
            }
        }
    }

    fun SelectedGridPos(gridPos: PointF): Boolean
    {
        for(piece in Pieces)
        {
            if(piece.IsAlive && piece.IsSelected)
            {
                if(piece.GetAbsoluteMovementPattern().contains(gridPos))
                {
                    piece.Pos = gridPos
                    piece.IsSelected = false
                    return true
                }
                break
            }
        }
        return false
    }

    fun Draw(canvas: Canvas, paint: Paint)
    {
        //TODO: DRAW THE BOARD HERE!
        canvas.drawColor(Color.GREEN)
        val w = 1500
        for (k in 0..8)
        {
            var pos = PointF(0f, (k * GridLength).toFloat())
            canvas.drawLine(pos.x, pos.y, pos.x + w, pos.y, paint)

            canvas.drawLine(pos.y, pos.x, pos.y, pos.x + w, paint)
        }

        canvas.drawText("debug", 200f, 0f, paint)


        //----------------------------------------------

        for (piece in Pieces)
        {
            piece.Draw(canvas, paint)
        }


        //TODO: DRAW FREE MOVE POS

        for (piece in Pieces)
        {
            if (piece.IsSelected)
            {
                var freePositions = GetFreePositions(piece.GetAbsoluteMovementPattern())
                //then draw the positions!

                DrawFreePos(freePositions, canvas, paint)
                break
            }
        }
    }


    fun DrawFreePos(gridPositions: ArrayList<PointF>, canvas: Canvas, paint: Paint)
    {
        for(gridPosition in gridPositions)
        {
            val cartesianPos = GridToCartesian(gridPosition).plus(PointF(GridLength.toFloat() / 2, GridLength.toFloat() / 2))
            if(IsCartesianPosWithinRange(cartesianPos))
                canvas.drawCircle(cartesianPos.x, cartesianPos.y, GridLength.toFloat() / 4, paint)
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