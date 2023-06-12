package com.fzrsahi.colorguesser

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Tictactoe : AppCompatActivity(), View.OnClickListener {


    lateinit var buttons: Array<Button>
    lateinit var displayScore: Array<TextView>
    var playerTurn = true
    var movesCount = 0
    var playerOneScore = 0
    var playerTwoScore = 0
    lateinit var displayWinner : TextView
    lateinit var restartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.tictactoe)
        displayWinner = findViewById(R.id.display_winner);
        restartButton = findViewById(R.id.button_restart)


        displayScore = arrayOf(
            findViewById(R.id.score_player_one),
            findViewById(R.id.score_player_two)
        )

        buttons = arrayOf(
            findViewById(R.id.button_00),
            findViewById(R.id.button_01),
            findViewById(R.id.button_02),
            findViewById(R.id.button_10),
            findViewById(R.id.button_11),
            findViewById(R.id.button_12),
            findViewById(R.id.button_20),
            findViewById(R.id.button_21),
            findViewById(R.id.button_22)

        )

        val resetButton: Button = findViewById(R.id.button_reset)
        resetButton.setOnClickListener {
            resetGame()
        }
        restartButton.setOnClickListener{
            restartGame()
        }
        for (button in buttons) {
            button.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
//        Log.i("button",v.toString())
        val selectedButton = v as Button
        Log.i("button",selectedButton.toString())

        if (selectedButton.text.toString() != "") {
            Log.i("button","not Valid")
            return
        }


        if (playerTurn) {
            selectedButton.text = "X"
        } else {
            selectedButton.text = "O"
        }

        movesCount++

        if (checkForWin()) {
            lateinit var winner: String
            if (playerTurn){
                Log.i("test","player satu "+checkForWin().toString())
                winner = "1"
                playerOneScore++
            }else{
                Log.i("test","player dua "+checkForWin().toString())
                Log.i("test",checkForWin().toString())
                winner = "2"
                playerTwoScore++
            }
            showScore()
            showMessage("Player $winner Menang")
            disableButtons()
        } else if (movesCount == 9) {
            showMessage("Drawww!!")
        }

        playerTurn = !playerTurn
    }

    private fun checkForWin(): Boolean {
        Log.i("buttonss","ada")

        val patterns = arrayOf(
            intArrayOf(0, 1, 2), // baris atas
            intArrayOf(3, 4, 5), // baris tengah
            intArrayOf(6, 7, 8), // baris bawah
            intArrayOf(0, 3, 6), // kolom kiri
            intArrayOf(1, 4, 7), // kolom tengah
            intArrayOf(2, 5, 8), // kolom kanan
            intArrayOf(0, 4, 8), // x kiri atas kanan bawah
            intArrayOf(2, 4, 6)  // x kanan atas kiri bawah
        )

        for (pattern in patterns) {
            Log.i("button",pattern.toString())
            val firstButton = buttons[pattern[0]]
            val secondButton = buttons[pattern[1]]
            val thirdButton = buttons[pattern[2]]

            if (firstButton.text != "" && firstButton.text == secondButton.text && firstButton.text == thirdButton.text) {
                Log.i("test",firstButton.text.toString())
                Log.i("test",secondButton.text.toString())
                Log.i("test",thirdButton.text.toString())
                return true
            }
        }
        return false
    }
    private fun disableButtons() {
        for (button in buttons) {
            button.isEnabled = false
        }
    }

    private fun showMessage(message: String) {
        val displayWinnerText = resources.getString(R.string.display_winner, message)
        Log.i("debug",displayWinnerText)
        displayWinner.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30f)
        displayWinner.text = displayWinnerText
    }


    private fun showScore() {
        val playerOneScoreText = resources.getString(R.string.player_1_score_number , "P. One :" + playerOneScore)
        val playerTwoScoreText = resources.getString(R.string.player_2_score_number , "P. Two :" + playerTwoScore)
        displayScore[0].text = playerOneScoreText
        displayScore[1].text = playerTwoScoreText
    }


    fun restartGame(){
        for (button in buttons) {
            button.text = ""
            button.isEnabled = true
        }
        playerTurn = true
        movesCount = 0
    }

    private fun resetGame() {
        for (button in buttons) {
            button.text = ""
            button.isEnabled = true
        }

        playerOneScore = 0
        playerTwoScore = 0
        displayScore[0].text = resources.getString(R.string.player_1_score)
        displayScore[1].text = resources.getString(R.string.player_2_score)

        displayWinner.text = resources.getString(R.string.title)
        playerTurn = true
        movesCount = 0
    }
}
