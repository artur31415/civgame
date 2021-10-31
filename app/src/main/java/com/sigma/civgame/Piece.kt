package com.sigma.civgame

import android.content.res.Resources
import android.graphics.*
import android.util.Log
import androidx.core.graphics.plus

class Piece () {
    var Name = String()
    var Key = String()

    var Pos = PointF(-1f, -1f)
    var Type: PieceType = PieceType.PAWN
    var Color = -1

    var IsAlive = false
    var IsSelected = false

    var MovementPattern = ArrayList<PointF> ()

    var IMG = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    enum class PieceType {
        EMPTY,
        PAWN,
        KNIGHT,
        BISHOP,
        ROOK,
        KING,
        QUEEN
    }

    constructor(name: String, type: PieceType, color: Int) : this() {
        Name = name
        Key = " " //This should be a random string
        Type = type
        Color = color
    }

    fun IsSameColorAsAnother(anotherPiece: Piece): Boolean
    {
        if(Color == anotherPiece.Color)
            return true
        else
            return false
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
        const val COLOR_WHITE = 0
        const val COLOR_BLACK = 1

        val PieceCharToPieceType: HashMap<Char, PieceType> = hashMapOf(
            'p' to PieceType.PAWN,
            'n' to PieceType.KNIGHT,
            'b' to PieceType.BISHOP,
            'r' to PieceType.ROOK,
            'q' to PieceType.QUEEN,
            'k' to PieceType.KING
        )

        val PieceTypeToImageIDW: HashMap<PieceType, Int> = hashMapOf(
            PieceType.PAWN to R.drawable.pawn_w3,
            PieceType.KNIGHT to R.drawable.knight_w3,
            PieceType.BISHOP to R.drawable.bishop_w3,
            PieceType.ROOK to R.drawable.rook_w3,
            PieceType.QUEEN to R.drawable.queen_w3,
            PieceType.KING to R.drawable.king_w3
        )

        val PieceTypeToImageIDB: HashMap<PieceType, Int> = hashMapOf(
            PieceType.PAWN to R.drawable.pawn_b3,
            PieceType.KNIGHT to R.drawable.knight_b3,
            PieceType.BISHOP to R.drawable.bishop_b3,
            PieceType.ROOK to R.drawable.rook_b3,
            PieceType.QUEEN to R.drawable.queen_b3,
            PieceType.KING to R.drawable.king_b3
        )


        //FIXME: SET THE MOTION ACCORDING TO THE RULES
        val PieceTypeToMovementPattern: HashMap<PieceType, ArrayList<PointF>> = hashMapOf(
            PieceType.PAWN to arrayListOf<PointF>(PointF(0f, 1f)),
            PieceType.KNIGHT to arrayListOf<PointF>(
                PointF(-1f, 2f),
                PointF(-1f, -2f),
                PointF(-2f, -1f),
                
                PointF(1f, 2f),
                PointF(1f, 2f),
                PointF(2f, 1f)
                ),
            PieceType.BISHOP to arrayListOf<PointF>(
                PointF(1f, 1f), PointF(2f, 2f), PointF(3f, 3f), 
                PointF(-1f, -1f), PointF(-2f, -2f), PointF(-3f, -3f)
                ),
            PieceType.ROOK to arrayListOf<PointF>(
                PointF(1f, 0f), PointF(2f, 0f), PointF(3f, 0f),
                PointF(-1f, 0f), PointF(-2f, 0f), PointF(-3f, 0f),
                PointF(0f, 1f), PointF(0f, 2f), PointF(0f, 3f),
                PointF(0f, -1f), PointF(0f, -2f), PointF(0f, -3f)
                ),
            PieceType.QUEEN to arrayListOf<PointF>(
                PointF(1f, 0f), PointF(2f, 0f), PointF(0f, 1f), PointF(0f, 2f)
                ),
            PieceType.KING to arrayListOf<PointF>(
                PointF(1f, 0f), PointF(-1f, 0f), PointF(0f, 1f), PointF(0f, -1f)
                )
        )

        fun ToggleColor(anotherColor: Int): Int
        {
            if (anotherColor == COLOR_BLACK)
                return COLOR_WHITE
            else
                return COLOR_BLACK
        }

        fun GetEmptyPiece(): Piece
        {
            return Piece("EMPTY", PieceType.EMPTY, -1)
        }

        fun SetPiece(position: PointF, type: PieceType, color: Int, resources: Resources, bitmapSize: Point, imgIDW: Int, imgIDB: Int, movementPattern: ArrayList<PointF>): Piece
        {
            var newPiece = GetEmptyPiece()

            newPiece.IsAlive = true
            newPiece.Name = type.name
            newPiece.Type = type
            newPiece.Pos = position
            newPiece.Color = color


            var imgID = imgIDB

            if (color == Piece.COLOR_WHITE)
                imgID = imgIDW

            newPiece.IMG = GetPieceImg(resources, imgID, bitmapSize)

            newPiece.MovementPattern = movementPattern
            return newPiece
        }

        fun GetPieceImg(resources: Resources, imageID: Int, imageSize: Point): Bitmap
        {
            val img = BitmapFactory.decodeResource(resources, imageID)
            return Bitmap.createBitmap(img, 0, 0, imageSize.x / 8, imageSize.y / 8)
        }

        fun GetPiece(pieceType: PieceType, position: PointF, color: Int, bitmapSize: Point, resources: Resources): Piece
        {
            return SetPiece(position, pieceType, color, resources, bitmapSize, PieceTypeToImageIDW[pieceType]!!, PieceTypeToImageIDB[pieceType]!!, Piece.PieceTypeToMovementPattern[pieceType]!!)
        }

        fun GetPieceByChar(pieceChar: Char, position: PointF, bitmapSize: Point, resources: Resources): Piece
        {
            var pieceType = PieceCharToPieceType[pieceChar.lowercaseChar()]!!
            var pieceColor = Piece.COLOR_WHITE

            if(pieceChar.isLowerCase())
                pieceColor = Piece.COLOR_BLACK


            return SetPiece(position, pieceType, pieceColor, resources, bitmapSize, PieceTypeToImageIDW[pieceType]!!, PieceTypeToImageIDB[pieceType]!!, Piece.PieceTypeToMovementPattern[pieceType]!!)
        }

    }
}