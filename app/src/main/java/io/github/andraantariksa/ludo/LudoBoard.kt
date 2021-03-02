package io.github.andraantariksa.ludo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup

const val RATIO = 1.0f

class LudoBoard(context: Context, attributeSet: AttributeSet):
        ViewGroup(context, attributeSet) {
    private val ludoPawnsInLane = arrayOf<LudoPawn>()
    private val totalGridToTarget = 6
    private var gridSideSize = 0F
    private val colors = arrayOf(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN)
    private val decks = arrayOf<LudoDeck>(
            LudoDeck(context),
            LudoDeck(context),
            LudoDeck(context),
            LudoDeck(context))

    init {
        setWillNotDraw(false)
        decks.zip(colors).forEach { (deck, color) ->
            deck.color = color
            addView(deck)
        }
    }

    fun setup(totalPawn: Int) {
        totalPawn.coerceIn(1, 4)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        var width = measuredWidth
        var height = measuredHeight
        val widthWithoutPadding = width - paddingLeft - paddingRight
        val heightWithoutPadding = height - paddingTop - paddingBottom

        val maxWidth = (heightWithoutPadding * RATIO).toInt()
        val maxHeight = (widthWithoutPadding / RATIO).toInt()

        if (widthWithoutPadding > maxWidth) {
            width = maxWidth + paddingLeft + paddingRight
        } else {
            height = maxHeight + paddingTop + paddingBottom
        }

        gridSideSize = width / (totalGridToTarget * 2 + 3).toFloat()

        // 6 -> total grid size
        val deckSideSize = gridSideSize * 6F
        val initCoord = gridSideSize * 9F
        decks.forEachIndexed { idx, value ->
            val l = ((((idx / 2) - 1) * -1) * initCoord).toInt()
            val t = ((idx shr 1 xor (idx and 1)) * initCoord).toInt()
            value.layout(
                l,
                t,
                l + deckSideSize.toInt(),
                t + deckSideSize.toInt())
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val p = Paint()
        p.setStyle(Paint.Style.STROKE)
        p.setStrokeWidth(3F)
        canvas!!.drawRect(0F, 0F, (measuredWidth - 1).toFloat(), (measuredHeight - 1).toFloat(), p)

        val vertices = FloatArray((totalGridToTarget * 2 * 4 + 4) * 2)
        val mid = measuredWidth / 2F
        val halfCenterBox = gridSideSize * 1.5F

        // Center Box

        val p2 = Paint()
        val centerBoxVertices = arrayOf(mid - halfCenterBox, mid + halfCenterBox)
        val platformRectsVerticesValue = arrayOf(gridSideSize * (totalGridToTarget + 1), gridSideSize * (totalGridToTarget + 2), mid - gridSideSize, mid + gridSideSize, measuredWidth - gridSideSize)
        val rects = arrayOf(
                RectF(platformRectsVerticesValue[0], gridSideSize, platformRectsVerticesValue[1], platformRectsVerticesValue[3]),
                RectF(platformRectsVerticesValue[3], platformRectsVerticesValue[0], platformRectsVerticesValue[4], platformRectsVerticesValue[1]),
                RectF(platformRectsVerticesValue[0], platformRectsVerticesValue[3], platformRectsVerticesValue[1], platformRectsVerticesValue[4]),
                        RectF(gridSideSize, platformRectsVerticesValue[0], platformRectsVerticesValue[2], platformRectsVerticesValue[1])
        )
        colors.forEachIndexed { idx, el ->
            p2.color = el
            canvas.drawRect(rects[idx], p2)

            val pathTriangle = Path()
            pathTriangle.moveTo(mid, mid)
            pathTriangle.lineTo(centerBoxVertices[(idx / 2 - 1) * -1], centerBoxVertices[idx shr 1 xor (idx and 1)])
            pathTriangle.lineTo(centerBoxVertices[idx shr 1 xor (idx and 1)], centerBoxVertices[idx / 2])
            // (1, 0), (1, 1), (0, 1), (0, 0)
            // (0, 0), (1, 0), (1, 1), (0, 1)
            pathTriangle.lineTo(mid, mid)
            pathTriangle.close()
            canvas.drawPath(pathTriangle, p2)
        }

        // Lines outside
        for (x in 0 until totalGridToTarget) {
            vertices[x * 4] = mid + halfCenterBox + (gridSideSize * x).toFloat()
            vertices[x * 4 + 1] = (gridSideSize * totalGridToTarget).toFloat()
            vertices[x * 4 + 2] = mid + halfCenterBox + (gridSideSize * x).toFloat()
            vertices[x * 4 + 3] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 3

            vertices[totalGridToTarget * 4 + x * 4] = mid - halfCenterBox - (gridSideSize * x).toFloat()
            vertices[totalGridToTarget * 4 + x * 4 + 1] = (gridSideSize * totalGridToTarget).toFloat()
            vertices[totalGridToTarget * 4 + x * 4 + 2] = mid - halfCenterBox - (gridSideSize * x).toFloat()
            vertices[totalGridToTarget * 4 + x * 4 + 3] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 3


            vertices[totalGridToTarget * 2 * 4 + x * 4] = (gridSideSize * totalGridToTarget).toFloat()
            vertices[totalGridToTarget * 2 * 4 + x * 4 + 1] = mid + halfCenterBox + (gridSideSize * x).toFloat()
            vertices[totalGridToTarget * 2 * 4 + x * 4 + 2] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 3
            vertices[totalGridToTarget * 2 * 4 + x * 4 + 3] = mid + halfCenterBox + (gridSideSize * x).toFloat()

            vertices[totalGridToTarget * 3 * 4 + x * 4] = (gridSideSize * totalGridToTarget).toFloat()
            vertices[totalGridToTarget * 3 * 4 + x * 4 + 1] = mid - halfCenterBox - (gridSideSize * x).toFloat()
            vertices[totalGridToTarget * 3 * 4 + x * 4 + 2] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 3
            vertices[totalGridToTarget * 3 * 4 + x * 4 + 3] = mid - halfCenterBox - (gridSideSize * x).toFloat()
        }

        canvas.drawLines(vertices, p)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        return super.onTouchEvent(event)
    }
}
