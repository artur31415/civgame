package com.sigma.civgame

import android.content.res.Resources
import android.graphics.*
import androidx.core.graphics.plus
import kotlin.math.floor

class Board () {

    var Pieces = ArrayList<Piece>()
    

    //board features?

    constructor(initialPosString: String, resources: Resources): this()
    {
        //TODO: setup pieces here
        for (piece in Pieces)
        {
            piece.SetToEmpty()
        }

        Pieces = StringToBoardState(initialPosString, resources)

        // Pieces[0] = Piece.GetPiece(Piece.PieceType.PAWN, PointF(1f, 1f), Piece.COLOR_WHITE, Point(WorldW, WorldW), resources)
        // Pieces[1] = Piece.GetPiece(Piece.PieceType.KNIGHT, PointF(1f, 5f), Piece.COLOR_WHITE, Point(WorldW, WorldW), resources)
        // Pieces[2] = Piece.GetPiece(Piece.PieceType.BISHOP, PointF(3f, 3f), Piece.COLOR_WHITE, Point(WorldW, WorldW), resources)
        // Pieces[3] = Piece.GetPiece(Piece.PieceType.ROOK, PointF(6f, 5f), Piece.COLOR_WHITE, Point(WorldW, WorldW), resources)
        // Pieces[4] = Piece.GetPiece(Piece.PieceType.QUEEN, PointF(7f, 0f), Piece.COLOR_WHITE, Point(WorldW, WorldW), resources)
        // Pieces[5] = Piece.GetPiece(Piece.PieceType.KING, PointF(4f, 4f), Piece.COLOR_WHITE, Point(WorldW, WorldW), resources)


    }

    companion object
    {
        var GridSize = PointF(8f, 8f)
        var WorldW = 1500
        var GridLength = WorldW / 8 //pixels

        //Forsyth–Edwards_Notation Parser
        fun StringToBoardState(initialPosString: String, resources: Resources): ArrayList<Piece>
        {
            var pieces = ArrayList<Piece>()
            //rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
            var elementsStr = initialPosString.split(" ")
            var boardStateStr = elementsStr[0].split("/")


            var lineCounter = 0
            for(lineStr in boardStateStr)
            {
                if(lineStr != "8")
                {
                    var rankCounter = 0
                    for(pieceChar in lineStr)
                    {
                        var newPiece = Piece.GetPieceByChar(pieceChar, PointF(rankCounter.toFloat(), lineCounter.toFloat()), Point(WorldW, WorldW), resources)

                        pieces.add(newPiece)
                        rankCounter++
                    }
                }

                lineCounter++
            }

            return pieces
        }

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
                    //TODO: PIECE CAPTURING HERE!

                    var isPosFree = true

                    for(anotherPiece in Pieces)
                    {
                        if(anotherPiece.IsAlive && anotherPiece.Pos.equals(gridPos))
                        {
                            if(!anotherPiece.IsSameColorAsAnother(piece))
                            {
                                anotherPiece.IsAlive = false
                            }
                            else
                                isPosFree = false

                            break
                        }
                    }

                    if(isPosFree)
                    {
                        piece.Pos = gridPos
                        piece.IsSelected = false
                        return true
                    }
                    else
                        return false
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
                //TODO: Filter for viable positions

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