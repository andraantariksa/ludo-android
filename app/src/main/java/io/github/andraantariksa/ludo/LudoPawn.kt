package io.github.andraantariksa.ludo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class LudoPawn(context: Context): View(context) {
    var ownedByPlayer = Player.None

    override fun onDraw(canvas: Canvas) {
        val p = Paint()
        p.color = Color.BLACK
        canvas.drawCircle(width / 2F, height / 2F, width.toFloat(), p)
    }
}
