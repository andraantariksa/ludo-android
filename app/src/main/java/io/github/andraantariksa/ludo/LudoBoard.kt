package io.github.andraantariksa.ludo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup

const val RATIO = 1.0f

class LudoBoard(context: Context, attributeSet: AttributeSet):
        ViewGroup(context, attributeSet) {
    val playerPawns = mutableMapOf<Player, List<LudoPawn>>()
    private val totalGridToTarget = 6
    private var gridSideSize = 0F
    private val colors = arrayOf(
            Color.RED,
            Color.BLUE,
            Color.YELLOW,
            Color.GREEN)
    val vertices = FloatArray((totalGridToTarget * 2 * 4 + 4 + 4 * 4) * 2)
    val decks = mutableMapOf<Player, LudoDeck>()
    val pawns = mutableListOf<LudoPawn>()
    val p = Paint()
    val p2 = Paint()
    val path: Map<Player, Array<Pair<Byte, Byte>>> = mapOf(
            Player.Red to arrayOf(
                // Top right
                Pair(8, 1),
                Pair(8, 2),
                Pair(8, 3),
                Pair(8, 4),
                Pair(8, 5),
                // Right
                Pair(9, 6),
                Pair(10, 6),
                Pair(11, 6),
                Pair(12, 6),
                Pair(13, 6),
                Pair(14, 6),
                Pair(14, 7),
                Pair(14, 8),
                Pair(13, 8),
                Pair(12, 8),
                Pair(11, 8),
                Pair(10, 8),
                Pair(9, 8),
                // Bottom
                Pair(8, 9),
                Pair(8, 10),
                Pair(8, 11),
                Pair(8, 12),
                Pair(8, 13),
                Pair(8, 14),
                Pair(7, 14),
                Pair(6, 14),
                Pair(6, 13),
                Pair(6, 12),
                Pair(6, 11),
                Pair(6, 10),
                Pair(6, 9),
                // Left
                Pair(5, 8),
                Pair(4, 8),
                Pair(3, 8),
                Pair(2, 8),
                Pair(1, 8),
                Pair(0, 8),
                Pair(0, 7),
                Pair(0, 6),
                Pair(1, 6),
                Pair(2, 6),
                Pair(3, 6),
                Pair(4, 6),
                Pair(5, 6),
                // Top Left
                Pair(6, 5),
                Pair(6, 4),
                Pair(6, 3),
                Pair(6, 2),
                Pair(6, 1),
                Pair(6, 0),
                Pair(7, 0),
                // Home lane
                Pair(7, 1),
                Pair(7, 2),
                Pair(7, 3),
                Pair(7, 4),
                Pair(7, 5),
                Pair(7, 6)
            ),
            Player.Blue to arrayOf(
                    // Right bottom
                    Pair(13, 8),
                    Pair(12, 8),
                    Pair(11, 8),
                    Pair(10, 8),
                    Pair(9, 8),
                    // Bottom
                    Pair(8, 9),
                    Pair(8, 10),
                    Pair(8, 11),
                    Pair(8, 12),
                    Pair(8, 13),
                    Pair(8, 14),
                    Pair(7, 14),
                    Pair(6, 14),
                    Pair(6, 13),
                    Pair(6, 12),
                    Pair(6, 11),
                    Pair(6, 10),
                    Pair(6, 9),
                    // Left
                    Pair(5, 8),
                    Pair(4, 8),
                    Pair(3, 8),
                    Pair(2, 8),
                    Pair(1, 8),
                    Pair(0, 8),
                    Pair(0, 7),
                    Pair(0, 6),
                    Pair(1, 6),
                    Pair(2, 6),
                    Pair(3, 6),
                    Pair(4, 6),
                    Pair(5, 6),
                    // Top Left
                    Pair(6, 5),
                    Pair(6, 4),
                    Pair(6, 3),
                    Pair(6, 2),
                    Pair(6, 1),
                    Pair(6, 0),
                    Pair(7, 0),
                    Pair(8, 0),
                    Pair(8, 1),
                    Pair(8, 2),
                    Pair(8, 3),
                    Pair(8, 4),
                    Pair(8, 5),
                    // Right top
                    Pair(9, 6),
                    Pair(10, 6),
                    Pair(11, 6),
                    Pair(12, 6),
                    Pair(13, 6),
                    Pair(14, 6),
                    Pair(14, 7),
                    // Home lane
                    Pair(13, 7),
                    Pair(12, 7),
                    Pair(11, 7),
                    Pair(10, 7),
                    Pair(9, 7),
                    Pair(8, 7)
            ),
            Player.Yellow to arrayOf(
                    // Bottom Left
                    Pair(6, 13),
                    Pair(6, 12),
                    Pair(6, 11),
                    Pair(6, 10),
                    Pair(6, 9),
                    // Left
                    Pair(5, 8),
                    Pair(4, 8),
                    Pair(3, 8),
                    Pair(2, 8),
                    Pair(1, 8),
                    Pair(0, 8),
                    Pair(0, 7),
                    Pair(0, 6),
                    Pair(1, 6),
                    Pair(2, 6),
                    Pair(3, 6),
                    Pair(4, 6),
                    Pair(5, 6),
                    // Top
                    Pair(6, 5),
                    Pair(6, 4),
                    Pair(6, 3),
                    Pair(6, 2),
                    Pair(6, 1),
                    Pair(6, 0),
                    Pair(7, 0),
                    Pair(8, 0),
                    Pair(8, 1),
                    Pair(8, 2),
                    Pair(8, 3),
                    Pair(8, 4),
                    Pair(8, 5),
                    // Right
                    Pair(9, 6),
                    Pair(10, 6),
                    Pair(11, 6),
                    Pair(12, 6),
                    Pair(13, 6),
                    Pair(14, 6),
                    Pair(14, 7),
                    Pair(14, 8),
                    Pair(13, 8),
                    Pair(12, 8),
                    Pair(11, 8),
                    Pair(10, 8),
                    Pair(9, 8),
                    // Bottom Right
                    Pair(8, 9),
                    Pair(8, 10),
                    Pair(8, 11),
                    Pair(8, 12),
                    Pair(8, 13),
                    Pair(8, 14),
                    Pair(7, 14),
                    // Home lane
                    Pair(7, 13),
                    Pair(7, 12),
                    Pair(7, 11),
                    Pair(7, 10),
                    Pair(7, 9),
                    Pair(7, 8)
            ),
            Player.Green to arrayOf(
                    // Left Top
                    Pair(1, 6),
                    Pair(2, 6),
                    Pair(3, 6),
                    Pair(4, 6),
                    Pair(5, 6),
                    // Top
                    Pair(6, 5),
                    Pair(6, 4),
                    Pair(6, 3),
                    Pair(6, 2),
                    Pair(6, 1),
                    Pair(6, 0),
                    Pair(7, 0),
                    Pair(8, 0),
                    Pair(8, 1),
                    Pair(8, 2),
                    Pair(8, 3),
                    Pair(8, 4),
                    Pair(8, 5),
                    // Right
                    Pair(9, 6),
                    Pair(10, 6),
                    Pair(11, 6),
                    Pair(12, 6),
                    Pair(13, 6),
                    Pair(14, 6),
                    Pair(14, 7),
                    Pair(14, 8),
                    Pair(13, 8),
                    Pair(12, 8),
                    Pair(11, 8),
                    Pair(10, 8),
                    Pair(9, 8),
                    // Bottom
                    Pair(8, 9),
                    Pair(8, 10),
                    Pair(8, 11),
                    Pair(8, 12),
                    Pair(8, 13),
                    Pair(8, 14),
                    Pair(7, 14),
                    Pair(6, 14),
                    Pair(6, 13),
                    Pair(6, 12),
                    Pair(6, 11),
                    Pair(6, 10),
                    Pair(6, 9),
                    // Left Bottom
                    Pair(5, 8),
                    Pair(4, 8),
                    Pair(3, 8),
                    Pair(2, 8),
                    Pair(1, 8),
                    Pair(0, 8),
                    Pair(0, 7),
                    // Home lane
                    Pair(1, 7),
                    Pair(2, 7),
                    Pair(3, 7),
                    Pair(4, 7),
                    Pair(5, 7),
                    Pair(6, 7)
            )
        )

    init {
        setWillNotDraw(false)

        setBackgroundColor(Color.WHITE)

        colors.forEach { color ->
            val deck = LudoDeck(context)
            val player = Player.fromColor(color)
            decks[player] = deck
            deck.paintRectOuter.color = color

            addView(deck)

            val playerPawns_ = mutableListOf<LudoPawn>()
            for (i in 0..3) {
                val pawn = LudoPawn(this, player)
                // (0, 0), (1, 0), (0, 1), (1, 1)
                pawn.initPosInDeck =
                        Pair(
                            ((i + 1) % 2).toByte(),
                            (i / 2).toByte())
                pawns.add(pawn)
                playerPawns_.add(pawn)

                addView(pawn)
            }

            playerPawns[player] = playerPawns_
        }
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
        val deckSideSize = (gridSideSize * 6F).toInt()
        decks.values.forEach {
            it.measure(deckSideSize, deckSideSize)
        }

        pawns.forEach {
            it.measure(gridSideSize.toInt(), gridSideSize.toInt())
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        p.setStyle(Paint.Style.STROKE)
        p.setStrokeWidth(3F)
        canvas!!.drawRect(0F, 0F, (measuredWidth - 1).toFloat(), (measuredHeight - 1).toFloat(), p)

        val mid = measuredWidth / 2F
        val halfCenterBox = gridSideSize * 1.5F

        // Center Box
        val centerBoxVertices = arrayOf(mid - halfCenterBox, mid + halfCenterBox)
        val platformRectsVerticesValue = arrayOf(
                gridSideSize * (totalGridToTarget + 1),
                gridSideSize * (totalGridToTarget + 2),
                mid - gridSideSize,
                mid + gridSideSize,
                measuredWidth - gridSideSize)
        val rects = arrayOf(
                RectF(platformRectsVerticesValue[0], gridSideSize, platformRectsVerticesValue[1], platformRectsVerticesValue[3]),
                RectF(platformRectsVerticesValue[3], platformRectsVerticesValue[0], platformRectsVerticesValue[4], platformRectsVerticesValue[1]),
                RectF(platformRectsVerticesValue[0], platformRectsVerticesValue[3], platformRectsVerticesValue[1], platformRectsVerticesValue[4]),
                RectF(gridSideSize, platformRectsVerticesValue[0], platformRectsVerticesValue[2], platformRectsVerticesValue[1]))
        val rects2 = arrayOf(
                RectF(mid + (gridSideSize / 2F), gridSideSize, mid + (gridSideSize / 2F) + gridSideSize, gridSideSize * 2),
                RectF(measuredWidth.toFloat() - gridSideSize * 2, mid + (gridSideSize / 2F), measuredWidth.toFloat() - gridSideSize, mid + (gridSideSize / 2F) + gridSideSize),
                RectF(mid - (gridSideSize / 2F), measuredHeight - gridSideSize * 2, mid - (gridSideSize / 2F) - gridSideSize, measuredHeight - gridSideSize),
                RectF(gridSideSize * 2, mid - (gridSideSize / 2F), gridSideSize, mid - (gridSideSize / 2F) - gridSideSize))
        colors.forEachIndexed { idx, el ->
            p2.color = el
            canvas.drawRect(rects[idx], p2)
            canvas.drawRect(rects2[idx], p2)

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

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2] = mid + halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 1] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 2] = mid + halfCenterBox + (gridSideSize * totalGridToTarget).toFloat()
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 3] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 4] = mid + halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 5] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 6] = mid + halfCenterBox + (gridSideSize * totalGridToTarget).toFloat()
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 7] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 8] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 9] = mid + halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 10] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 11] = mid + halfCenterBox + (gridSideSize * totalGridToTarget).toFloat()

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 12] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 13] = mid + halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 14] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 15] = mid + halfCenterBox + (gridSideSize * totalGridToTarget).toFloat()

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 16] = mid - halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 17] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 18] = 0F
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 19] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 20] = mid - halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 21] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 22] = 0F
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 23] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 24] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 25] = mid - halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 26] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 27] = 0F

        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 28] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 29] = mid - halfCenterBox
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 30] = (gridSideSize * totalGridToTarget).toFloat() + gridSideSize * 2
        vertices[(totalGridToTarget * 2 * 4 + 4) * 2 + 31] = 0F

        canvas.drawLines(vertices, p)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val initCoord = gridSideSize * 9F
        decks.values.forEachIndexed { idx, deck ->
            val l = ((((idx / 2) - 1) * -1) * initCoord).toInt()
            val t = ((idx shr 1 xor (idx and 1)) * initCoord).toInt()
            deck.layout(
                l,
                t,
                l + deck.measuredWidth,
                t + deck.measuredHeight)
        }
        pawns.forEach {
            it.refreshLayout()
        }
    }
}
