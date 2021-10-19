package com.sigma.civgame

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import com.sigma.civgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_main)

        var aPiece = Piece("K", Piece.TYPE_ROOK, Piece.COLOR_BLACK)

        if (binding.SVGAME.holder.surface.isValid)
        {
            var canvas = binding.SVGAME.holder.lockCanvas()
            var img = BitmapFactory.decodeResource(resources, R.drawable.hjm_crystal_game_pieces_all)
            var imgPiece1 = Bitmap.createBitmap(img, 0, 0, 50, 50)
            //img = Bitmap.createScaledBitmap(img, 100, 100, false)

            var paint = Paint()
            paint.textSize = 50f
            paint.color = Color.WHITE


            for (k in 0..2)
            {
                var pos = PointF(0f, k * 20f)
                canvas.drawLine(pos.x, pos.y, pos.x + 100, pos.y, paint)

                canvas.drawLine(pos.y, pos.x, pos.y, pos.x + 100, paint)
            }


            canvas.drawBitmap(imgPiece1, 0f, 0f, paint)
            canvas.drawText("debug", 50f, 50f, paint)



            binding.SVGAME.holder.unlockCanvasAndPost(canvas)
        }

    }
}