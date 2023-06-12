package com.fzrsahi.colorguesser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
    }

    fun startTicTacToe(view: View) {
        val intent = Intent(this, Tictactoe::class.java)
        startActivity(intent)
    }

    fun startColorGuesser(view: View) {
        Log.i("colorguesser","test")
        val intent = Intent(this, ColorGuesser::class.java)
        startActivity(intent)
    }
}
