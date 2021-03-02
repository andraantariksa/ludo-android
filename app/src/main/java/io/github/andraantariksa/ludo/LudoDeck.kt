package io.github.andraantariksa.ludo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class LudoDeck(context: Context): View(context) {
    private var totalPawn = 4
    var color = Color.BLACK

    init {
        setWillNotDraw(false)
    }

    fun dispatchPawn() {
        totalPawn -= 1
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        Log.d("zzzzz", "Draw")
        val p = Paint()
        p.color = color
        val rect = Rect(0, 0, width, height)
        canvas.drawRect(rect, p)

        val pad = (width / 6F).toInt()
        val p2 = Paint()
        p2.color = Color.WHITE
        val rect2 = Rect(pad, pad, width - pad, height - pad)
        canvas.drawRect(rect2, p2)
    }
}
