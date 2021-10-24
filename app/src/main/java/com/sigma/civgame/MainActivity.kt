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
import com.sigma.civgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    var paint = Paint()

    var lastTouchDown = PointF()

    var selectedPiece = Piece.GetEmptyPiece()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_main)

        //var aPiece = Piece("K", Piece.TYPE_ROOK, Piece.COLOR_BLACK)

        var img = BitmapFactory.decodeResource(resources, R.drawable.chess_pieces)
        var imgPiece1 = Bitmap.createBitmap(img, 0, 0, 50, 50)

        var aBoard = Board()

        selectedPiece = aBoard.GetPieceByPos(PointF(1f, 1f))
        selectedPiece.IMG = imgPiece1

        
        paint.textSize = 50f
        paint.color = Color.WHITE




//        binding.SVGAME.setOnClickListener {
//
//        }


        binding.SVGAME.setOnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    lastTouchDown.x = event.getX();
                    lastTouchDown.y = event.getY();

                    //TODO: CONVERT TO GRID POS!
                    val touchedGrid = Board.CartesianToGrid(lastTouchDown)
                    Log.d("OnTouchListener>DOWN", lastTouchDown.toString())
                }
                MotionEvent.ACTION_MOVE -> {

                }
                MotionEvent.ACTION_UP -> {

                }
            }

            Log.d("OnTouchListener", "tOUCH")
            v?.onTouchEvent(event) ?: true
        }


        binding.SVGAME.holder.addCallback(object: Callback{

            override fun surfaceDestroyed(holder: SurfaceHolder)
            {

            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                var canvas = holder.lockCanvas();
                if (canvas != null) {

                    //TODO: Draw Stuff here

                    aBoard.Draw(canvas, paint)

                    if(!selectedPiece.IsEmpty())
                    {
                        var freePositions = aBoard.GetFreePositions(selectedPiece.MovementPattern)
                        //then draw the positions!

                        aBoard.DrawFreePos(freePositions, canvas, paint)
                    }


                    holder.unlockCanvasAndPost(canvas)
                    Log.d("DEBUG", "HERE2")
                }
                else
                {
                    Log.d("DEBUG", "HERE3")
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int)
            {

            }

        })



        if (binding.SVGAME.holder.surface.isValid)
        {
            var canvas = binding.SVGAME.holder.lockCanvas()
            binding.SVGAME.holder.unlockCanvasAndPost(canvas)
        }
        else
        {
            Log.d("DEBUG", "HERE2")
        }

    }
}