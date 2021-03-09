package io.github.andraantariksa.ludo

interface UIController {
    fun showGameOverOverlay(winner: Player)
    fun clearDice()
    fun afterRoll()
    fun getPawnPosition(turn: Player, position: Int): Pair<Byte, Byte>
    fun dispatchPawnFromDeck(turn: Player)
    fun setGameInfoText(text: CharSequence)
    fun revertOtherPlayerPawn(player: Player, position: Pair<Byte, Byte>)
    fun isThereAnyPossibleMove(player: Player): Boolean
}
