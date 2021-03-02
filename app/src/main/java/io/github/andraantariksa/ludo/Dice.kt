package io.github.andraantariksa.ludo

class Dice {
    fun roll(): Int {
        val rollValue = (1..6).random()
        return rollValue
    }
}