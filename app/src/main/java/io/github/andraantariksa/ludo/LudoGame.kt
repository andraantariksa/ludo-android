package io.github.andraantariksa.ludo

import kotlinx.coroutines.GlobalScope
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LudoGame(private val uiController: UIController) {
    var diceValue = -1
    var totalPlayer = 0
    var turn = Player.Red
    var state = GameState.Roll
        get() = field

        set(value) {
            field = value
            refreshGameInfoText()
        }
    var playerPawnInHome = mutableMapOf(
            Player.Red to 0,
            Player.Blue to 0,
            Player.Yellow to 0,
            Player.Green to 0)

    fun nextTurn() {
        turn = turn.next()
        if (turn == Player.None) {
            turn = turn.next()
        }
    }

    fun gameOver(winner: Player) {
        state = GameState.End
        uiController.showGameOverOverlay(winner)
    }

    fun isAnyWin(): Player? {
        return playerPawnInHome.entries.find {
            it.value == 4
        }?.key
    }

    fun deckClicked(ludoDeck: LudoDeck) {
        if (state != GameState.ChoosePawn) return

        if (ludoDeck.isOwnedBy(turn) &&
            diceValue == 6) {
            // Dispatch pawn
            uiController.dispatchPawnFromDeck(turn)
        }
    }

    fun pawnClicked(ludoPawn: LudoPawn) {
        if (state != GameState.ChoosePawn) return

        if (ludoPawn.isOwnedBy(turn) && ludoPawn.position != 56) {
            // Move pawn
            if (ludoPawn.position == -1) {
                if (diceValue == 6) {
                    ludoPawn.position = 0
//                    ludoPawn.position = 56
                } else {
                    return
                }
            } else {
                ludoPawn.addDiceMove(diceValue)
            }

            ludoPawn.refreshLayout()
            if (ludoPawn.position == 56) {
                playerPawnInHome[ludoPawn.owner] = playerPawnInHome[ludoPawn.owner]!!.plus(1)
                Log.d("zzzzz", "val ${playerPawnInHome[ludoPawn.owner]}")
            }
            if (ludoPawn.position < 51) {
                revertOtherPlayerPawn(turn, uiController.getPawnPosition(turn, ludoPawn.position))
            }
            pawnChosen(ludoPawn)
        }
    }

    private fun pawnChosen(ludoPawn: LudoPawn) {
        nextTurn()
        diceValue = -1
        uiController.clearDice()
        state = GameState.Roll

        isAnyWin()?.let {
            gameOver(it)
            return
        }
    }

    private fun revertOtherPlayerPawn(player: Player, position: Pair<Byte, Byte>) {
        uiController.revertOtherPlayerPawn(player, position)
    }

    fun rollDice(): Int {
        diceValue = (1..6).random()
        uiController.afterRoll()
        if (uiController.isThereAnyPossibleMove(turn) && diceValue != 6) {
            state = GameState.CannotMove
            Log.d("zzzzz", "Cannot move")
            GlobalScope.launch(Dispatchers.Main) {
                Log.d("zzzzz", "Roll next")
                delay(2000)
                nextTurn()
                diceValue = -1
                uiController.clearDice()
                state = GameState.Roll
            }
        } else {
            state = GameState.ChoosePawn
        }
        return diceValue
    }

    fun refreshGameInfoText() {
        val text = when (state) {
            GameState.Roll -> {
                "$turn's turn to roll the dice"
            }
            GameState.ChoosePawn -> {
                "$turn's turn to choose pawn"
            }
            GameState.End -> {
                "Game over!"
            }
            GameState.CannotMove -> {
                "No possible move for $turn"
            }
        }
        uiController.setGameInfoText(text)
    }
}
