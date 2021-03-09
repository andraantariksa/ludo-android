package io.github.andraantariksa.ludo

import android.graphics.Color

enum class Player {
    None,
    Red,
    Blue,
    Yellow,
    Green;

    fun toColor(): Int {
        return when (ordinal) {
            1 -> Color.RED
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4 -> Color.GREEN
            else -> Color.BLACK
        }
    }

    companion object {
        fun fromColor(color: Int): Player {
            return when (color) {
                Color.RED -> Player.Red
                Color.BLUE -> Player.Blue
                Color.YELLOW -> Player.Yellow
                Color.GREEN -> Player.Green
                else -> Player.None
            }
        }
    }
}
