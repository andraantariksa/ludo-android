package io.github.andraantariksa.ludo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.github.andraantariksa.ludo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), UIController {
    private val ludoGame = LudoGame(this)
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding?.root
        setContentView(view)

        setupListener()
        ludoGame.refreshGameInfoText()
    }

    private fun setupListener() {
        binding?.buttonDiceRoll?.setOnClickListener {
            val diceValue = ludoGame.rollDice()

            when (diceValue) {
                1 -> binding?.imageViewDice?.setImageResource(R.drawable.ic_dice_one)
                2 -> binding?.imageViewDice?.setImageResource(R.drawable.ic_dice_two)
                3 -> binding?.imageViewDice?.setImageResource(R.drawable.ic_dice_three)
                4 -> binding?.imageViewDice?.setImageResource(R.drawable.ic_dice_four)
                5 -> binding?.imageViewDice?.setImageResource(R.drawable.ic_dice_five)
                6 -> binding?.imageViewDice?.setImageResource(R.drawable.ic_dice_six)
                else -> TODO("Not yet implemented")
            }
        }

        binding?.ludoBoard?.pawns?.forEach { pawn ->
            pawn.setOnClickListener { view ->
                ludoGame.pawnClicked(pawn)
            }
        }

        binding?.ludoBoard?.decks?.values?.forEach { deck ->
            deck.setOnClickListener { view ->
                ludoGame.deckClicked(deck)
            }
        }

        binding?.buttonMainMenu?.setOnClickListener {
            finish()
        }
    }

    override fun clearDice() {
        binding?.imageViewDice?.setImageResource(R.drawable.ic_dice)
        binding?.buttonDiceRoll?.visibility = View.VISIBLE
    }

    override fun getPawnPosition(turn: Player, position: Int): Pair<Byte, Byte> {
        return binding?.ludoBoard?.path?.get(turn)?.get(position)!!
    }

    override fun afterRoll() {
        binding?.buttonDiceRoll?.visibility = View.INVISIBLE
    }

    override fun showGameOverOverlay(winner: Player) {
        binding?.textViewWinner?.text = "$winner is the winner!"
        binding?.constraintLayoutGameOverOverlay?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun dispatchPawnFromDeck(turn: Player) {
        binding?.ludoBoard?.playerPawns?.get(turn)?.find {
            it.position == -1
        }?.performClick()
    }

    override fun setGameInfoText(text: CharSequence) {
        binding?.textViewGameInfo!!.text = text
    }

    override fun revertOtherPlayerPawn(player: Player, position: Pair<Byte, Byte>) {
        binding?.ludoBoard?.playerPawns?.forEach { (t, u) ->
            if (t != player) {
                u.forEach {
                    if (it.position != -1 && binding?.ludoBoard?.path?.get(it.owner)?.get(it.position) == position) {
                        it.position = -1
                        it.refreshLayout()
                    }
                }
            }
        }
    }

    override fun isThereAnyPossibleMove(player: Player): Boolean {
        return binding?.ludoBoard?.playerPawns?.get(player)?.all {
            it.position == -1 || it.position == 56
        }?: false
    }
}