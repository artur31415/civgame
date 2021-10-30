package com.sigma.civgame

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var paint = Paint()

    var lastTouchDown = PointF()

    var selectedPiece = Piece.GetEmptyPiece()

    val bitmapW = 1500
    val bitmapH = 1500

    val bitmap = Bitmap.createBitmap(
        bitmapW,
        bitmapH,
        Bitmap.Config.ARGB_8888
    )

    val InitBoardStateString = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"

    val aBoard = Board(InitBoardStateString, resources)

    fun GetEmptyCanvas(): Canvas
    {
        return Canvas(bitmap).apply {
            drawColor(Color.parseColor("#E9D66B"))
        }
    }

    fun Draw()
    {
        aBoard.Draw(GetEmptyCanvas(), paint)
        IV_GAME.setImageBitmap(bitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var aPiece = Piece("K", Piece.TYPE_ROOK, Piece.COLOR_BLACK)

//        var img = BitmapFactory.decodeResource(resources, R.drawable.pawn_b3)
//        var imgPiece1 = Bitmap.createBitmap(img, 0, 0, bitmapW / 8, bitmapH / 8)

        selectedPiece = aBoard.GetPieceByPos(PointF(1f, 1f))
        //selectedPiece.IMG = imgPiece1
        selectedPiece.IsSelected = true

        BT_RESET.setOnClickListener {
            aBoard.SetBoardStateFromString(InitBoardStateString, resources)
        }
        
//        paint.textSize = 50f
//        paint.color = Color.WHITE

        val canvas = GetEmptyCanvas()

        paint = Paint().apply {
            color = Color.parseColor("#545AA7")
            strokeWidth = 5F
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.SQUARE
            strokeMiter = 20F
            textSize = 75f
        }

        Draw()
//
//        IV_GAME.setOnClickListener {
//            Log.d("OnClickListener", "Clicked")
//        }


        IV_GAME.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchDown.x = event.getX();
                    lastTouchDown.y = event.getY();

                    //TODO: CONVERT TO GRID POS!
                    val touchedGrid = Board.CartesianToGrid(lastTouchDown)
                    var drawFlag = false
                    if(!aBoard.IsAnyPieceSelected())
                        drawFlag = aBoard.SelectPieceAt(touchedGrid)
                    else
                    {
                        drawFlag = aBoard.SelectedGridPos(touchedGrid)
                        if(!drawFlag)
                        {
                            aBoard.UnselectPiece()
                            drawFlag = true
                        }
                    }

                    if(drawFlag)
                    {
                        Draw()
                    }

                    Log.d("OnTouchListener>DOWN", lastTouchDown.toString() + "; " + Board.CartesianToGrid(lastTouchDown))
                }
                MotionEvent.ACTION_MOVE -> {

                }
                MotionEvent.ACTION_UP -> {

                }
            }
            v?.onTouchEvent(event) ?: true
        }

    }
}