package io.github.andraantariksa.ludo

import android.content.Context
import android.graphics.*
import android.view.View

class LudoDeck(context: Context): View(context) {
    private var totalPawn = 4
    val paintRectOuter = Paint()
    val paintRectInner = Paint()
    val rectOuter = Rect(0, 0, 50, 50)
    val rectInner = Rect(10, 10, 40, 40)

    init {
        setWillNotDraw(false)

        paintRectInner.color = Color.WHITE
    }

    fun isPawnAvailable(): Boolean {
        return totalPawn > 0
    }

    fun dispatchPawn() {
        totalPawn -= 1
    }

    fun isOwnedBy(player: Player): Boolean {
        return player.toColor() == paintRectOuter.color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        rectOuter.right = widthMeasureSpec
        rectOuter.bottom = heightMeasureSpec

        val pad = (widthMeasureSpec / 6F).toInt()
        rectInner.top = pad
        rectInner.left = pad
        rectInner.right = widthMeasureSpec - pad
        rectInner.bottom = heightMeasureSpec - pad

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(rectOuter, paintRectOuter)
        canvas.drawRect(rectInner, paintRectInner)
//        val pad = (width / 6F).toInt()
//        val p2 = Paint()
//        p2.color = Color.WHITE
//        val rect2 = Rect(pad, pad, width - pad, height - pad)
//        canvas.drawRect(rect2, p2)
    }
}
