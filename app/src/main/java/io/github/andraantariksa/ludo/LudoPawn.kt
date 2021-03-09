package io.github.andraantariksa.ludo

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Parcelable
import android.view.View

const val MAX_POS = 56

class LudoPawn(private val ludoBoard: LudoBoard): View(ludoBoard.context) {

    constructor(ludoBoard: LudoBoard, player: Player): this(ludoBoard) {
        owner = player
        p.color = player.toColor()
        p2.color = Color.BLACK
    }

    var position = -1
    lateinit var initPosInDeck: Pair<Byte, Byte>
    var owner = Player.None
    val p = Paint()
    val p2 = Paint()

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    fun isOwnedBy(player: Player): Boolean {
        return player == owner
    }

    override fun onDraw(canvas: Canvas) {
        val pad = measuredWidth.toFloat() / 10F
        val mid = measuredWidth.toFloat() / 2F
        canvas.drawCircle(mid, mid, mid - pad, p2)
        canvas.drawCircle(mid, mid, mid - pad * 2, p)
    }

    fun refreshLayout() {
        val l: Int
        val t: Int
        if (position > -1) {
            val gridPosition = ludoBoard.path[owner]!![position]
            l = measuredWidth * gridPosition.first
            t = measuredHeight * gridPosition.second
        } else {
            l = ludoBoard.decks[owner]!!.left + measuredWidth * 2 + measuredWidth * initPosInDeck.first
            t = ludoBoard.decks[owner]!!.top + measuredHeight * 2 + measuredHeight * initPosInDeck.second
        }
        layout(
            l,
            t,
            l + measuredWidth,
            t + measuredHeight)
    }

    fun addDiceMove(diceValue: Int) {
        position += diceValue
        if (position > MAX_POS) {
            position = MAX_POS - (position % MAX_POS)
        }
    }
}
