package io.github.andraantariksa.ludo

class LudoGame {
    var totalPlayer = 0
    var turn = 0

    fun nextTurn() {
        turn = (turn + 1) % totalPlayer
    }

    fun clearGame() {
        
    }
}
